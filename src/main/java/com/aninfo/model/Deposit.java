package com.aninfo.model;

import com.aninfo.exceptions.DepositNegativeSumException;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction{
    public Deposit(Account targetAccount, double amount){
        super(targetAccount, amount);
    }

    public Deposit(){
    }

    @Override
    public void execute(){
        if (amount <= 0) throw new DepositNegativeSumException("Cannot deposit negative sums");

        //Account tendria que tener un metodo "deposit" por buenas practicas, pero por simplicidad lo dejamos como esta...
        getTargetAccount().setBalance(getTargetAccount().getBalance() + getAmount());
    }
}
