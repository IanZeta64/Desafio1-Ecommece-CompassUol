package Services;

import Entities.OrderLine;

public interface OrderLineService extends GenericService<OrderLine> {

     Boolean existByProductIdAndCustomerIdAndNotOrdered(Integer productId, Integer customerId);

     OrderLine findByProductIdAndCustomerId(Integer productId, Integer customerId);
}
