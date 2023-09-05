package com.society.dto;

import java.math.BigDecimal;

public class OnlinePaymentDTO {
    private Long paymentId;
    private Long rId;
    private Long billNo;
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
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public Long getBillNo() {
		return billNo;
	}
	public void setBillNo(Long billNo) {
		this.billNo = billNo;
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
	public OnlinePaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OnlinePaymentDTO(Long paymentId, Long rId, Long billNo, Long amount, String street, String city,
			String country, String zipcode) {
		super();
		this.paymentId = paymentId;
		this.rId = rId;
		this.billNo = billNo;
		this.amount = amount;
		this.street = street;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "OnlinePaymentDTO [paymentId=" + paymentId + ", rId=" + rId + ", billNo=" + billNo + ", amount=" + amount
				+ ", street=" + street + ", city=" + city + ", country=" + country + ", zipcode=" + zipcode + "]";
	}
	
    
    
}
