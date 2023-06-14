import Entities.Customer;
import config.DatabaseConfig;
import repositories.CustomerRepository;
import repositories.impl.CustomerRepositoryImpl;
import java.time.LocalDate;

public class MainTestCostumer {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        CustomerRepository repository = new CustomerRepositoryImpl(new DatabaseConfig());

        Customer customer = new Customer("Agnes", LocalDate.of(1999,12,15), "987654321");

//        System.out.println(repository.save(customer));
//        var customer1 = repository.getById(1);
        var listCustomer = repository.selectAll();
        listCustomer.forEach(System.out::println);
//        System.out.println(customer1.get());
//        Customer customer2 = new Customer("Agnes2", LocalDate.of(2222, 12, 15), "987654321");
//        customer2.setId(7);
//        System.out.println(repository.update(customer2));

        repository.deleteById(9);
        var list = repository.selectAll();
        System.out.println(list);

    }
}