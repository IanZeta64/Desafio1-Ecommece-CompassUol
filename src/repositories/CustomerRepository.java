package repositories;

import Entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> getById(Integer id);
    Customer update(Customer customer);
    void deleteById(Integer id);

}
