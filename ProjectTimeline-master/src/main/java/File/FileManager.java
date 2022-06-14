package File;

import javafx.scene.control.Label;

/** Класс по работе с сохранением/загрузкой проекта.
 * Инициализирует и предоставляет доступ к соответствующим интерфесам.  */
public class FileManager {

    private IFileSave fileSave;
    private IFileLoad fileLoad;

    /** Конструктор.
     * Инициализирует интерфейсы загрузки/сохранения.
     * Принимает Label для вывода информации в ходе работы программы */
    public FileManager (Label infoLabel) {
        fileSave = new FileSave(infoLabel);
        fileLoad = new FileLoad(infoLabel);
    }

    /** Метод доступа к интерфейсу сохранения */
    public IFileSave getFileSave () {
        return fileSave;
    }

    /** Метод доступа к интерфейсу загрузки */
    public IFileLoad getFileLoad () {
        return fileLoad;
    }
}
