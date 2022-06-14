package WebSchedule.Program.Model;
/**
 * Класс для таблицы Schedule
 * @version 1.0
 */
public class UserTable implements Entity{
    /**
     * id
     */
    private Long id;
    /**
     * Логин
     */
    private String login;
    /**
     * Пароль
     */
    private String password;

    /**
     * Конструктор
     * @param id id пользователя
     * @param login login пользователя
     * @param password password пользователя
     */
    public UserTable(Long id, String login, String password){
        this.id=id;
        this.login=login;
        this.password=password;
    }

    /**
     * конструктор
     */
    public UserTable(){
    }

    /**
     * Возвращает id
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Установить id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает login
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     *Установить Логин
     * @param login {@link String String.class}
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает Пароль
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }
    /**
     *Установить Пароль
     * @param password {@link String String.class}
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Возвращает название класса
     * @return название класса
     */
    @Override
    public String getType() {
        return "User";
    }
}
