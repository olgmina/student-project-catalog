package WebSchedule.Program.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Класс для таблицы Schedule
 * @version 1.0
 */
public class Schedule implements Entity{
    /**
     * id
     */
    private Long id;
    /**
     * Пользователь
     */
    private UserTable user;
    /**
     * Название дня
     */
    private String NameDay;
    /**
     * Аудитория
     */
    private String Auditorium;
    /**
     * Название занятия
     */
    private String NameClasses;
    /**
     * Дата и время начала занятия
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime DateStart;
    /**
     * Номер недели
     */
    private int number;

    /**
     * Пустой конструктор
     */
    public Schedule(){}

    /**
     * Функция возвращает номер дня недели
     * @return номер дня недели
     */
    public int getNumber() {
        return number;
    }

    /**
     * Установить номер недели
     * @param number int
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     *Функция возвращает аудиторию
     * @return аудиторию
     */
    public String getAuditorium() {
        return Auditorium;
    }

    /**
     * Установить аудиторию
     * @param auditorium {@link String}
     */
    public void setAuditorium(String auditorium) {
        Auditorium = auditorium;
    }

    /**
     * Функция возвращает название занятия
     * @return номер дня недели
     */
    public String getNameClasses() {
        return NameClasses;
    }

    /**
     * Установить название занятия
     * @param nameClasses {@link String}
     */
    public void setNameClasses(String nameClasses) {
        NameClasses = nameClasses;
    }

    /**
     * Функция возвращает дату и время начала занятия
     * @return дату и время начала занятия
     */
    public LocalDateTime getDateStart() {
        return DateStart;
    }

    /**
     * Установить дату и время начала занятия
     * @param dateStart {@link LocalDateTime}
     */
    public void setDateStart(LocalDateTime dateStart) {
        DateStart = dateStart;
    }

    /**
     * Возвращает дату и время начала занятия
     * @return дату и время в формате для вывода в html
     */
    public String StringDate(){
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("eeee, MMM dd. yyyy.\nHH:mm");
        String formattedString = DateStart.format(formatter);
        return formattedString;
    }

    /**
     * Возвращает id записи расписания
     * @return id записи расписания
     */
    public Long getId() {
        return id;
    }
    /**
     * Установить id записи расписания
     * @param id {@link Long}
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Возвращает Пользователя редактировавшего расписание
     * @return user {@link UserTable}
     */
    public UserTable getUser() {
        return user;
    }

    /**
     * Установить Пользователя редактировавшего расписание
     * @param user {@link UserTable}
     */
    public void setUser(UserTable user) {
        this.user = user;
    }

    /**
     * Возвращает Название дня
     * @return Название дня
     */
    public String getNameDay() {
        return NameDay;
    }

    /**
     * Установить Название дня
     * @param nameDay {@link LocalDateTime}
     */
    public void setNameDay(String nameDay) {
        NameDay = nameDay;
    }

    /**
     * Возвращает название класса
     * @return название класса
     */
    @Override
    public String getType() {
        return "Shedule";
    }
}
