package com.aninfo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "target_account_cbu")
    protected Account targetAccount;

    protected double amount;

    public Transaction(){
    }

    public Transaction(Account targetAccount, double amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;

        targetAccount.addTransaction(this);
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount.removeTransaction(this);
        this.targetAccount = targetAccount;
        targetAccount.addTransaction(this);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public abstract void execute();

    public abstract void unexecute();
}
