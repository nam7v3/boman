package com.github.boman.game;

import com.github.boman.entity.*;
import com.github.boman.event.EventHandlerListener;
import com.github.boman.util.Box;
import com.github.boman.util.SoundEffects;

import java.util.*;

// TODO: Thêm một danh sách thực thể để kiểm tra va chạm
// TODO: Sửa lại fire. Lỗi do fire kết thúc thì nó set Grass.

/**
 * Giữ một bảng bao gồm các tile, Mỗi tile có chiều dài rộng 1 và 1, tọa độ bắt đầu từ (0, 0).
 */
public class BomanEngine implements Engine {
    // Danh sách các Entity cần phải cập nhật. Gọi add để thêm Entity vào danh sách cần cập nhật.
    // Dùng cho các Entity cần phải truyền thời gian vào để cập nhật, xử lí trong hàm update
    // (animation, logic,...).
    // Có thể dùng cho các Entity xuất hiện một lúc trên màn hình rồi biến mất.
    private List<Entity> updateableEntity;
    // Danh sách các Entity xóa đi sau khi cập nhật xong. Được gọi sau khi đã cập nhật hết trong
    // updateableEntity, Engine sẽ duyệt qua các Entity cần xóa và bỏ nó ra khỏi updateableEntity.
    private List<Entity> scheduleRemoveEntity;
    // Tương tự scheduleRemoveEntity, nhưng cần add vào.
    private List<Entity> scheduleAddEntity;
    // Bảng chơi của game.
    private TileEntity[][] board;
    // Những thực thể ở trên bảng chơi.
    private List<MoveableEntity> entities;
    // Truyền input cho các Object đã được thêm vào.
    private EventHandlerListener handler;
    private Bomber player;
    private int enemyCount;
    private int balloomCount;
    private int onealCount;
    private int kondoriaCount;
    private int minvoCount;
    private int dollCount;
    private List<Thwimp> thwimps;
    private int mapWidth;
    private int mapHeight;
    private boolean started;

    private boolean winGame = false;

    private boolean paused;

    private int currentLevel = -1;

    private String[] levels = new String[]{
            "level1.txt",
            "level2.txt",
            "level3.txt"
    };

    public BomanEngine(EventHandlerListener handler) {
        this.updateableEntity = new LinkedList<>();
        this.scheduleRemoveEntity = new ArrayList<>();
        this.scheduleAddEntity = new ArrayList<>();
        this.entities = new LinkedList<>();
        this.handler = handler;
    }

    /**
     * Cập nhật danh sách các Entity đang đợi được update.
     */
    public void update() {
        if (paused) {
            return;
        }
        // Xóa các Entity
        for (Entity entity : scheduleRemoveEntity) {
            updateableEntity.remove(entity);
        }
        scheduleRemoveEntity.clear();
        // Thêm các Entity cần cập nhật
        updateableEntity.addAll(scheduleAddEntity);
        scheduleAddEntity.clear();
        // Cập nhật các Entity đã được thêm vào bằng add.
        for (Entity entity : updateableEntity) {
            //Entity entity = updateableEntity.get(i);
            entity.update();
        }

        // Va chạm giữa các thực thể.
        for (Entity e : entities) {
            for (Entity other : entities) {
                if (e != other) {
                    e.interactWith(other);
                    other.interactWith(e);
                }
            }
        }
    }

    /**
     * Thêm Entity cần được update.
     *
     * @param e Entity.
     */
    public void addUpdateEntity(Entity e) {
        scheduleAddEntity.add(e);
    }

    /**
     * Xóa Entity khỏi danh sách update.
     *
     * @param e Entity cần xóa.
     */
    public void removeUpdateEntity(Entity e) {
        scheduleRemoveEntity.add(e);
    }

    public List<Entity> getUpdateableEntity() {
        return updateableEntity;
    }

