package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Admin;
import com.project.entity.Loan;
import com.project.entity.User;
import com.project.repository.AdminRepository;
import com.project.repository.LoanRepository;
import com.project.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    // ADMIN LOGIN
    public Admin loginAdmin(String email, String password) {
        return adminRepository.findByEmailAndPassword( email,password);

    }

    // VIEW ALL LOANS
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // APPROVE LOAN
    public void approveLoan(int id) {
        Loan loan = loanRepository.findById(id).get();
        loan.setStatus("APPROVED");
        loanRepository.save(loan);

    }

    public void rejectLoan(int id) {
        Loan loan = loanRepository.findById(id).get();
        loan.setStatus("REJECTED");
        loanRepository.save(loan);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
