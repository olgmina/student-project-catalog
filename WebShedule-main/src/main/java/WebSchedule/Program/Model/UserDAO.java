package WebSchedule.Program.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для взаимодействия с таблицы User  в БД.
 * @version 1.0
 */
public class UserDAO implements DAO{
    /**
     * Подключение к БД
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Конструктор, в котором устанавливается подключение к БД
     */
    public UserDAO(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/webschedule");
        ds.setUsername("root");
        ds.setPassword("");
        jdbcTemplate = new JdbcTemplate(ds);
    }
    /**
     * Фунция для получения количесва записей таблице User в БД
     * @return количесво записей таблице User в БД
     */
    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from type_week", Integer.class);
    }

    /**
     * Функция возвращает роль пользователя
     * @param userId id
     * @return роль
     */
    public List<String> getRoleNames(Long userId) {
        String sql = "";

        List<String> roles = jdbcTemplate.queryForList("Select r.Role_Name " +
                "from role_user ur, role r " +
                " where ur.Role_Id = r.id_Role and ur.User_Id = ? ", new Object[] { userId }, String.class);

        return roles;
    }
    /**
     * Добавляет запись в таблицу User
     * @param entity {@link Entity }
     */
    @Override
    public void add(Entity entity) {
        UserTable user = new UserTable();
        user= (UserTable) entity;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(user.getPassword());
        if(user.getId()  == null)
        {
            jdbcTemplate.update("INSERT INTO user (`id`, `login`,`password`) VALUES(NULL ,?,?)", user.getLogin(), encodedPassword);
        }
        else
        {
            update(entity);
        }

    }
    /**
     * Функция поиска по id в БД
     * @param id {@link Long }
     */
    @Override
    public Entity getById(Long id) {
        return jdbcTemplate.query("select * from user where id=?",new Object[]{id},new UserMapper())
                .stream().findAny().orElse(null);
    }
    /**
     *  Функция поиска по логину в БД
     * @param login {@link  String}
     * @return UserTable {@link  UserTable}
     */
    public UserTable getByLogin(String login) {
        return jdbcTemplate.query("select * from user where login=?",new Object[]{login},new UserMapper())
                .stream().findAny().orElse(null);
    }
    /**
     * Обновления записи в таблицу User
     * @param entity {@link Entity Entity.class}
     */
    @Override
    public void update(Entity entity) {
        UserTable user = new UserTable();
        user= (UserTable) entity;
        jdbcTemplate.update("UPDATE user SET login=? WHERE id=?", user.getLogin(), user.getId());
    }
    /**
     * Функция удаления записи в таблице User по id в БД
     * @param id {@link Long Long.class}
     */
    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM user WHERE id=?", id);
    }

    /**
     *  Функция для показа всех записей в таблице User в БД
     * @return List {@link List}
     */
    public List<UserTable> getAll() {
        return jdbcTemplate.query("select * from user",new BeanPropertyRowMapper<>(UserTable.class));
    }
}
