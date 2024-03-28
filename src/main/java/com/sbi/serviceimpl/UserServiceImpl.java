package com.sbi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbi.entity.Account;
import com.sbi.entity.User;
import com.sbi.repository.AccountRepository;
import com.sbi.repository.UserRepository;
import com.sbi.service.AccountService;
import com.sbi.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private AccountService accountService;
	
	public User getReference(String id) 
	{
		User user=userRepository.findById(id).orElse(null);
		return user;
	}

	public Account register(User user) 
	{
		userRepository.save(user);
		Account account=new Account();
		account.setUserid(user.getId());
		account.setUsername(user.getName());
		Account acc=accountService.create(account);
		return acc;
	}
	public void updatePass(String confirmpass,String id) 
	{
		userRepository.updatePass(confirmpass,id);
	}



	


}
