package Services;
import Entities.Customer;
import java.util.List;

public interface CustomerService extends GenericService<Customer>{

    List<Customer> searchByNameContainsString(String name);

    Boolean verifyRegister(String name, String document);

}
