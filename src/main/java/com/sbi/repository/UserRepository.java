package com.sbi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbi.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,String> 
{

	@Modifying
	@Transactional
	@Query("update User set password=:arg where id=:arg1")
	void updatePass(@Param("arg")String confirmpass,@Param("arg1") String id);


	
}
