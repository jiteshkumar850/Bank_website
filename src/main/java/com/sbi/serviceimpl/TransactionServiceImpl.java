package com.sbi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbi.entity.Transaction;
import com.sbi.repository.TransactionRepository;
import com.sbi.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService 
{
	@Autowired
	private TransactionRepository transactionRepository;
	
	public void store(Transaction transaction) 
	{
		transactionRepository.save(transaction);
	}


	public List<Transaction> getList() 
	{
		List<Transaction> tlist=transactionRepository.findAll();
		return tlist;
	}


}
