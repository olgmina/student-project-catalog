package org.planworks.roadmap.selector;

import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;
import org.planworks.roadmap.graphicElements.Mark;

/**
 * Состояние
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class Momento {
    private final Mark mark;

    /** Сделать снимок
     * @param state состояние
     */
    public Momento(Mark state){
        this.mark = state;
    }


    /** Вернуть первоначальный свойства
     * @return метка
     */
    public Mark getState(){
        mark.getNode().setEffect(null);
        mark.getNode().setOnMouseDragged(e -> mark.move(e.getSceneX()-130,e.getSceneY()-50));
        return mark;
    }

    /** Установить состояние
     * @return метка
     */
    public Mark initState() {
        Shadow shadow= new Shadow();
        shadow.setColor(Color.BLUE);
        shadow.setRadius(1);
        mark.getNode().setEffect(shadow);
        return mark;
    }

}
