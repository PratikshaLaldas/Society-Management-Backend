package com.society.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OnlinePayment")
public class OnlinePayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "billNo")
    private Account account;

    private Long amount;
    private String street;
    private String city;
    private String country;
    private String zipcode;
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Resident getResident() {
		return resident;
	}
	public void setResident(Resident resident) {
		this.resident = resident;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public OnlinePayment(Long paymentId, Resident resident, Account account, Long amount, String street, String city,
			String country, String zipcode) {
		super();
		this.paymentId = paymentId;
		this.resident = resident;
		this.account = account;
		this.amount = amount;
		this.street = street;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
	}
	public OnlinePayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OnlinePayment [paymentId=" + paymentId + ", resident=" + resident + ", account=" + account + ", amount="
				+ amount + ", street=" + street + ", city=" + city + ", country=" + country + ", zipcode=" + zipcode
				+ "]";
	}
	
    
    
    
}