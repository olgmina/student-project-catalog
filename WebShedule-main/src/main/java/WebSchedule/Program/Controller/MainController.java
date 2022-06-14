package WebSchedule.Program.Controller;

import WebSchedule.Program.Exeption.RecordNotFoundException;
import WebSchedule.Program.Model.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер Spring MVC
 * @version 1.0
 */
@Controller
public class MainController {
    /**
     * Для взаимодействия  с таблицей Shedule в БД
     */
    SheduleDAO service;
    /**
     * Для взаимодействия с таблицей User в БД
     */
    public UserDAO user;

    /**
     * Функця для обработки GET запроса /
     * @param model {@link Model}
     * @return главную страницу  index.html
     */
    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public String getIndex(Model model)
    {
        return "index";
    }
    /**
     * Функця для обработки GET запроса /schedule
     * @param model {@link Model}
     * @return страницу расписания schedule.html
     */
    @RequestMapping(value = { "/schedule"}, method = RequestMethod.GET)
    public String getSchedule(Model model)
    {
        service = new SheduleDAO();
        List<Schedule> list = service.getAll();
        model.addAttribute("employees", list);
        return "schedule";
    }

    /**
     * Функця для обработки GET запроса /scheduleAdmin
     * @param model {@link Model}
     * @return страницу расписания scheduleAdmin.html
     */
    @RequestMapping(value = { "/scheduleAdmin"}, method = RequestMethod.GET)
    public String getScheduleAdmin(Model model)
    {
        service = new SheduleDAO();
        List<Schedule> list = service.getAll();
        model.addAttribute("employees", list);
        return "scheduleAdmin";
    }

    /**
     * Функця для обработки GET запроса /login
     * @param model {@link Model}
     * @return страницу входа login.html
     */
    @RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public String login(Model model)
    {
        return "login";
    }
    /**
     * Функця для обработки GET запросов /edit и /edit/{id}
     * @param id {@link Optional<Long>}
     * @param model {@link Model}
     * @return страницу изменения add-edit-employee.html
     * @throws RecordNotFoundException {@link RecordNotFoundException}
     */
    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editSheduleById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            Entity entity = new Schedule();
            entity = service.getById(id.get());
            model.addAttribute("schedule", entity);
        } else {
            model.addAttribute("schedule", new Schedule());
        }
        return "add-edit-employee";
    }

    /**
     * Функця для обработки GET запроса /delete/{id}
     * @param id {@link Optional<Long>}
     * @param model {@link Model}
     * @return страницу расписания schedule.html
     * @throws RecordNotFoundException {@link RecordNotFoundException}
     */
    @RequestMapping(path = "/delete/{id}")
    public String deleteSheduleById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        service.deleteById(id);
        return "redirect:/schedule";
    }

    /**
     * Функця для обработки GET запроса /logoutSuccessful
     * @param model {@link Model}
     * @return страницу расписания schedule.html
     */
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        return "logoutSuccessful";
    }

    /**
     * Функця для обработки POST запроса /createSchedule для добавления записи в расписание
     * @param schedule {@link Schedule}
     * @param principal {@link Principal}
     * @return GET запрос /schedule
     */
    @RequestMapping(path = "/createSchedule", method = RequestMethod.POST)
    public String createOrUpdateShedules(Schedule schedule, Principal principal)    {
        this.user = new UserDAO();
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        schedule.setUser(user.getByLogin(userName));
        Calendar cal = Calendar.getInstance();
        cal.set(schedule.getDateStart().getYear(),schedule.getDateStart().getMonthValue() -1, schedule.getDateStart().getDayOfMonth(),
                schedule.getDateStart().getHour(), schedule.getDateStart().getMinute(), schedule.getDateStart().getSecond());
        int numberWeek = cal.get(Calendar.WEEK_OF_YEAR);
        System.out.println(numberWeek);
        schedule.setNumber(numberWeek);
        service.add(schedule);
        return "redirect:/schedule";
    }

    /**
     * Функця для обработки GET запроса /register для регисрации
     * @param model {@link Model}
     * @return страницу регисрации register.html
     * @throws RecordNotFoundException {@link RecordNotFoundException}
     */
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerUser(Model model)    throws RecordNotFoundException {
        UserTable user =  new UserTable();
        model.addAttribute("user",user);
        return "register";
    }

    /**
     *  Функця для обработки POST запроса /createUser для регисрации нового пользователя
     * @param user {@link UserTable}
     * @return GET запрос /
     */
    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public String createUser(UserTable user)    {
        this.user = new UserDAO();
        this.user.add(user);
        return "redirect:/";
    }
}
