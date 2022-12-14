package com.emb.ecom.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emb.ecom.model.User;
import com.emb.ecom.service.UserService;
import com.emb.ecom.global.GlobalData;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {

	@Autowired
	UserService userService;
	
	private static final Logger log = LogManager.getLogger(AuthController.class);
	
	{
		BasicConfigurator.configure();
	}	

	@GetMapping({"/", "/home"})
    public String homePage(Model model){
		//model.addAttribute("cartCount", GlobalData.cart.size());
		return "/homepage";		
    }
    
    @GetMapping("/login")
    public String login(){
    	//GlobalData.cart.clear();
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
    		BindingResult bindingResult, Model model){
    	try {
	        if(bindingResult.hasErrors()){
	        	model.addAttribute("bindingResult", bindingResult);
	            //model.addAttribute("successMessage", "Fill in all details!");
	            return "auth/register";
	        }
	        List<Object> userPresentObj = userService.isUserPresent(user);
	        if((Boolean) userPresentObj.get(0)){
	            model.addAttribute("successMessage", userPresentObj.get(1));
	            return "auth/register";
	        }
	        userService.saveUser(user);
	        model.addAttribute("successMessage", "User registered successfully!");
	        return "auth/register";
    	}catch(Exception e) {
    		log.error(e);
			model.addAttribute("successMessage", "Something went wrong!");
            }
		return "auth/register";
    }
    
    @GetMapping("/aboutus")
    public String aboutUs(){
        return "/aboutus";
    }
}
