package pl.tomek.ordermanagement.feature.customer.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.feature.customer.exception.CustomerCreateValidatorException;
import pl.tomek.ordermanagement.feature.validation.ObjectsValidator;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectsValidator<CustomerCreate> validator;

    @Autowired
    CustomerServiceImpl(CustomerRepository customerRepository, ObjectsValidator<CustomerCreate> validator) {
        this.customerRepository = customerRepository;
        this.validator = validator;
    }

    @Override
    public Customer create(CustomerCreate customerCreate) {
        Set<String> violations = validator.validate(customerCreate);
        if (!violations.isEmpty()) {
            throw new CustomerCreateValidatorException(violations);
        }
        CustomerEntity customerEntity = CustomerEntity.of(customerCreate);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
        return savedCustomerEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer getById(UUID id) {
        return customerRepository.getReferenceById(id).toDomain();
    }

    @Override
    @Transactional
    public Set<Customer> getAll() {
        return customerRepository.findAll().stream().map(CustomerEntity::toDomain).collect(Collectors.toSet());
    }
}
