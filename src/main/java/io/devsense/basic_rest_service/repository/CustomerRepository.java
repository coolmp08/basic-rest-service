package io.devsense.basic_rest_service.repository;

import io.devsense.basic_rest_service.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    @Override
    Optional<Customer> findById(Long id);
}
