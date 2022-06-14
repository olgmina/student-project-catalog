package model;
import java.util.ArrayList;

public interface DataBaseInterface {
    ArrayList<ReferencePoint> getAllPoints();
    ArrayList<ReferencePoint> getPointsByPartOfName(String part);
    int getFloorFromId(int id_floor);
    int getBuildingFromId(int id_building);
    ArrayList<ReferencePoint> getStairs(int building, int floor);
    ArrayList<ReferencePoint> getFloorPoints(int building, int floor);
    void addPoint(ReferencePoint point);
    ArrayList<ReferencePoint> getConnectedPoints(ReferencePoint referencePoint);
    void deletePoint(int x, int y);
}
