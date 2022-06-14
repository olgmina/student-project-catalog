package WebSchedule.Program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Класс для запуска программы
 */
@SpringBootApplication
public class ProgramApplication {
	/**
	 * Главная функция программы
	 * @param args {@link String}
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProgramApplication.class, args);
	}

}
