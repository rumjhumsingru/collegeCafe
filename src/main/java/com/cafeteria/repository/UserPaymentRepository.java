package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {

}
