package repositories;
import java.util.List;
import java.util.Optional;
public interface GenericRepository<T> {
    T insert(T t);
    List<T> selectAll();
    Optional<T> selectById(Integer id);
    T update(T t);
    void deleteById(Integer id);
}
