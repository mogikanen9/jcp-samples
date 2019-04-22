package org.mogikanensoftware.play.deadlock;

public class Account{

    private static final int INIT_AMOUNT  = 100_000_000;

    private DollarAmount sum;

    public Account(){
        this.sum = new DollarAmount(INIT_AMOUNT);
    }

    public DollarAmount getBalance(){
        return this.sum;
    }

    public void debit(DollarAmount da){
        this.sum = new DollarAmount(this.sum.getAmount()+da.getAmount());
    }

    public void credit(DollarAmount da){
        this.sum = new DollarAmount(this.sum.getAmount()-da.getAmount());
    }
}