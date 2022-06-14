package WebSchedule.Program.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс SheduleMapper для отображения строк ResultSet в Schedule
 * @version 1.0
 */
public class SheduleMapper implements RowMapper<Schedule> {
    /**
     * Функция маппинга результата запроса ResultSet в класс Shedule({@link Schedule})
     * @param rs {@link ResultSet}
     * @param rowNum int
     * @return Schedule {@link Schedule}
     * @throws SQLException исключение sql
     */
    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getLong("id"));
        UserTable userTable = new UserTable();
        userTable.setId(rs.getLong("user"));
        UserDAO userDAO = new UserDAO();
        userTable= (UserTable) userDAO.getById(userTable.getId());
        schedule.setUser(userTable);
        schedule.setNameDay(rs.getString("NameDay"));
        schedule.setNameClasses(rs.getString("NameClasses"));
        schedule.setAuditorium(rs.getString("Auditorium"));
        schedule.setNumber(rs.getInt("number"));
        schedule.setDateStart(rs.getTimestamp("DateStart").toLocalDateTime());
        return schedule;
    }
}
