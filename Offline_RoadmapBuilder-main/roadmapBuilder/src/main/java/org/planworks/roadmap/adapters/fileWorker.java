package org.planworks.roadmap.adapters;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.planworks.roadmap.graphicElements.Mark;
import org.planworks.roadmap.graphicElements.MarkTask;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;

/**
 * Класс для работы с файлами
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class fileWorker {
    /** сохранение файл
     * @param file сохраняемый файл
     * @param image нарисованные объекты
     * @param marks метки
     * @param height высота
     * @param width ширина
     */
    public static void saveFile(File file, Image image, Marks marks, double width,double height)
    {
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream outStream = new ObjectOutputStream(fos);
            outStream.writeDouble(width);
            outStream.writeDouble(height);
            saveImage(image,outStream);
            outStream.writeObject(marks);
            outStream.flush();
            outStream.close();
            System.out.println("Сохранено!");
        }catch(Exception e)
        {
            System.out.println("Error"+e.getMessage());
        }
    }

    /** загрузка файла
     * @param file загружаемый файл
     * @param background задний фон
     * @param marks метки
     * @param pane рабочая панель
     */
    public static void loadFile(File file,BackgroundImage background,Marks marks, Pane pane) {
        try {
            Marks tempmarks;
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            pane.setPrefWidth(inputStream.readDouble());
            pane.setPrefHeight(inputStream.readDouble());
            Image image = getImage(inputStream);
            tempmarks = (Marks) inputStream.readObject();
            inputStream.close();
            if(image!=null) {
                background = new BackgroundImage(image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
                pane.setBackground(new Background(background));
            }
            marks.clear();
            for (Mark mark : tempmarks.getAll()) {
                Mark temp = marks.add(mark.getX(), mark.getY()+40,mark.getDate());
                for (MarkTask markTask : mark.tasks.getAllTasks())
                    temp.tasks.add(markTask.getText());
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    /** сохранение изображения
     * @param image сохраняемое изображение
     * @param outStream выходной поток
     * @throws IOException отсутствие выходного потока
     */
    private static void saveImage(Image image,ObjectOutputStream outStream) throws IOException {
        int width = ((int) image.getWidth());
        int height = ((int) image.getHeight());

            int[][] data = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = r.getArgb(i, j);
            }
        }
        outStream.writeInt(width);
        outStream.writeInt(height);
        for(int i=0;i<width;i++)
            for (int j=0;j<height;j++)
                outStream.writeInt(data[i][j]);
    }

    /** получение изображения
     * @param inputStream входной поток
     * @return полученное изображение
     * @throws IOException отсутствие входного потока
     */
    private static Image getImage(ObjectInputStream inputStream) throws IOException {
        int width=inputStream.readInt();
        int height=inputStream.readInt();
        if(width!=0 && height!=0) {
            WritableImage img = new WritableImage(width, height);
            int[][] data = new int[width][height];
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    data[i][j] = inputStream.readInt();
            PixelWriter w = img.getPixelWriter();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    w.setArgb(i, j, data[i][j]);
                }
            }
            return img;
        }
        return null;
    }

    /** сохранение в PNG
     * @param pane панель с изображением
     * @param file файл сохранения
     */
    public static void saveImagePNG(Pane pane,File file)
    {
        try {
            WritableImage writableImage = new WritableImage((int)pane.getWidth(),(int)pane.getHeight());
            pane.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    /** сохранение в PNG
     * @param pane панель с изображением
     * @param marks метки
     * @return изображение
     */
    public static Image saveImagePNG(Pane pane, Marks marks, Canvas canvas)
    {
        for (Mark mark:marks.getAll()) {
            mark.getNode().setVisible(false);
        }
        canvas.setVisible(false);
        WritableImage writableImage = new WritableImage((int)pane.getWidth(),(int)pane.getHeight());
        pane.snapshot(null, writableImage);
        for (Mark mark:marks.getAll()) {
            mark.getNode().setVisible(true);
        }
        canvas.setVisible(true);
        return writableImage;
    }
}
