package WebSchedule.Program.Model;

import java.util.List;
/**
 * Интерефейс для DAO объектов
 * @version 1.0
 */
public interface DAO {
    /**
     * Функция count
     * @return возвращает количество строк в таблице
     */
    public int count();
    /**
     * Функция добавления в БД
     * @param entity {@link Entity} запись
     */
    public void add(Entity entity);
    /**
     * Функция поиска по id в БД
     * @param id {@link Long Long.class}
     * @return entity {@link Entity} запись
     */
    public Entity getById(Long id);
    /**
     * Функция обновления в БД
     * @param entity {@link Entity} запись
     */
    public void update(Entity entity);
    /**
     * Функция удаления по id в БД
     * @param id id
     */
    public void deleteById(Long id);

}
