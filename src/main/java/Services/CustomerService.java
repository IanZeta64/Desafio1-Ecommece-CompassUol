package Services;

import Entities.Customer;
import Entities.Product;

import java.util.List;

public interface CustomerService extends GenericService<Customer>{

    List<Customer> searchByNameContainsString(String name);

}
