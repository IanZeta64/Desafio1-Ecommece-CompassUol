package repositories;
import java.util.List;
import java.util.Optional;


public interface GenericRepository<T> {

    T save(T t);
    List<T> findAll();
    Optional<T> getById(Integer id);

}
