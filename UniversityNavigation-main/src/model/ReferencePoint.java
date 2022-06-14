package model;

public class ReferencePoint {
    private final int id;
    private final int id_building;
    private final int id_floor;
    private final String name;
    private final int x;
    private final int y;

    public ReferencePoint(int id, int id_building, int id_floor, String name, int x, int y) {
        this.id = id;
        this.id_building = id_building;
        this.id_floor = id_floor;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getId_building() {
        return id_building;
    }

    public int getId_floor() {
        return id_floor;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return name;
    }
}
