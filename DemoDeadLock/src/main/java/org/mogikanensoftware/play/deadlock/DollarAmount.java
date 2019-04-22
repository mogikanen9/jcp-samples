package org.mogikanensoftware.play.deadlock;

public class DollarAmount implements Comparable<DollarAmount>{
    private Integer amount;

    public DollarAmount(int amount){
        this.amount = amount;
    }

    public Integer getAmount(){
        return this.amount;
    }

	@Override
	public int compareTo(DollarAmount o) {
		return this.getAmount().compareTo(((DollarAmount)o).getAmount());
	}
}