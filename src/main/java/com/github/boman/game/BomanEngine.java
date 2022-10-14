package com.github.boman.game;

import com.github.boman.entity.*;
import com.github.boman.event.EventHandlerListener;

import java.util.*;

// TODO: Thêm một danh sách thực thể để kiểm tra va chạm
// TODO: Sửa lại fire. Lỗi do fire kết thúc thì nó set Grass.
/**
 * Handle game logic.
 */
public class BomanEngine implements Engine {
    public double tileHeight = 20;
    public double tileWidth = 20;
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
    // Truyền input cho các Object đã được thêm vào.
    private EventHandlerListener handler;
    private boolean started;

    public BomanEngine(EventHandlerListener handler) {
        this.updateableEntity = new LinkedList<>();
        this.scheduleRemoveEntity = new ArrayList<>();
        this.scheduleAddEntity = new ArrayList<>();
        this.handler = handler;
    }

    /**
     * Cập nhật danh sách các Entity đang đợi được update.
     */
    public void update() {
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
    }

    /**
     * Thêm Entity cần được update.
     *
     * @param e Entity.
     */
    public void add(Entity e) {
        scheduleAddEntity.add(e);
    }

    /**
     * Xóa Entity khỏi danh sách update.
     *
     * @param e Entity cần xóa.
     */
    public void remove(Entity e) {
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

        board = new TileEntity[height][width];
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                switch (line.charAt(j)) {
                    case '#' -> board[i][j] = new Wall(this);
                    case 'p' -> {
                        board[i][j] = new Grass(this);
                        Bomber player = new Bomber(this, j, i);
                        handler.addListener(player);
                        add(player);
                    }
                    case '1' -> {
                        board[i][j] = new Grass(this);
                        Enemy enemy = new Enemy(this, j, i);
                        add(enemy);
                    }
                    case '*' -> {
                        Brick brick = new Brick(this, j, i);
                        board[i][j] = brick;
                    }
                    default -> board[i][j] = new Grass(this);
                }
            }
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
        if (getEntity(x, y).block() || getEntity(x, y) instanceof Bomb || getEntity(x, y) instanceof Fire) {
            return false;
        }
        Bomb bomb = new Bomb(this, player, x, y, power);
        add(bomb);
        setEntity(bomb, x, y);
        return true;
    }

    public boolean spawnFire(Bomb bomb, int x, int y, Fire.State state) {
        if (getEntity(x, y) instanceof Wall) {
            return false;
        }
        if (getEntity(x, y) instanceof Bomb otherBomb) {
            if (bomb != otherBomb) {
                otherBomb.explode();
            }
        }
        if (getEntity(x, y) instanceof Fire otherFire) {
            state = switch (state) {
                case VDown -> switch (otherFire.getState()){
                    case Horizontal, HLeft, HRight, Middle -> Fire.State.Middle;
                    case Vertical, VUp -> Fire.State.Vertical;
                    case VDown -> Fire.State.VDown;
                };
                case VUp -> switch (otherFire.getState()){
                    case Horizontal, HLeft, HRight, Middle -> Fire.State.Middle;
                    case Vertical, VDown -> Fire.State.Vertical;
                    case VUp -> Fire.State.VUp;
                };
                case HRight -> switch (otherFire.getState()){
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                    case Horizontal, HLeft -> Fire.State.Horizontal;
                    case  HRight -> Fire.State.HRight;
                };
                case HLeft -> switch (otherFire.getState()){
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                    case Horizontal, HRight -> Fire.State.Horizontal;
                    case  HLeft -> Fire.State.HLeft;
                };
                case Horizontal -> switch (otherFire.getState()){
                    case HLeft, Horizontal, HRight -> Fire.State.Horizontal;
                    case VDown, Vertical, VUp, Middle -> Fire.State.Middle;
                };
                case Vertical -> switch (otherFire.getState()){
                    case VDown, Vertical, VUp  -> Fire.State.Vertical;
                    case HLeft, Horizontal, HRight, Middle -> Fire.State.Middle;
                };
                case Middle -> Fire.State.Middle;
            };
        }
        if (getEntity(x, y) instanceof Brick brick) {
            brick.breakBrick();
            add(brick);
            return false;
        }

        Fire fire = new Fire(this, x, y, state);
        add(fire);
        setEntity(fire, x, y);
        return true;
    }

    public TileEntity[][] getBoard() {
        return board;
    }

    @Override
    public double getTileHeight() {
        return tileHeight;
    }

    @Override
    public double getTileWidth() {
        return tileWidth;
    }

    @Override
    public TileEntity getEntity(int x, int y) {
        return board[y][x];
    }

    @Override
    public void setEntity(TileEntity e, int x, int y) {
        board[y][x] = e;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }


}
