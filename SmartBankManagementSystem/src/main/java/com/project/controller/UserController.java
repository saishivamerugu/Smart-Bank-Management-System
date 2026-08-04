package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import com.project.entity.Loan;
import com.project.entity.Transaction;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.service.OtpService;
import com.project.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private UserRepository userRepository;

    // Registration Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Save User
    @PostMapping("/saveUser")
    public String saveUser(User user, HttpSession session, Model model) {
        // Generates and sends the otp
        int otp = otpService.generateOtp();
        otpService.sendOtp(user.getEmail(), otp);
        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", user);
        model.addAttribute("message", "OTP was Sent To Your Email");
        return "verifyOtp";
    }
    
    @PostMapping("/verifyOtp")
    public String verifyOtp(int otp, HttpSession session, Model model) {

        int generatedOtp = (int) session.getAttribute("otp");

        User user =
                (User) session.getAttribute("tempUser");

        if(otp == generatedOtp) {

            userService.registerUser(user);

            model.addAttribute(
                    "success",
                    "Registration Successful");

            return "login";

        } else {

            model.addAttribute(
                    "error",
                    "Invalid OTP");

            return "verifyOtp";

        }

    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    // LOGIN USER
    @PostMapping("/loginUser")
    public String loginUser(String email,
                            String password,
                            Model model,
                            HttpSession session) {

        User validUser = userService.loginUser(email, password);

        if(validUser != null) {

            session.setAttribute("loggedInUser", validUser);

            model.addAttribute("user", validUser);

            return "dashboard";

        } else {

            model.addAttribute("error", "Invalid Email or Password");

            return "login";
        }

    }
    
 // DEPOSIT MONEY
    @PostMapping("/deposit")
    public String depositMoney(double amount,
                               HttpSession session,
                               Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        User updatedUser = userService.depositMoney(user, amount);

        session.setAttribute("loggedInUser", updatedUser);

        model.addAttribute("user", updatedUser);

        model.addAttribute("success",
                "Money Deposited Successfully");

        return "dashboard";

    }

    // WITHDRAW MONEY
    @PostMapping("/withdraw")
    public String withdrawMoney(double amount,
                                HttpSession session,
                                Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        User updatedUser =
                userService.withdrawMoney(user, amount);

        if(updatedUser == null) {

            model.addAttribute("user", user);

            model.addAttribute("error",
                    "Insufficient Balance");

            return "dashboard";
        }

        session.setAttribute("loggedInUser", updatedUser);

        model.addAttribute("user", updatedUser);

        model.addAttribute("success",
                "Money Withdrawn Successfully");

        return "dashboard";

    }
 // TRANSFER MONEY
 
 // TRANSFER MONEY
    @PostMapping("/transfer")
    public String transferMoney(String receiverEmail,
                                double amount,
                                HttpSession session,
                                Model model) {

        // GET LOGGED USER
        User sender =
                (User) session.getAttribute(
                        "loggedInUser");

        // SESSION CHECK
        if(sender == null) {

            return "redirect:/login";

        }

        // TRANSFER
        String result =
                userService.transferMoney(
                        sender,
                        receiverEmail,
                        amount);

        // GET UPDATED USER
        User updatedUser =
                userRepository.findByEmail(
                        sender.getEmail());

        // UPDATE SESSION
        session.setAttribute(
                "loggedInUser",
                updatedUser);

        // SEND USER TO DASHBOARD
        model.addAttribute(
                "user",
                updatedUser);

        // SUCCESS / ERROR MESSAGE
        if(result.contains("Successful")) {

            model.addAttribute(
                    "success",
                    result);

        } else {

            model.addAttribute(
                    "error",
                    result);

        }

        return "dashboard";

    }
    
    // Mini Statement Method 
    @GetMapping("/statement")
    public String miniStatement(HttpSession session,
                                Model model) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser");

        if(user == null) {

            return "redirect:/login";

        }

        List<Transaction> transactions =
                userService.getMiniStatement(
                        user.getEmail());

        model.addAttribute(
                "transactions",
                transactions);

        return "statement";

    }
    
 // LOAN PAGE
    @GetMapping("/loan")
    public String loanPage() {

        return "loan";
    }

    // APPLY LOAN
    @PostMapping("/applyLoan")
    public String applyLoan(Loan loan,
                            HttpSession session,
                            Model model) {

        User user =
                (User) session.getAttribute("loggedInUser");

        userService.applyLoan(user, loan);

        model.addAttribute("success",
                "Loan Applied Successfully");

        return "loan";
    }

    // VIEW LOANS
    @GetMapping("/viewLoans")
    public String viewLoans(HttpSession session,
                            Model model) {

        User user =
                (User) session.getAttribute("loggedInUser");

        model.addAttribute("loans",
                userService.getUserLoans(user.getEmail()));

        return "viewloans";

    }

}
