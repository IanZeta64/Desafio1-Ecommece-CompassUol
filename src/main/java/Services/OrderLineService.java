package Services;

import Entities.OrderLine;

public interface OrderLineService extends GenericService<OrderLine> {

     Boolean existByProductIdAndNotOrdered(Integer productId);

     OrderLine findByProductId(Integer productId);
}
