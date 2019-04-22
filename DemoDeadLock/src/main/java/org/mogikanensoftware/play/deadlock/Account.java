package org.mogikanensoftware.play.deadlock;

public class Account{

    private DollarAmount sum;

    public Account(){
        this.sum = new DollarAmount(0);
    }

    public DollarAmount getBalance(){
        return this.sum;
    }

    public void debit(DollarAmount da){
        
    }

    public void credit(DollarAmount da){
        
    }
}