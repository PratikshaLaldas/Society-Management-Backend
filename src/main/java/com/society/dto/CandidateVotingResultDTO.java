package com.society.dto;

public class CandidateVotingResultDTO {
	private String candidateName;
	private int votesReceived;
	public CandidateVotingResultDTO() {
		super();
	}
	public CandidateVotingResultDTO(String candidateName, int votesReceived) {
		super();
		this.candidateName = candidateName;
		this.votesReceived = votesReceived;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public int getVotesReceived() {
		return votesReceived;
	}
	public void setVotesReceived(int votesReceived) {
		this.votesReceived = votesReceived;
	}
	@Override
	public String toString() {
		return "CandidateVotingResultDTO [candidateName=" + candidateName + ", votesReceived=" + votesReceived + "]";
	}
}
