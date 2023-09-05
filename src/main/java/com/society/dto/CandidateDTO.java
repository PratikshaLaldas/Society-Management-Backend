package com.society.dto;

public class CandidateDTO {
	private String candidateName;

	public CandidateDTO() {
		super();
	}

	public CandidateDTO(String candidateName) {
		super();
		this.candidateName = candidateName;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	@Override
	public String toString() {
		return "CandidateDTO [candidateName=" + candidateName + "]";
	}
}