package com.czhang.dashboard.dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.czhang.dashboard.dashboard.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
	
	User findByUsername(String userName);

}
