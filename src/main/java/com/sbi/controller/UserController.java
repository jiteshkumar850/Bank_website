package com.sbi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sbi.entity.Account;
import com.sbi.entity.Transaction;
import com.sbi.entity.User;
import com.sbi.service.AccountService;
import com.sbi.service.TransactionService;
import com.sbi.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("bank")
public class UserController 
{
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("register")
	private String register() 
	{
		return "/user/registration";
	}
	@RequestMapping("registration")
	private String registerSucess(User user,Model model,String password,String confirmpassword) 
	{
		User user1=userservice.getReference(user.getId());
		if(user1!=null) 
		{
			model.addAttribute("user",user.getId());
			model.addAttribute("msg","This user id already exists");
			return "/user/registration";
		}
		if(password.equals(confirmpassword)) 
		{
			Account ac=userservice.register(user);
			model.addAttribute("name",user.getName());
			model.addAttribute("acc",ac.getAccno());
			return "/user/reg-sucess";
		}
		else 
		{
		model.addAttribute("msg","both password not match");
		return "/user/registration";
		}
	}
	@RequestMapping("login")
	private String login() 
	{
		return "/user/login";
	}
	@RequestMapping("login-verify")
	private String loginVerify(String id,HttpServletRequest request,String password,HttpSession session,Model model) 
	{	
		User user=userservice.getReference(id);
		
		if(user==null) 
		{
			model.addAttribute("msg","Enter userid doesn't exist");
			return "/user/login";
		} 
		String dpass=user.getPassword();

		if(!password.equals(dpass)) 
		{
			model.addAttribute("msg","Password is Incorrect");			
			return "/user/login";
		}
		session=request.getSession();
		String userid=user.getId();
		session.setAttribute("userid", userid);
		Account account=accountService.get(userid);
		session.setAttribute("accname",account.getUsername());
		session.setAttribute("accno",account.getAccno());
		session.setAttribute("balance", account.getAmount());
		return "/user/home";
	}
	@RequestMapping("home")
	private String getHome(HttpSession session) 
	{
		if(session.getAttribute("userid")==null) 
		{
			return "/user/login";
		}
		else 
		{
			return "/user/home";
		}
	}
	@RequestMapping("showbalance")
	private String getShowBalance(HttpSession ses,Model model) 
	{
		int acno=(Integer)ses.getAttribute("accno");
		Account ac=accountService.getById(acno);
		model.addAttribute("acno",ac.getAmount());
		model.addAttribute("name",ac.getUsername());
		return "/user/show-balance";
	}
	@RequestMapping("deposit")
	private String getDeposit() 
	{
		return "/user/deposit";
	}
	@RequestMapping("deposit-sucess")
	private String getDepositSucess(int amount,int acno,HttpSession session,Model model) 
	{
		
		Transaction transac=new Transaction();
		transac.setAccountNo(acno);
		transac.setAccounttype("SAVING");
		transac.setAmounttype("credit");
		transac.setAmount(amount);
		transac.setOtheraccount(acno);
		transactionService.store(transac);
		Account a=accountService.getById(acno);
		int damt=a.getAmount();
		int ndmt=amount+damt;
		accountService.updateAmount(ndmt,acno);
		model.addAttribute("bal",amount);
		return "/transaction/deposit-sucess";
	}
	@RequestMapping("withdraw")
	private String getWithdraw() 
	{
		return "/transaction/withdraw";
	}
	@RequestMapping("withdraw-sucess")
	private String getWithdrawSucess(int amount,int acno,Model model)
	{
		Account acc=accountService.getById(acno);
		int oamount=acc.getAmount();
		if(amount>oamount) 
		{
			model.addAttribute("msg","you dont have sufficient balance to withdraw");
			return "/transaction/withdraw";
		}
		
			int n=oamount-amount;
			accountService.updateAmount(n,acno);
			Transaction transac=new Transaction();
			transac.setAccountNo(acno);
			transac.setAccounttype("SAVING");
			transac.setAmount(amount);
			transac.setAmounttype("debit");
			transac.setOtheraccount(acno);
			transactionService.store(transac);
			model.addAttribute("msg",amount);
			return "/transaction/withdraw-sucess";

		
	}
	@RequestMapping("transfer")
	private String transfer() 
	{
		return "/transaction/transfer";
	}
	
