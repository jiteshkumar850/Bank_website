package com.sbi.service;

import com.sbi.entity.Account;

public interface AccountService 
{

	Account create(Account account);

	Account get(String id);

	Account getById(int acno);

	void updateAmount(int ndmt, int acno);

	void updateWithdraw(int newreceiveramount, int acno);



}
