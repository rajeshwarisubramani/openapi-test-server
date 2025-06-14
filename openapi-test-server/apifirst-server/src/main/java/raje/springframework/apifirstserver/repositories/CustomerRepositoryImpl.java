package raje.springframework.apifirstserver.repositories;

import org.springframework.stereotype.Repository;
import raje.springframework.apifirst.model.Address;
import raje.springframework.apifirst.model.Customer;
import raje.springframework.apifirst.model.PaymentMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

private final Map<UUID, Customer> entityMap = new HashMap<>();
    @Override
    public <S extends Customer> S save(S entity) {
        UUID id = UUID.randomUUID();

        Customer.CustomerBuilder builder1 = Customer.builder();
        builder1.id(id);

        if(entity.getBillToAddress() != null){
            builder1.billToAddress(Address.builder()
                    .addressLine1(entity.getBillToAddress().getAddressLine1())
                    .addressLine2(entity.getBillToAddress().getAddressLine2())
                    .city(entity.getBillToAddress().getCity())
                    .state(entity.getBillToAddress().getState())
                    .zip(entity.getBillToAddress().getZip())
                    .build());
        }

        if (entity.getShipToAddress() != null) {
            builder1.shipToAddress(Address.builder()
                    .addressLine1(entity.getShipToAddress().getAddressLine1())
                    .addressLine2(entity.getShipToAddress().getAddressLine2())
                    .city(entity.getShipToAddress().getCity())
                    .state(entity.getShipToAddress().getState())
                    .zip(entity.getShipToAddress().getZip())
                    .build());
        }

        if(entity.getPaymentMethods() != null) {
            builder1.paymentMethods(entity.getPaymentMethods()
                    .stream()
                    .map(paymentMethod -> PaymentMethod.builder()
                            .id(paymentMethod.getId())
                            .displayName(paymentMethod.getDisplayName())
                            .cardNumber(paymentMethod.getCardNumber())
                            .expiryMonth(paymentMethod.getExpiryMonth())
                            .expiryYear(paymentMethod.getExpiryYear())
                            .cvv(paymentMethod.getCvv())
                            .dateCreated(paymentMethod.getDateCreated())
                            .dateUpdated(paymentMethod.getDateUpdated())
                            .build())
                    .collect(Collectors.toList())
            );

          }
        Customer customer = builder1.email(entity.getEmail())
                .name(entity.getName())
                .build();
        entityMap.put(id, customer);
        return (S) customer;

    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        return Optional.of(entityMap.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return (entityMap.get(uuid) != null);
    }

    @Override
    public Iterable<Customer> findAll() {
        return entityMap.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<UUID> uuids) {
        return StreamSupport.stream(uuids.spliterator(), false)
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return entityMap.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        entityMap.remove(uuid);
    }

    @Override
    public void delete(Customer entity) {
        entityMap.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
    uuids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
    entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entityMap.clear();
    }
}