	@RequestMapping("transfer-sucess")
	private String transferSucess(int accountno,int acno,int amount,Model model) 
	{

		if(accountno==acno) 
		{
			model.addAttribute("msg","you don't transfer to Own account");
			return "/transaction/transfer";
		}
		Account senderac=accountService.getById(acno);
		int sender=senderac.getAmount();
		if(amount>sender)
		{
			model.addAttribute("msg","low balance");
			return "/transaction/transfer";
		}
		int newsenderamount=sender-amount;
		accountService.updateAmount(newsenderamount, acno);
		
		Transaction transac=new Transaction();
		transac.setAccountNo(acno);
		transac.setAccounttype("SAVING");
		transac.setAmount(amount);
		transac.setAmounttype("debit");
		transac.setOtheraccount(acno);
		transactionService.store(transac);

		
		Account receiveac=accountService.getById(accountno);
		if(receiveac==null) 
		{
			model.addAttribute("msg","account no Not exists");
			return "/transaction/transfer";
		}
		int receiver=receiveac.getAmount();
		int newreceiveramount=receiver+amount;	
		accountService.updateWithdraw(newreceiveramount, receiveac.getAccno());	
		
		Transaction transa=new Transaction();
		transa.setAccountNo(accountno);
		transa.setAccounttype("SAVING");
		transa.setAmount(amount);
		transa.setAmounttype("credit");
		transa.setOtheraccount(accountno);
		transactionService.store(transa);

		
		model.addAttribute("receiver",accountno);
		model.addAttribute("amount",amount);
		
		return "/transaction/transfer-sucess";
		
		
	}
	@RequestMapping("logout")
	private String logout(HttpSession session) 
	{
		session.invalidate();
		return "/user/login";
	}
	@RequestMapping("changepassword")
	private String changePassword() 
	{
		return "/password/password";
	}
	@RequestMapping("password-verify")
	private String verifyPassword(String oldpass,Model model,int acno) 
	{
		Account account=accountService.getById(acno);
		String userid=account.getUserid();
		User user=userservice.getReference(userid);
		String oldpassword=user.getPassword();
		if(oldpass.equals(oldpassword)) 
		{
			return "/password/confirm-password";
		}
		
			model.addAttribute("msg", "password is incoreect");
			return "/password/password";
	}
	@RequestMapping("password-change")
	private String passwordChange(int acno,String newpass,String confirmpass,Model model) 
	{
		Account acc=accountService.getById(acno);
		String id=acc.getUserid();
		User u=userservice.getReference(id);
		String p=u.getPassword();
		if(newpass.equals(p)) 
		{
			model.addAttribute("msg","Enter password different from as old");
			return "/password/confirm-password";	
		}
		
		if(newpass.equals(confirmpass)) 
		{
			
			userservice.updatePass(confirmpass,id);
			model.addAttribute("msg","Your password is sucessfully changed");
			return "/password/sucess";
		}
		model.addAttribute("msg","not match found");
		return "/password/confirm-password";
	}
	@RequestMapping("service")
	private String services() 
	{
		return "/user/services";
	}
	@RequestMapping("about")
	private String about() 
	{
		return "/user/about";
	}
	@RequestMapping("statement")
	private String statement(HttpSession session,Model model) 
	{
		int s=(Integer)session.getAttribute("accno");
		List<Transaction> tlist=transactionService.getList();
		model.addAttribute("tlist",tlist);
		return "/transaction/statement";
	}
	@RequestMapping("profile")
	private String profile(HttpSession session,Model model) 
	{
		int s=(Integer)session.getAttribute("accno");
		Account ac=accountService.getById(s);
		User user=userservice.getReference(ac.getUserid());
		model.addAttribute("ac",ac);
		model.addAttribute("user",user);
		return "/user/profile";
	}
}

