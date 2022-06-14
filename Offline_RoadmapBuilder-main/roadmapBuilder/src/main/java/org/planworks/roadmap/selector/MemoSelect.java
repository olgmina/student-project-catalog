package org.planworks.roadmap.selector;

import javafx.scene.input.MouseEvent;
import org.planworks.roadmap.graphicElements.Mark;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Множественный выбор
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class MemoSelect {
    private final Queue<Momento> mementoList = new ArrayDeque<>();

    /** Добавить состояние
     * @param state состояние
     */
    public void push(Momento state){
        mementoList.add(state);
        state.initState().getNode().setOnMouseDragged(this::moveAll);
    }

    /** Получить массив оъектов
     * @return массив оъектов
     */
    public Object[] select()
    {
        return mementoList.toArray();
    }

    /** Получить и удалить состояние
     * @return состояние
     */
    public Momento poll(){
        return mementoList.poll();
    }

    /** Удалить состояние
     * @param mark метка
     */
    public void remove(Mark mark)
    {
        for(Momento m:mementoList)
            if(m.initState().equals(mark)){
                m.getState();
                mementoList.remove(m);
            break;
            }
    }
    private Momento peek()
    {
      return mementoList.peek();
    }

    /** Проверка наполнения
     * @return пустой или нет
     */
    public  boolean isEmpty()
    {
        return mementoList.isEmpty();
    }

    /** Проверка содержания элемента
     * @param momento состояние
     * @return содержание
     */
    public boolean contains(Momento momento)
    {
        for (Momento element:mementoList) {
            if(element.initState().getId()==momento.initState().getId())
                return true;
        }
        return false;
    }

    /** Премещение всех элементов
     * @param e событие мыши
     */
    private void moveAll(MouseEvent e)
    {
        double originX = mementoList.peek().initState().getX();
        double originY = mementoList.peek().initState().getY();
        mementoList.peek().initState().move(e.getSceneX()-130, e.getSceneY()-50);
            for (int i = 1; i < select().length; i++) {

                ((Momento) select()[i]).initState().move(e.getSceneX()- originX +((Momento) select()[i]).initState().getX()-130,
                        e.getSceneY()- originY + ((Momento) select()[i]).initState().getY()-50);
            }
    }
}
