package com.sbi.service;

import java.util.List;

import com.sbi.entity.Transaction;

public interface TransactionService {

	void store(Transaction transaction);

	List<Transaction> getList();

}
