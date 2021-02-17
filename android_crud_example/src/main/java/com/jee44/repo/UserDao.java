package com.jee44.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jee44.model.UserModel;

@Repository
@Transactional
public interface UserDao extends JpaRepository<UserModel, Long>{
	@Query("FROM UserModel WHERE username=:username")
	public UserModel getByUsername(@Param(value = "username") String username);
}
