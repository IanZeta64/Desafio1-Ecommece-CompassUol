package repositories;

import Entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
    Customer insert(Customer customer);
    List<Customer> selectAll();
    Optional<Customer> selectById(Integer id);
    Customer update(Customer customer);
    void deleteById(Integer id);

}
