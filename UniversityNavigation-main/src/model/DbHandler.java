package model;
import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class DbHandler implements DataBaseInterface{
    private static final String CON_STR = "jdbc:sqlite:ReferencePoints.sqlite";
    private static DbHandler instance = null;
    private final Connection connection;

    public static synchronized DbHandler getInstance() {
        if (instance == null) {
            try {
                instance = new DbHandler();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return instance;
    }

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    @Override
    public ArrayList<ReferencePoint> getAllPoints() {
        try (Statement statement = this.connection.createStatement()) {
            ArrayList<ReferencePoint> referencePoints = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT id, id_building, id_floor, name, x, y FROM reference_points");
            while (resultSet.next()) {
                referencePoints.add(new ReferencePoint(resultSet.getInt("id"),
                        resultSet.getInt("id_building"),
                        resultSet.getInt("id_floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")));
            }
            return referencePoints;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<ReferencePoint> getPointsByPartOfName(String part) {
        try (Statement statement = this.connection.createStatement()) {
            ArrayList<ReferencePoint> referencePoints = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT id, id_building, id_floor, name, x, y FROM reference_points WHERE reference_points.name LIKE '%" + part + "%'");
            while (resultSet.next()) {
                referencePoints.add(new ReferencePoint(resultSet.getInt("id"),
                        resultSet.getInt("id_building"),
                        resultSet.getInt("id_floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")));
            }
            return referencePoints;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int getFloorFromId(int id_floor) {
        return getFromId("number", "floors", id_floor);
    }

    @Override
    public int getBuildingFromId(int id_building) {
        return getFromId("number", "buildings", id_building);
    }

    private int getFromId(String field, String table, int id) {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT " + field + " FROM " + table + " WHERE id = " + id);
            if (resultSet.next())
                return resultSet.getInt(field);
            else
                return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<ReferencePoint> getStairs(int building, int floor) {
        ArrayList<ReferencePoint> stairs = new ArrayList<>();
        ArrayList<ReferencePoint> floorStairsList = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT reference_points.id, id_building, id_floor, name, x, y FROM reference_points INNER JOIN stairs ON (reference_points.id = stairs.id_point)");
            while (resultSet.next()) {
                stairs.add(new ReferencePoint(resultSet.getInt("id"),
                        resultSet.getInt("id_building"),
                        resultSet.getInt("id_floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")));
            }
            for (ReferencePoint stair : stairs)
                if (getFloorFromId(stair.getId_floor()) == floor)
                    floorStairsList.add(stair);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floorStairsList;
    }

    @Override
    public ArrayList<ReferencePoint> getFloorPoints(int building, int floor) {
        ArrayList<ReferencePoint> referencePoints = getAllPoints();
        ArrayList<ReferencePoint> newList = new ArrayList<>();
        for (ReferencePoint referencePoint : referencePoints)
            if (getBuildingFromId(referencePoint.getId_building()) == building && getFloorFromId(referencePoint.getId_floor()) == floor)
                newList.add(referencePoint);
        return newList;
    }

    @Override
    public void addPoint(ReferencePoint point) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO reference_points(`id_building`, `id_floor`, `name`, `x`, `y`) " +
                        "VALUES(?, ?, ?, ?, ?)")) {
            statement.setObject(1, point.getId_building());
            statement.setObject(2, point.getId_floor());
            statement.setObject(3, point.getName());
            statement.setObject(4, point.getX());
            statement.setObject(5, point.getY());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ReferencePoint> getConnectedPoints(ReferencePoint referencePoint) {
        ArrayList<ReferencePoint> referencePoints = getAllPoints();
        ArrayList<ReferencePoint> connectedPoints = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id_reference_point1, id_reference_point2 FROM connections WHERE id_reference_point1 = " + referencePoint.getId() + " OR id_reference_point2 = " + referencePoint.getId());
            while (resultSet.next()) {
                int id_rp1 = resultSet.getInt("id_reference_point1");
                int id_rp2 = resultSet.getInt("id_reference_point2");
                for (ReferencePoint point : referencePoints) {
                    if (id_rp1 == referencePoint.getId() && point.getId() == id_rp2) {
                        connectedPoints.add(point);
                    }
                    else if (id_rp2 == referencePoint.getId() && point.getId() == id_rp1) {
                        connectedPoints.add(point);
                    }
                }
            }
            return connectedPoints;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deletePoint(int x, int y) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM reference_points WHERE x = ? AND y = ?")) {
            statement.setObject(1, x);
            statement.setObject(2, y);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}