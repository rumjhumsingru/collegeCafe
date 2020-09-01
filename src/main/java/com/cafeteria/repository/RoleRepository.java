package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
