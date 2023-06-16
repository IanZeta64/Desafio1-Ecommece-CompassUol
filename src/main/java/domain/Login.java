package domain;

import Entities.Customer;

public record Login (Boolean verify, String customerName, String customerDocument) {

}
