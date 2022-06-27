package com.omrixyz.dfmh.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omrixyz.dfmh.model.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	@Modifying
	@Transactional
	@Query("UPDATE User user SET user.enabled = true WHERE user.email = :mail")
	void activateUser(String mail);

}
