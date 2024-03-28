package com.sbi.service;

import java.util.List;

import com.sbi.entity.Account;
import com.sbi.entity.User;

public interface UserService {


	User getReference(String id);

	Account register(User user);

	void updatePass(String confirmpass, String id);



	
}
