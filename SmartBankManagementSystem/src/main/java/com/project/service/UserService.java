package com.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entity.Loan;
import com.project.entity.Transaction;
import com.project.entity.User;
import com.project.repository.LoanRepository;
import com.project.repository.TransactionRepository;
import com.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // REGISTER USER
    public User registerUser(User user) {

        user.setBalance(0.0);

        String encryptedPassword =
                passwordEncoder.encode(
                        user.getPassword());

        user.setPassword(encryptedPassword);

        return userRepository.save(user);

    }

    // LOGIN USER
    public User loginUser(String email,
                          String password) {

        User user =
                userRepository.findByEmail(email);

        if(user != null &&
                passwordEncoder.matches(
                        password,
                        user.getPassword())) {

            return user;

        }

        return null;

    }

    // DEPOSIT MONEY
    public User depositMoney(User user,
                             double amount) {

        user.setBalance(
                user.getBalance() + amount);

        Transaction transaction =
                new Transaction();

        transaction.setSenderEmail(
                user.getEmail());

        transaction.setReceiverEmail(
                user.getEmail());

        transaction.setTransactionType(
                "DEPOSIT");

        transaction.setAmount(amount);

        transaction.setTransactionTime(
                LocalDateTime.now());

        transaction.setFraudStatus("SAFE");

        transactionRepository.save(transaction);

        return userRepository.save(user);

    }

    // WITHDRAW MONEY
    public User withdrawMoney(User user,
                              double amount) {

        if(amount > user.getBalance()) {

            return null;

        }

        user.setBalance(
                user.getBalance() - amount);

        Transaction transaction =
                new Transaction();

        transaction.setSenderEmail(
                user.getEmail());

        transaction.setReceiverEmail(
                user.getEmail());

        transaction.setTransactionType(
                "WITHDRAW");

        transaction.setAmount(amount);

        transaction.setTransactionTime(
                LocalDateTime.now());

        transaction.setFraudStatus("SAFE");

        transactionRepository.save(transaction);

        return userRepository.save(user);

    }

    // TRANSFER MONEY
    public String transferMoney(User sender,
                                String receiverEmail,
                                double amount) {

        if(sender == null) {

            return "Session Expired Please Login Again";

        }

        if(amount <= 0) {

            return "Invalid Amount";

        }

        User receiver =
                userRepository.findByEmail(
                        receiverEmail);

        if(receiver == null) {

            return "Receiver Account Not Found";

        }

        if(sender.getEmail()
                .equals(receiverEmail)) {

            return "Cannot Transfer To Same Account";

        }

        if(amount > sender.getBalance()) {

            return "Insufficient Balance";

        }

        LocalDateTime start =
                LocalDateTime.now()
                        .toLocalDate()
                        .atStartOfDay();

        LocalDateTime end =
                LocalDateTime.now();

        List<Transaction> todayTransactions =
                transactionRepository
                        .findBySenderEmailAndTransactionTimeBetween(
                                sender.getEmail(),
                                start,
                                end);

        double dailyTotal = 0;

        for(Transaction t : todayTransactions) {

            if(t.getTransactionType()
                    .equals("TRANSFER")) {

                dailyTotal += t.getAmount();

            }

        }

        if(dailyTotal + amount > 100000) {

            return "Daily Transfer Limit Exceeded";

        }

        sender.setBalance(
                sender.getBalance() - amount);

        receiver.setBalance(
                receiver.getBalance() + amount);

        userRepository.save(sender);

        userRepository.save(receiver);

        Transaction transaction =
                new Transaction();

        transaction.setSenderEmail(
                sender.getEmail());

        transaction.setReceiverEmail(
                receiverEmail);

        transaction.setTransactionType(
                "TRANSFER");

        transaction.setAmount(amount);

        transaction.setTransactionTime(
                LocalDateTime.now());

        if(amount > 50000) {

            transaction.setFraudStatus(
                    "SUSPICIOUS");

        } else {

            transaction.setFraudStatus(
                    "SAFE");

        }

        transactionRepository.save(transaction);

        if(amount > 50000) {

            return "Transfer Successful (Fraud Alert Generated)";

        }

        return "Money Transfer Successful";

    }

    // MINI STATEMENT
    public List<Transaction>
    getMiniStatement(String email) {

        return transactionRepository
                .findBySenderEmailOrReceiverEmailOrderByTransactionTimeDesc(
                        email,
                        email);

    }

    // APPLY LOAN
    public Loan applyLoan(User user,
                          Loan loan) {

        loan.setCustomerName(
                user.getFullName());

        loan.setCustomerEmail(
                user.getEmail());

        loan.setStatus("PENDING");

        return loanRepository.save(loan);

    }

    // VIEW USER LOANS
    public List<Loan>
    getUserLoans(String email) {

        return loanRepository
                .findByCustomerEmail(email);

    }

}