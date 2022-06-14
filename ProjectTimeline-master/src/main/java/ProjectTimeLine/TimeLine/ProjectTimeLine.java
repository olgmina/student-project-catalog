package ProjectTimeLine.TimeLine;

/** Содержит всю информацию о карте проекта */
public class ProjectTimeLine {

    /** Содержит информацию о визуальном представлекнии фона */
    public UICanvas uiCanvas;

    /** Начальный (базовый) нод проекта */
    public Node startNode;

    /** Конструктор инициализирует UICanvas */
    public ProjectTimeLine () {

        uiCanvas = new UICanvas();
    }

}
