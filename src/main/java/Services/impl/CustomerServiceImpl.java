package Services.impl;
import Entities.Customer;
import Services.CustomerService;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicatedCustomerException;
import repositories.CustomerRepository;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        try {
            if (customerRepository.selectAll().stream().anyMatch(cstmr -> cstmr.equals(customer))) {
                throw new DuplicatedCustomerException("Customer already registered, not saved in database.");
            }
            return customerRepository.insert(customer);
        } catch (DuplicatedCustomerException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.selectAll();
    }

    @Override
    public Customer getById(Integer id) throws CustomerNotFoundException {
        return customerRepository.selectById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found by id: %s", id)));
    }

    @Override
    public Customer update(Customer customer) {
        try {
            if (!existById(customer.getId())) throw new CustomerNotFoundException("Customer not found, can't update.");
            return customerRepository.update(customer);
        } catch (CustomerNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!existById(id)) throw new CustomerNotFoundException("Customer not found, can't delete.");
            customerRepository.deleteById(id);
        } catch (CustomerNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = customerRepository.selectById(id).stream().count();
        return cont > 0;
    }

    @Override
    public List<Customer> searchByNameContainsString(String name) {
        return customerRepository.selectAll().stream().filter(customer -> customer.getName().contains(name)).toList();
    }

    @Override
    public Boolean verifyRegister(String name, String document) {
        return customerRepository.selectAll().stream()
                .anyMatch(customer -> customer.getName().equalsIgnoreCase(name) && customer.getDocument().equals(document));
    }
}
