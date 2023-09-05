package com.society.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billNo;
    
    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;
    
    @Column(name = "fname")
    private String residentFirstName; // Not part of the database, used for reference

    @Column(name = "lname")
    private String residentLastName; // Not part of the database, used for reference
    

    private Long amount;
    
    private LocalDate date;
    
    @Column(columnDefinition = "varchar(50) default 'unpaid'") // Setting default value for status
    private String status;
    
    private LocalDate dueDate;
    
    private String period;
    
    private Integer maintenanceFee;
    
    @Column(columnDefinition = "integer default 500") // Setting default value for securityCharge
    private Integer securityCharge;
    
    @Column(columnDefinition = "integer default 750") // Setting default value for commonAreaUtilization
    private Integer commonAreaUtilization;
    
    
    private Integer parkingCharge;


	public Long getBillNo() {
		return billNo;
	}


	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}


	public Resident getResident() {
		return resident;
	}


	public void setResident(Resident resident) {
		this.resident = resident;
	}


	public String getResidentFirstName() {
		return residentFirstName;
	}


	public void setResidentFirstName(String residentFirstName) {
		this.residentFirstName = residentFirstName;
	}


	public String getResidentLastName() {
		return residentLastName;
	}


	public void setResidentLastName(String residentLastName) {
		this.residentLastName = residentLastName;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public Integer getMaintenanceFee() {
		return maintenanceFee;
	}


	public void setMaintenanceFee(Integer maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}


	public Integer getSecurityCharge() {
		return securityCharge;
	}


	public void setSecurityCharge(Integer securityCharge) {
		this.securityCharge = securityCharge;
	}


	public Integer getCommonAreaUtilization() {
		return commonAreaUtilization;
	}


	public void setCommonAreaUtilization(Integer commonAreaUtilization) {
		this.commonAreaUtilization = commonAreaUtilization;
	}


	public Integer getParkingCharge() {
		return parkingCharge;
	}


	public void setParkingCharge(Integer parkingCharge) {
		this.parkingCharge = parkingCharge;
	}


	public Account(Long billNo, Resident resident, String residentFirstName, String residentLastName, Long amount,
			LocalDate date, String status, LocalDate dueDate, String period, Integer maintenanceFee,
			Integer securityCharge, Integer commonAreaUtilization, Integer parkingCharge) {
		super();
		this.billNo = billNo;
		this.resident = resident;
		this.residentFirstName = residentFirstName;
		this.residentLastName = residentLastName;
		this.amount = amount;
		this.date = date;
		this.status = status;
		this.dueDate = dueDate;
		this.period = period;
		this.maintenanceFee = maintenanceFee;
		this.securityCharge = securityCharge;
		this.commonAreaUtilization = commonAreaUtilization;
		this.parkingCharge = parkingCharge;
	}


	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Account [billNo=" + billNo + ", resident=" + resident + ", residentFirstName=" + residentFirstName
				+ ", residentLastName=" + residentLastName + ", amount=" + amount + ", date=" + date + ", status="
				+ status + ", dueDate=" + dueDate + ", period=" + period + ", maintenanceFee=" + maintenanceFee
				+ ", securityCharge=" + securityCharge + ", commonAreaUtilization=" + commonAreaUtilization
				+ ", parkingCharge=" + parkingCharge + "]";
	}


	
	
    


}