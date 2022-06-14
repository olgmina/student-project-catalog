package com.example.editornetworkplan.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Класс для работы с файлами
 */
public class FileWorker {
    ArrayList<Element> C ;

    /**
     * Открытия изображения
     * @param p панели компоновки
     * @return ImageView
     */
    public ImageView OpenImg(Pane p){
        FileChooser fileChooser = new FileChooser(); fileChooser.setTitle("Открытие файла");
        fileChooser.setInitialFileName("new_image"); fileChooser.getExtensionFilters().add(new
                FileChooser.ExtensionFilter("Изображение", "*.png"));
        File loadImageFile=fileChooser.showOpenDialog(p.getScene().getWindow());
        Image image = new Image(loadImageFile.toURI().toString()); ImageView imV= new ImageView(image);
        imV.setFitHeight(p.getHeight()); imV.setFitWidth(p.getWidth());
        p.getChildren().add(imV);
        return imV;
    }


    public FileWorker(){
        C = new ArrayList<>();
        C.add(new Router(1,2,2,new Pane()));
        C.add(new Switch(1,2,2,new Pane()));
        C.add(new PC(1,2,2,new Pane()));
    }

    /**
     * Функция сохранения в файл
     * @param AOR ArrayList компанентов
     */
    public void Save(ArrayList<Element> AOR){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Date Files", "*.dat"));
        File file=fileChooser.showSaveDialog(null);
        if (file != null) {
            try(FileOutputStream fos=new FileOutputStream( file)) {
                for (var a:AOR) {
                    int NumClass=-1;
                    for (int i = 0; i < C.size(); i++) {
                        if(a.getClass().toString().equals(C.get(i).getClass().toString()))
                        { NumClass=i;
                            break;
                        }
                    }
                    byte[] buffer =(NumClass+" "+a.getX()+" "+a.getY()+"\n").getBytes();
                    fos.write(buffer);
                }

            } catch (IOException ex)
            {
                System.out.println(ex);
            }
        }
    }

    /**
     * Функция загрузки файла
     * @param pane панель
     * @return  ElementAdapter
     */
    public ElementAdapter Load(Pane pane) {
        ElementAdapter AOR = new ElementAdapter(pane);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузка содержимого аудитории в формате dat");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Date Files", "*.dat"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                FileInputStream fos = new FileInputStream(file);
                int i = -1;
                String s = "";
                while ((i = fos.read()) != -1) {
                    System.out.print((char) i);
                    s += (char) i;
                    if ((char) i == '\n') {
                        String[] temp = s.split(" ");
                        s = "";
                        var comp = C.get(Integer.parseInt(temp[0]));
                        comp.setX(Double.parseDouble(temp[1]));
                        comp.setY(Double.parseDouble(temp[2]));
                        AOR.add(comp.getX(), comp.getY(),Integer.parseInt(temp[0]));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AOR;
    }
    /** сохранение изображения
     * @param image сохраняемое изображение
     * @param outStream выходной поток
     * @throws IOException отсутствие выходного потока
     */
    private static void saveImage(Image image, ObjectOutputStream outStream) throws IOException {
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
     */
    public static void saveImagePNG(Pane pane)
    {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл...");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Date Files", "*.png"));
            File file=fileChooser.showSaveDialog(null);
            WritableImage writableImage = new WritableImage((int)pane.getWidth(),(int)pane.getHeight());
            pane.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
