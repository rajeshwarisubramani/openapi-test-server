package raje.springframework.apifirstserver.repositories;

import org.springframework.data.repository.CrudRepository;
import raje.springframework.apifirst.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
