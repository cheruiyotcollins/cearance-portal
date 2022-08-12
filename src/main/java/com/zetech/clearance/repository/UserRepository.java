package com.zetech.clearance.repository;

import com.zetech.clearance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
