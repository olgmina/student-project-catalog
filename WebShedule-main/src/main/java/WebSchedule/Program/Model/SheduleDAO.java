package WebSchedule.Program.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Класс для взаимодействия с таблицей Schedule в БД
 * @version 1.0
 */
public class SheduleDAO implements DAO {
    /**
     * Подключение к БД
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Конструктор, в котором устанавливается подключение к БД
     */
    public SheduleDAO(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/webschedule");
        ds.setUsername("root");
        ds.setPassword("");
        jdbcTemplate = new JdbcTemplate(ds);
    }

    /**
     * Фунция для получения количесва записей таблице Schedule в БД
     * @return количесво записей таблице Schedule в БД
     */
    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from schedule", Integer.class);
    }

    /**
     * Добавляет запись в таблицу Schedule
     * @param entity {@link Entity Entity.class}
     */
    @Override
    public void add(Entity entity) {
        Schedule typeWeek = new Schedule();
        SheduleDAO sheduleDAO = new SheduleDAO();
        sheduleDAO.getByNameDay(typeWeek.getNameDay());
        typeWeek= (Schedule) entity;
        if(typeWeek.getId()  == null)
        {
            jdbcTemplate.update("INSERT INTO schedule (`id`, `user`, `DateStart`, `NameClasses`, `Auditorium`, `number`, `NameDay`) " +
                            "VALUES(NULL ,?,?,?,?, ?, ?)",
                    typeWeek.getUser().getId(),
                    typeWeek.getDateStart(),typeWeek.getNameClasses(), typeWeek.getAuditorium(), typeWeek.getNumber(), typeWeek.getNameDay());
        }
        else
        {
            update(entity);
        }
    }
    /**
     * Функция поиска по id в БД
     * @param id {@link Long Long.class}
     */
    @Override
    public Entity getById(Long id) {
        return jdbcTemplate.query("select * from schedule where id=?",new Object[]{id},new SheduleMapper())
                .stream().findAny().orElse(null);
    }

    /**
     *  Функция поиска по Названию дня в БД
     * @param str {@link  String String.class}
     * @return  Entity {@link Entity Entity.class} или null
     */
    public Entity getByNameDay(String str){
        return jdbcTemplate.query("select * from schedule where NameDay=?",new Object[]{str},new SheduleMapper())
                .stream().findAny().orElse(null);
    }

    /**
     * Обновления записи в таблицу Schedule
     * @param entity {@link Entity Entity.class}
     */
    @Override
    public void update(Entity entity) {
        Schedule typeWeek = new Schedule();
        typeWeek= (Schedule) entity;
        if (typeWeek.getId() != null){
            return;
        }
        jdbcTemplate.update("UPDATE schedule SET NameDay=?, NameClasses=?, user=?, DateStart=?, Auditorium=?, number=? WHERE id=?",
                typeWeek.getNameDay(), typeWeek.getNameClasses(), typeWeek.getUser().getId(),
                typeWeek.getDateStart(), typeWeek.getAuditorium(), typeWeek.getNumber(), typeWeek.getId());
    }
    /**
     * Функция удаления записи в таблице Schedule по id в БД
     * @param id {@link Long Long.class}
     */
    @Override
    public void deleteById(Long id) {
       jdbcTemplate.update("DELETE FROM schedule WHERE id=?", id);
    }

    /**
     *  Функция для показа всех записей в таблице Schedule в БД
     * @return List {@link List}
     */
    public List<Schedule> getAll() {
        return jdbcTemplate.query("select * from schedule as s JOIN user as u ON u.id=s.user order by DateStart",new SheduleMapper());
    }
}
