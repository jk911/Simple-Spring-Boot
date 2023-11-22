package com.example.demo.repositories;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT u FROM Customer u WHERE u.fullName = :username AND u.password = :password")
    Optional<Customer> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u FROM Customer u WHERE u.fullName = :username")
    Optional<Customer> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM Customer u WHERE u.fullName = :username")
    Customer findByName(@Param("username") String username);
}
