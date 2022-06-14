package WebSchedule.Program.Configuration;

import WebSchedule.Program.Model.UserDAO;
import WebSchedule.Program.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Класс конфигурации аутентификации для Spring Security
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsServiceImpl userDetailsService;

    /**
     * Конструктор
     * @param userDetailsService {@link UserDetailsServiceImpl}
     */
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Baen
     * @return bCryptPasswordEncoder {@link BCryptPasswordEncoder}
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    /**
     * Функция для создания сведений об аутентификации
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception исключение
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Функция для конфигурации аунтификации по протоколу HTTP
     * @param http {@link HttpSecurity}
     * @throws Exception исключение
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers( "/","/login", "/logout", "/register").permitAll();
        http.authorizeRequests().antMatchers("/schedule").hasAnyAuthority("User", "Admin");
        http.authorizeRequests().antMatchers("/scheduleAdmin").hasRole("Admin");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")//
                .defaultSuccessUrl("/schedule")//
                .failureUrl("/login?error=true")//
                .usernameParameter("login")//
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }
}
