package com.society.dto;

public class VotingDTO {
	private String position;
	private String key;
	private String candidateName;
	public VotingDTO() {
		super();
	}
	public VotingDTO(String position, String key, String candidateName) {
		super();
		this.position = position;
		this.key = key;
		this.candidateName = candidateName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	@Override
	public String toString() {
		return "VotingDTO [position=" + position + ", key=" + key + ", candidateName=" + candidateName + "]";
	}
}