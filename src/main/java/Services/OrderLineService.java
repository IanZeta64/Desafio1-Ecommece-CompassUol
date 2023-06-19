package Services;

import Entities.OrderLine;

public interface OrderLineService extends GenericService<OrderLine> {

     Boolean existByProductIdAndCustomerId(Integer productId, Integer customerId);

     OrderLine findByProductIdAndCustomerId(Integer productId, Integer customerId);
}
