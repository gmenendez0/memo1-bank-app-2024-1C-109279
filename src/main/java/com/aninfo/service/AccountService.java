package com.aninfo.service;

import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public Optional<Transaction> findByTransactionId(Integer id) {
        return transactionRepository.findById(id);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        Transaction transaction = new Withdraw(account, sum);
        transaction.execute();

        accountRepository.save(account);
        transactionRepository.save(transaction);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        Transaction transaction = new Deposit(account, sum);
        transaction.execute();

        accountRepository.save(account);
        transactionRepository.save(transaction);

        return account;
    }

    @Transactional
    public Account deleteTransaction(long cbu, Integer id) throws AccountNotFoundException{
        Optional<Account> accountOptional = findById(cbu);

        if (accountOptional.isEmpty()) throw new AccountNotFoundException("Account not found");

        Account account = accountOptional.get();
        Optional<Transaction> transactionOptional = account.getTransactions().stream().filter(transaction -> transaction.getId().equals(id)).findFirst();

        //Se deberia crear una excepcion para este caso...
        if (transactionOptional.isEmpty()) throw new AccountNotFoundException("Transaction not found");

        Transaction transaction = transactionOptional.get();
        transaction.unexecute();
        save(account);
        transactionRepository.delete(transaction);

        return account;
    }
}
