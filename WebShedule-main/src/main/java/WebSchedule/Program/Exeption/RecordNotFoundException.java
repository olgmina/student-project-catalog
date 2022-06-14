package WebSchedule.Program.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Ошибка не нахождения записи в бд
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Конструктор для RecordNotFoundException для передачи message исключения
     * @param message сообщение
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
    /**
     * Конструктор для RecordNotFoundException для передачи message исключения
     * @param message сообщение {@link String}
     * @param t стек-трейс {@link Throwable}
     */
    public RecordNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}