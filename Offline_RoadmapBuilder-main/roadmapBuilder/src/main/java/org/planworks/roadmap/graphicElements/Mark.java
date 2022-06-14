package org.planworks.roadmap.graphicElements;

import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.planworks.roadmap.adapters.MarkTasks;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * Метка
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class Mark implements Serializable {
    /**
     * Координата x
     */
    private double X;
    /**
     * Координата y
     */
    private double Y;
    /**
     * id
     */
    private final int id;
    /**
     * выбор даты
     */
    final transient private DatePicker datePicker;
    /**
     * дата
     */
    private String date;
    /**
     * список описаний
     */
    public final MarkTasks tasks;
    /**
     * контейнер
     */
    transient private final VBox container;
    /**
     * рабочая панель
     */
    transient private Pane pane;

    /** Конструктор метки
     * @param x координата x
     * @param y координата y
     * @param id id
     * @param workspace рабочее пространство
     * @param date дата
     */
    public Mark(double x, double y, int id, Pane workspace,String date)
    {
        container = new VBox();
        this.date=date;
        tasks=new MarkTasks(getId(),container);
        this.X = x-25;
        this.Y = y-50;
        this.id = id;
        ImageView node = new ImageView();
        datePicker= new DatePicker();
        if(!date.equals(""))
            datePicker.setValue(LocalDate.parse(date));
        datePicker.setMaxWidth(120);
        datePicker.setMinWidth(120);
        datePicker.setOnAction(e -> this.date=this.datePicker.getValue().toString());
        container.getChildren().add(node);
        container.getChildren().add(datePicker);
        draw(workspace, node);
    }

    /** Получить дату
     * @return дата
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Настройка метки
     * @param node изображение
     */
    private void markSetting(ImageView node)
    {
        container.setId(String.valueOf(id));
        node.setImage(new Image("imgs/mark.png"));
        node.setFitWidth(50);
        node.setFitHeight(50);
        container.setLayoutX(X);
        container.setLayoutY(Y);
    }

    /** Рисование метки
     * @param pane панель рисования
     */
    private void draw(Pane pane,ImageView node)
    {
        this.pane=pane;
        markSetting(node);
        pane.getChildren().add(container);
    }

    /** Получить объект
     * @return  объект
     */
    public VBox getNode()
    {
        return container;
    }

    /** перемещение метки
     * @param x координата x
     * @param y координата y
     */
    public void move(double x,double y)
    {
        if(x>0 && x<pane.getWidth()) {
            final double deltaX = x - this.X;
            container.setTranslateX(container.getTranslateX() + deltaX);
            this.X = x;
        }
        if(y>0 && y<pane.getHeight()){
            final double deltaY = y - this.Y;
            container.setTranslateY(container.getTranslateY() + deltaY);
            this.Y = y;
        }
    }

    /** Получить id
     * @return id
     */
    public int getId()
    {
        return this.id;
    }

    /** Получить x
     * @return x
     */
    public double getX()
    {
        return this.X;
    }

    /** Получить y
     * @return y
     */
    public double getY()
    {
        return this.Y;
    }

    /** Клонирование метки
     * @return Метка-клон
     * @throws CloneNotSupportedException недоступное клонирование
     */
    public Mark clone() throws CloneNotSupportedException {
        return (Mark) super.clone();
    }
}
