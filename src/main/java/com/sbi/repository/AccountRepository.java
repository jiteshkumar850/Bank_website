package com.sbi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbi.entity.Account;

import jakarta.transaction.Transactional;

public interface AccountRepository extends JpaRepository<Account,Integer> 
{

	@Query("from Account where userid=:arg")
	Account find(@Param("arg")String id);

	@Query("from Account where accno=:arg")
	Account findAll(@Param("arg")int acno);

	@Modifying
	@Transactional
	@Query("update Account set amount=:arg where accno=:arg1")
	void updateAmount(@Param("arg")int ndmt,@Param("arg1")int acno);

	@Modifying
	@Transactional
	@Query("update Account set amount=:arg where accno=:arg1")
	void updatWithdraw(@Param("arg")int newreceiveramount,@Param("arg1")int acno);

	

	
}




