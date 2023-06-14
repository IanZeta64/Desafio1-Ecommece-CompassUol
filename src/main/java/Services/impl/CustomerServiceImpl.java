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
        if (customerRepository.selectAll().stream().anyMatch(cstmr -> cstmr.equals(customer))){
            throw  new DuplicatedCustomerException("Customer already registered");
        }else {
            return customerRepository.insert(customer);
        }
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.selectAll();
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.selectById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found by id: %s", id)));
    }

    @Override
    public Customer update(Customer customer) {
        if (existById(customer.getId())) {
            return customerRepository.update(customer);
        }else {
            throw new CustomerNotFoundException("Customer not found, can't update.");
        }
    }

    @Override
    public void delete(Integer id) {
        if (existById(id)) {
             customerRepository.deleteById(id);
        }else {
            throw new CustomerNotFoundException("Customer not found, can't delete.");
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
}
