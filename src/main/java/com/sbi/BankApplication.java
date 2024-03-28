package com.sbi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sbi.entity.Account;
import com.sbi.entity.Transaction;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	@Bean
	public Account make() 
	{
		return new Account();
	}
	@Bean
	public Transaction makeBean() 
	{
		return new Transaction();
	}
}
