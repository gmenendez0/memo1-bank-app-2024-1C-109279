package com.aninfo.model;

import com.aninfo.exceptions.InsufficientFundsException;

import javax.persistence.Entity;

@Entity
public class Withdraw extends Transaction{
    public Withdraw(Account targetAccount, double amount){
        super(targetAccount, amount);
    }

    public Withdraw(){
    }

    @Override
    public void execute(){
        if (targetAccount.getBalance() < amount) throw new InsufficientFundsException("Insufficient funds");

        //Account tendria que tener un metodo "withdraw" por buenas practicas, pero por simplicidad lo dejamos como esta...
        getTargetAccount().setBalance(getTargetAccount().getBalance() - getAmount());
    }

    @Override
    public void unexecute(){
        //Account tendria que tener un metodo "deposit" por buenas practicas, pero por simplicidad lo dejamos como esta...
        getTargetAccount().setBalance(getTargetAccount().getBalance() + getAmount());
        targetAccount.removeTransaction(this);
    }
}
