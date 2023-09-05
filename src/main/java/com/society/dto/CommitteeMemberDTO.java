package com.society.dto;

public class CommitteeMemberDTO {
    private Long aId;
    private String email;
    private String fName;
    private String mInit;
    private String lName;
    private Integer wingNo;
    private Integer flatNo;
    private Integer floorNo;
    private Integer memberCount;
    private String password;
	public Long getaId() {
		return aId;
	}
	public void setaId(Long aId) {
		this.aId = aId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public CommitteeMemberDTO(Long aId, String email, String fName, String mInit, String lName, Integer wingNo,
			Integer flatNo, Integer floorNo, Integer memberCount, String password) {
		super();
		this.aId = aId;
		this.email = email;
		this.fName = fName;
		this.mInit = mInit;
		this.lName = lName;
		this.wingNo = wingNo;
		this.flatNo = flatNo;
		this.floorNo = floorNo;
		this.memberCount = memberCount;
		this.password = password;
	}
	
	public CommitteeMemberDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CommitteeMemberDTO [aId=" + aId + ", email=" + email + ", fName=" + fName + ", mInit=" + mInit
				+ ", lName=" + lName + ", wingNo=" + wingNo + ", flatNo=" + flatNo + ", floorNo=" + floorNo
				+ ", memberCount=" + memberCount + ", password=" + password + "]";
	}
	
	
    

    
}
