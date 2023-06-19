package Services;
import java.util.List;

public interface GenericService<T> {
    T save(T t);
    List<T> getAll();
    T getById(Integer id);
    T update(T t);
    void delete(Integer id);
    Boolean existById(Integer id);
}
