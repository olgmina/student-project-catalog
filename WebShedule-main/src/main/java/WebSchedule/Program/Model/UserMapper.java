package WebSchedule.Program.Model;



import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс UserMapper для отображения строк ResultSet в UserTable ({@link UserTable})
 * @version 1.0
 */
public class UserMapper implements RowMapper<UserTable> {
    /**
     * Функция маппинга результата запроса ResultSet в класс UserTable({@link UserTable})
     * @param rs {@link ResultSet}
     * @param rowNum int
     * @return Schedule {@link Schedule}
     * @throws SQLException sql исключение
     */
    @Override
    public UserTable mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long userId = rs.getLong("id");
        String userLogin = rs.getString("login");
        String encrytedPassword = rs.getString("password");
        return new UserTable(userId, userLogin, encrytedPassword);
    }

}
