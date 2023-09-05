package com.society.dto;

public class ResidentDTO {
    private Long rId;
    private String email;
    private String fName;
    private String mInit;
    private String lName;
    private Integer wingNo;
    private Integer flatNo;
    private Integer floorNo;
    private Integer memberCount;
    private Integer twoWheelerCount; // Number of two-wheelers
    private Integer fourWheelerCount; // Number of four-wheeler
    private String password;
    private Integer birthYear;
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getmInit() {
		return mInit;
	}
	public void setmInit(String mInit) {
		this.mInit = mInit;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public Integer getWingNo() {
		return wingNo;
	}
	public void setWingNo(Integer wingNo) {
		this.wingNo = wingNo;
	}
	public Integer getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(Integer flatNo) {
		this.flatNo = flatNo;
	}
	public Integer getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public Integer getTwoWheelerCount() {
		return twoWheelerCount;
	}
	public void setTwoWheelerCount(Integer twoWheelerCount) {
		this.twoWheelerCount = twoWheelerCount;
	}
	public Integer getFourWheelerCount() {
		return fourWheelerCount;
	}
	public void setFourWheelerCount(Integer fourWheelerCount) {
		this.fourWheelerCount = fourWheelerCount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public ResidentDTO(Long rId, String email, String fName, String mInit, String lName, Integer wingNo, Integer flatNo,
			Integer floorNo, Integer memberCount, Integer twoWheelerCount, Integer fourWheelerCount, String password,
			Integer birthYear) {
		super();
		this.rId = rId;
		this.email = email;
		this.fName = fName;
		this.mInit = mInit;
		this.lName = lName;
		this.wingNo = wingNo;
		this.flatNo = flatNo;
		this.floorNo = floorNo;
		this.memberCount = memberCount;
		this.twoWheelerCount = twoWheelerCount;
		this.fourWheelerCount = fourWheelerCount;
		this.password = password;
		this.birthYear = birthYear;
	}
	public ResidentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ResidentDTO [rId=" + rId + ", email=" + email + ", fName=" + fName + ", mInit=" + mInit + ", lName="
				+ lName + ", wingNo=" + wingNo + ", flatNo=" + flatNo + ", floorNo=" + floorNo + ", memberCount="
				+ memberCount + ", twoWheelerCount=" + twoWheelerCount + ", fourWheelerCount=" + fourWheelerCount
				+ ", password=" + password + ", birthYear=" + birthYear + "]";
	}
	
    
    
	
    
}

