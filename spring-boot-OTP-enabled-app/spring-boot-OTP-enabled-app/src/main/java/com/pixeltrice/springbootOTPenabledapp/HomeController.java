package com.pixeltrice.springbootOTPenabledapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//When the API “/” or localhost:8080 called then 
//the method homePage() will get executed and it will redirect to the signin page.
//Since we already enabled the thymeleaf template in our application with the use of the following property in the application.properties file.

//spring.thymeleaf.enabled=true
//spring.thymeleaf.prefix=classpath:/templates/
//spring.thymeleaf.suffix=.html

//we also configured the prefix and suffix.
//So, whenever any method returns the String value, the prefix and suffix will automatically add to that.
//For example in the above homePage() method, it is returning the signin,
//but actually it returning the localhost:8080/signin.html.

//Once the user will redirect to the page signin.html,
//then along with the application name, the message will also be displayed.

@Controller
public class HomeController {
	
    @Value("${spring.application.name}")// dynamically setting the value of appName from a properties file;
    String appName;
    
    @Autowired
    public OTPService otpService;
    
    @GetMapping("/")
    public String homePage(Model model) {
    	String message = " Welcome to Home Page";
        model.addAttribute("appName", appName);
        model.addAttribute("message", message);
        return "signin";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(){
    	return "dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "signin";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/user")
    public String user() {
        return "user";
    }
    
    @GetMapping("/aboutus")
    public String about() {
        return "aboutus";
    }
    
    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
       if (auth != null){    
	       String username = auth.getName();
	       //Remove the recently used OTP from server. 
	       otpService.clearOTP(username);
	       new SecurityContextLogoutHandler().logout(request, response, auth);
	       //The above line of code used to completely logged out the user from the application.
       }
       return "redirect:/login?logout";    
    }
    
}
