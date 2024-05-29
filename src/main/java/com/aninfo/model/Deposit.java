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

        //Deberia usarse una constante y ubicar la promo en otro fragmento de codigo por mas prolijidad...
        if(amount >= 2000){
            int promo = (int) (amount * 0.1);
            if (promo > 500) promo = 500;

            getTargetAccount().setBalance(getTargetAccount().getBalance() + promo);
        }
    }

    @Override
    public void unexecute(){
        //Account tendria que tener un metodo "withdraw" por buenas practicas, pero por simplicidad lo dejamos como esta...
        getTargetAccount().setBalance(getTargetAccount().getBalance() - getAmount());
        targetAccount.removeTransaction(this);

        //Deberia usarse una constante y ubicar la promo en otro fragmento de codigo por mas prolijidad...
        if(amount >= 2000){
            int promo = (int) (amount * 0.1);
            if (promo > 500) promo = 500;

            getTargetAccount().setBalance(getTargetAccount().getBalance() - promo);
        }
    }
}
