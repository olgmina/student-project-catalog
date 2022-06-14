package WebSchedule.Program.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SheduleDAOTest {
    private Schedule schedule;

    @MockBean
    private SheduleDAO scheduleDAO;

    @Test
    void add() {
        scheduleDAO = new SheduleDAO();
        schedule = new Schedule();
        schedule.setUser(new UserTable());
        schedule.setNameDay("Второй");
        scheduleDAO.add(schedule);
        Assert.assertNotNull(scheduleDAO.getByNameDay("Второй"));
    }

    @Test
    void getById() {
        long id;
        id=1;
        scheduleDAO = new SheduleDAO();
        Assert.assertNotNull(scheduleDAO.getById(id));
        id=10000;
        Assert.assertNull(scheduleDAO.getById(id));
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAll() {
        scheduleDAO = new SheduleDAO();
        Assert.assertNotNull(scheduleDAO.getAll());
        Assert.assertEquals(5, scheduleDAO.getAll().size());
    }
}