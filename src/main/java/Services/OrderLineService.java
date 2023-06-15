package Services;

import Entities.OrderLine;
import repositories.GenericRepository;

public interface OrderLineService extends GenericService<OrderLine> {

     Boolean existByProductId(Integer productId);

     OrderLine findByProductId(Integer productId);
}
