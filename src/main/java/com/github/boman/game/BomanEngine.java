package com.github.boman.game;

import com.github.boman.entity.*;
import com.github.boman.event.EventHandlerListener;

import java.time.Duration;
import java.util.*;

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
     *
     * @param t Thời gian.
     */
    public void update(Duration t) {
        // Xóa các Entity
        for (Entity entity: scheduleRemoveEntity) {
            //Entity entity = scheduleRemoveEntity.get(i);
            updateableEntity.remove(entity);
        }
        scheduleRemoveEntity.clear();
        // Thêm các Entity cần cập nhật
        updateableEntity.addAll(scheduleAddEntity);
        scheduleAddEntity.clear();
        // Cập nhật các Entity đã được thêm vào bằng add.
        for (Entity entity: updateableEntity) {
            //Entity entity = updateableEntity.get(i);
            entity.update(t);
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
                    case '*' -> board[i][j] = new Brick(this);
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
        if (board[y][x].block() || board[y][x] instanceof Bomb) {
            return false;
        }
        Bomb bomb = new Bomb(this, player, x, y, power);
        add(bomb);
        board[y][x] = bomb;
        return true;
    }

    /**
     * Nổ bomb.
     *
     * @param bomb Bomb.
     */
    public void explode(Bomb bomb) {
        board[bomb.getY()][bomb.getX()] = new Grass(this);
        // Spawn fire
        int x = bomb.getX();
        int y = bomb.getY();
        int power = bomb.getPower();
        remove(bomb);
        Fire fire = new Fire(this, x, y, Fire.State.Middle);
        add(fire);
        board[y][x] = fire;

    }

    /**
     * Hết lửa.
     *
     * @param fire fire.
     */
    public void endFire(Fire fire) {
        board[fire.getY()][fire.getX()] = new Grass(this);
        remove(fire);
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

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
