package WebSchedule.Program.Service;

import WebSchedule.Program.Model.UserTable;
import WebSchedule.Program.Model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс сервис используется для извлечения данных, связанных с пользователем для Spring Security
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserDAO appUserDAO;

    /**
     * Конуструктор UserDetailsServiceImpl
     */
    public UserDetailsServiceImpl(){
        appUserDAO = new UserDAO();
    }

    /**
     * Функция поиска Пользователя в БД
     * @param login {@link String}
     * @return UserDetails {@link UserDetails}
     * @throws UsernameNotFoundException исключение
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserTable appUser = this.appUserDAO.getByLogin(login);

        if (appUser == null) {
            System.out.println("User not found! " + login);
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appUserDAO.getRoleNames(appUser.getId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                System.out.println(role);
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }


        UserDetails userDetails = (UserDetails) new User(appUser.getLogin(), //
                appUser.getPassword(), grantList);

        return userDetails;
    }

}
