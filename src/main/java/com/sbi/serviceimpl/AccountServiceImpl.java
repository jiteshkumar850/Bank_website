package com.sbi.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbi.entity.Account;
import com.sbi.repository.AccountRepository;
import com.sbi.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService
{

	@Autowired
	private AccountRepository accountRepositpory;
	
	public Account create(Account account) 
	{
		Account acc=accountRepositpory.save(account);
		return acc;
	}
	public Account get(String id) 
	{
		Account acc=accountRepositpory.find(id);
		return acc;
	}
	public Account getById(int acno) 
	{
		Account acc=accountRepositpory.findAll(acno);
		return acc;
	}
	public void updateAmount(int ndmt, int acno) 
	{
			accountRepositpory.updateAmount(ndmt,acno);
	}
		
	public void updateWithdraw(int newreceiveramount, int acno) 
	{
		accountRepositpory.updatWithdraw(newreceiveramount,acno);
	}
	

}
