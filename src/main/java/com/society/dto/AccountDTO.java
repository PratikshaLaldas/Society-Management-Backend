package com.society.dto;

import java.sql.Date;
import java.time.LocalDate;

public class AccountDTO {
	
    private Long billNo;
    private Long rId;
    private String residentFirstName; // Not part of the database, used for reference
    private String residentLastName; // Not part of the database, used for reference
    private Long amount;
    private LocalDate date;
    private String status;
    private LocalDate dueDate;
    private String period;
    private Integer maintenanceFee;
    private Integer securityCharge;
    private Integer commonAreaUtilization;
    private Integer parkingCharge;
	public Long getBillNo() {
		return billNo;
	}
	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
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
	public AccountDTO(Long billNo, Long rId, String residentFirstName, String residentLastName, Long amount,
			LocalDate date, String status, LocalDate dueDate, String period, Integer maintenanceFee,
			Integer securityCharge, Integer commonAreaUtilization, Integer parkingCharge) {
		super();
		this.billNo = billNo;
		this.rId = rId;
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
	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AccountDTO [billNo=" + billNo + ", rId=" + rId + ", residentFirstName=" + residentFirstName
				+ ", residentLastName=" + residentLastName + ", amount=" + amount + ", date=" + date + ", status="
				+ status + ", dueDate=" + dueDate + ", period=" + period + ", maintenanceFee=" + maintenanceFee
				+ ", securityCharge=" + securityCharge + ", commonAreaUtilization=" + commonAreaUtilization
				+ ", parkingCharge=" + parkingCharge + "]";
	}
	
    
    

    
}

