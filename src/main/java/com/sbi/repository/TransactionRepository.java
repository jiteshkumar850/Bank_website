package com.sbi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

//	@Query("from Transaction where account_no=:arg")
//	Transaction searchAcc(@Param("arg")int s);


	
}
