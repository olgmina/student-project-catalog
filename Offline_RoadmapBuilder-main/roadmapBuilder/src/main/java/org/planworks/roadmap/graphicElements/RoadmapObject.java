package org.planworks.roadmap.graphicElements;

import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.planworks.roadmap.adapters.Marks;

/**
 * Класс объекта дорожной карты
 */
public class RoadmapObject {

    /**
     * Дорога
     */
    Road road;
    /**
     * Список меток
     */
    Marks marks;

    /**
     * @param workspace рабочее пространство
     * @param canvas область рисования
     * @param selection режим выбора
     * @param opened файл открыт или создан
     */
    public RoadmapObject(Pane workspace, Canvas canvas, BooleanProperty selection,Boolean opened)
    {
        this.road = new Road(canvas,selection);
        this.marks= new Marks(workspace);
        if(!opened) {
            road.setColor(Color.BLACK);
            road.setSize(10);
            for (int i = 0; i < canvas.getHeight(); i++)
                road.draw(i, i, false);
            marks.add(60, 60, "");
            marks.add(200, 200, "");
            marks.add(300, 300, "");
        }
    }

    /**
     * @return дорога
     */
    public Road getRoad()
    {
        return road;
    }

    /**
     * @return список меток
     */
    public Marks getMarks()
    {
        return marks;
    }
}