    /**
     * Load map từ file.
     *
     * @param filename Tên file trong folder resources/game.
     */
    public void loadMap(String filename) {
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
        int level = scanner.nextInt();
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        mapWidth = width + 1;
        mapHeight = height + 1;

        board = new TileEntity[height + 2][width + 2];
        scanner.nextLine();
        for (int i = 1; i <= height; i++) {
            String line = scanner.nextLine();
            for (int j = 1; j <= width; j++) {
                if(j - 1 >= line.length()){
                    board[i][j] = new Barrier(this);
                    continue;
                }
                switch (line.charAt(j - 1)) {
                    case '#' -> board[i][j] = new Wall(this);
                    case 'p' -> {
                        board[i][j] = new Grass(this);
                        player = new Bomber(this, j, i);
                        spawnBomber(player);
                    }
                    case '1' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Balloom(this, j, i));
                    }
                    case '2' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Oneal(this, j, i));
                    }
                    case '3' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Kondoria(this, j, i));
                    }
                    case '4' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Minvo(this, j, i));
                    }
                    case '5' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Doll(this, j, i));
                    }
                    case '6' -> {
                        board[i][j] = new Grass(this);
                        spawnEnemy(new Thwimp(this, j, i));
                    }
                    case '*' -> {
                        Brick brick = new Brick(this, j, i);
                        board[i][j] = brick;
                    }
                    case 'x' -> {
                        Portal portal = new Portal(this);
                        board[i][j] = portal;
                    }
                    default -> board[i][j] = new Grass(this);
                }
            }
        }
        for (int i = 0; i <= height + 1; ++i) {
            board[i][0] = new Barrier(this);
            board[i][width + 1] = new Barrier(this);
        }
        for (int i = 0; i <= width + 1; ++i) {
            board[0][i] = new Barrier(this);
            board[height + 1][i] = new Barrier(this);
        }
    }

    /**
     * Đặt bomb tại tile (x, y).
     *
     * @param x     Tọa độ x.
     * @param y     Tọa độ y.
     * @param power Sức mạnh bomb.
     * @return true nếu đặt được, false nếu
     */
    public boolean spawnBomb(Bomber player, int x, int y, int power) {
        if (getTile(x, y).block() ||
                getTile(x, y) instanceof Bomb ||
                getTile(x, y) instanceof Fire ||
                getTile(x, y) instanceof Portal) {
            return false;
        }
        Bomb bomb = new Bomb(this, player, x, y, power);
        addUpdateEntity(bomb);
        setTile(bomb, x, y);
        SoundEffects.instance.playSound(SoundEffects.SoundIndex.BOMB_PLANTED);
        return true;
    }

    public boolean spawnFire(Bomb bomb, int x, int y, Fire.State state) {
        if (getTile(x, y) instanceof Wall || getTile(x, y) instanceof Portal) {
            return false;
        }
        for (Thwimp thwimp: thwimps) {
            if ((int) thwimp.getPos().getX() == x && (int) thwimp.getPos().getY() == y) {
                return false;
            }
        }
        if (getTile(x, y) instanceof Bomb otherBomb) {
            if (bomb != otherBomb) {
                otherBomb.explode();
            }
        }
        if (getTile(x, y) instanceof Fire otherFire) {
            state = switch (state) {
                case VDown -> switch (otherFire.getState()) {
                    case Horizontal, HLeft, HRight, Middle -> Fire.State.Middle;
                    case Vertical, VUp -> Fire.State.Vertical;
                    case VDown -> Fire.State.VDown;
                };
                case VUp -> switch (otherFire.getState()) {
                    case Horizontal, HLeft, HRight, Middle -> Fire.State.Middle;
                    case Vertical, VDown -> Fire.State.Vertical;
                    case VUp -> Fire.State.VUp;
                };
                case HRight -> switch (otherFire.getState()) {
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                    case Horizontal, HLeft -> Fire.State.Horizontal;
                    case HRight -> Fire.State.HRight;
                };
                case HLeft -> switch (otherFire.getState()) {
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                    case Horizontal, HRight -> Fire.State.Horizontal;
                    case HLeft -> Fire.State.HLeft;
                };
                case Horizontal -> switch (otherFire.getState()) {
                    case HLeft, Horizontal, HRight -> Fire.State.Horizontal;
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                };
                case Vertical -> switch (otherFire.getState()) {
                    case VDown, Vertical, VUp -> Fire.State.Vertical;
                    case HLeft, Horizontal, HRight, Middle -> Fire.State.Middle;
                };
                case Middle -> Fire.State.Middle;
            };
        }
        if (getTile(x, y) instanceof Brick brick) {
            brick.breakBrick();
            addUpdateEntity(brick);
            return false;
        }

        Fire fire = new Fire(this, x, y, state);
        addUpdateEntity(fire);
        setTile(fire, x, y);
        return true;
    }

    @Override
    public void spawnBomber(Bomber bomber) {
        handler.addListener(bomber);
        addEntity(bomber);
        addUpdateEntity(bomber);
        this.player = bomber;
    }

    @Override
    public void spawnEnemy(Enemy enemy) {
        addEntity(enemy);
        addUpdateEntity(enemy);
        enemyCount++;
        if (enemy instanceof Balloom) {
            balloomCount++;
        }
        if (enemy instanceof Oneal) {
            onealCount++;
        }
        if (enemy instanceof Kondoria) {
            kondoriaCount++;
        }
        if (enemy instanceof Minvo) {
            minvoCount++;
        }
        if (enemy instanceof Doll) {
            dollCount++;
        }
        if (enemy instanceof Thwimp thwimp) {
            thwimps.add(thwimp);
        }
    }

    @Override
    public void addEntity(MoveableEntity e) {
        entities.add(e);
    }

    @Override
    public void removeEntity(MoveableEntity e) {
        entities.remove(e);
    }

    public boolean validTile(int x, int y) {
        return x >= 0 && x <= mapWidth
                && y >= 0 && y <= mapHeight;
    }

    public List<MoveableEntity> getEntity() {
        return entities;
    }

    public TileEntity[][] getBoard() {
        return board;
    }

    @Override
    public TileEntity getTile(int x, int y) {
        return board[y][x];
    }

    public Box getBoxAtTile(int x, int y) {
        return new Box(x, y, 1, 1);
    }

    @Override
    public void setTile(TileEntity e, int x, int y) {
        board[y][x] = e;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Bomber getPlayer() {
        return player;
    }

    public void setPlayer(Bomber player) {
        this.player = player;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    @Override
    public List<MoveableEntity> getEntities() {
        return entities;
    }

    @Override
    public boolean winLevel() {
        return enemyCount <= 0;
    }

    @Override
    public void reset() {
        this.enemyCount = 0;
        this.onealCount = 0;
        this.balloomCount = 0;
        this.kondoriaCount = 0;
        this.minvoCount = 0;
        this.dollCount = 0;
        this.thwimps = new ArrayList<>();
        handler.removeListener(this.player);
        this.player = null;
        this.entities.clear();
        this.board = null;
        this.updateableEntity.clear();
        this.scheduleAddEntity.clear();
        this.scheduleRemoveEntity.clear();
        this.started = false;
    }

    @Override
    public void nextLevel() {
        reset();
        currentLevel++;
        if (currentLevel >= levels.length) {
            winGame = true;
            return;
        }
        loadMap(levels[currentLevel]);
    }

    public void killEnemy(Enemy e) {
        if (e instanceof Balloom) {
            balloomCount--;
        }
        if (e instanceof Oneal) {
            onealCount--;
        }
        if (e instanceof Kondoria) {
            kondoriaCount--;
        }
        if (e instanceof Minvo) {
            minvoCount--;
        }
        if (e instanceof Doll) {
            dollCount--;
        }
        if (e instanceof Thwimp thwimp) {
            thwimps.remove(thwimp);
        }
        enemyCount--;
        removeEntity(e);
        removeUpdateEntity(e);
    }

    public boolean winGame() {
        return winGame;
    }
    public boolean loseGame() {
        return player.getLives() <= 0;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getOnealCount() {
        return onealCount;
    }

    public int getBalloomCount() {
        return balloomCount;
    }

    public int getKondoriaCount() {
        return kondoriaCount;
    }

    public int getMinvoCount() {
        return minvoCount;
    }

    public int getDollCount() {
        return dollCount;
    }

    public int getThwimpCount() {
        return thwimps.size();
    }

    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
