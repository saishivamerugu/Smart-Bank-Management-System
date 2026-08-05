package com.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction>
    findBySenderEmailOrReceiverEmailOrderByTransactionTimeDesc(String senderEmail, String receiverEmail);

    List<Transaction>
    findBySenderEmailAndTransactionTimeBetween(String email, LocalDateTime start, LocalDateTime end);

}
