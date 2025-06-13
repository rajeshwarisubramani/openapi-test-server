package raje.springframework.apifirstserver.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import raje.springframework.apifirst.model.Address;
import raje.springframework.apifirst.model.Customer;
import raje.springframework.apifirst.model.Name;
import raje.springframework.apifirst.model.PaymentMethod;
import raje.springframework.apifirstserver.repositories.CustomerRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Dataloader implements CommandLineRunner {


    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading data...");
        Address address1 = Address.builder()
                .addressLine1("123 Main St")
                .city("Springfield")
                .state("IL")
                .zip("62701")
                .build();

        Customer customer1 = Customer.builder()
                .name(Name.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .shipToAddress(address1)
                .billToAddress(address1)
                .email("johm@doe.com")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .cardNumber(411111)
                                .expiryMonth(05)
                                .expiryYear(2025)
                        .build()))
                .build();

        Address address2 = Address.builder()
                .addressLine1("123 Main St")
                .city("Springfield")
                .state("IL")
                .zip("62701")
                .build();

        Customer customer2 = Customer.builder()
                .name(Name.builder()
                        .firstName("Jamie")
                        .lastName("Jackson")
                        .build())
                .shipToAddress(address2)
                .billToAddress(address2)
                .email("jamie@jackson.com")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .cardNumber(415511)
                        .expiryMonth(05)
                        .expiryYear(2025)
                        .build()))
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }
}
