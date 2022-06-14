package org.planworks.roadmap.selector;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.planworks.roadmap.adapters.Marks;
import org.planworks.roadmap.graphicElements.Mark;

/**
 * Область множественного выбора
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class selectorRect {
    private Rectangle selectRectangle;
    private boolean firstX=true,firstY=true;
    private double tempX;
    private double tempY;

    /** Начало выбора
     * @param mouseEvent событие мыши
     * @param workPane рабочая панель
     */
    public void onBegin(MouseEvent mouseEvent, Pane workPane) {//захват

            selectRectangle = new Rectangle();
            selectRectangle.setStroke(Color.BLUE);
            selectRectangle.setFill(Color.web("rgba(117, 114, 236, 0.2)"));
            selectRectangle.setX(mouseEvent.getSceneX()-105);
            tempX=selectRectangle.getX();
            selectRectangle.setY(mouseEvent.getSceneY()-26);
            tempY=selectRectangle.getY();
            workPane.getChildren().add(selectRectangle);
            selectRectangle.toFront();
    }

    /** Изменение размера области выделения
     * @param e событие мыши
     * @param marks метки
     * @param pane рабочая панель
     */
    public void onDrag(MouseEvent e,Marks marks,Pane pane)
    {
        if(e.getSceneX()>105  && e.getSceneX()-105<pane.getWidth()) {
            if (tempX > e.getSceneX()-105) {
                if (firstX)
                    tempX = selectRectangle.getX();
                firstX = false;
                selectRectangle.setX(e.getSceneX()-105);
                selectRectangle.setWidth(tempX - e.getSceneX()+105);
            } else {
                selectRectangle.setWidth(e.getSceneX()-105 - tempX);
            }
        }
        if( e.getSceneY()>25 && e.getSceneY()-25< pane.getHeight())
        {
            if (tempY > e.getSceneY()-25) {
                if (firstY)
                    tempY = selectRectangle.getY();
                firstY = false;
                selectRectangle.setY(e.getSceneY()-25);
                selectRectangle.setHeight(tempY - e.getSceneY()+25);
            } else {
                selectRectangle.setHeight(e.getSceneY()-25 - tempY);
            }

            for (Mark shape : marks.getAll()) {
                if (selectRectangle.contains(selectRectangle.sceneToLocal(shape.getNode().getBoundsInParent().getMaxX()+105,shape.getNode().getBoundsInParent().getMaxY()+25)))
                {
                    Momento temp = new Momento(shape);
                    if (!marks.getMemoSelect().contains(temp)) {
                        marks.getMemoSelect().push(temp);
                    }
                }
                else marks.getMemoSelect().remove(shape);
            }
        }
    }

    /** Окончание выделения
     * @param pane рабочая панель
     */
    public void onStop(Pane pane) {
        firstY=true;
        firstX=true;
        pane.getChildren().remove(selectRectangle);
    }

    /** Сброс выделенных объектов
     * @param marks метки
     */
    public void onEnd(Marks marks) {
        while (!marks.getMemoSelect().isEmpty())
            marks.getMemoSelect().poll().getState();
    }
}
