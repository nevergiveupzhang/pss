package com.psssystem.connection.vo;

import java.io.Serializable;

public class BalanceVO implements Serializable {
	private int income;
	private int outcome;
	private int profit;
	private int discount;
	
	public BalanceVO() {
		super();
	}
	public BalanceVO(int income, int outcome, int profit,int discount) {
		super();
		this.income = income;
		this.outcome = outcome;
		this.profit = profit;
		this.discount=discount;
	}
	public int getIncome() {
		return income;
	}
	public int getOutcome() {
		return outcome;
	}
	public int getProfit() {
		return profit;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}
	public void setProfit(int profit) {
		this.profit = profit;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
}
