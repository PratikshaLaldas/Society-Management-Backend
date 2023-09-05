package com.society.dto;

import java.util.List;

public class ElectionResultDTO {
	private String position;
	private List<CandidateVotingResultDTO> candidateVotingResults;
	public ElectionResultDTO() {
		super();
	}
	public ElectionResultDTO(String position, List<CandidateVotingResultDTO> candidateVotingResults) {
		super();
		this.position = position;
		this.candidateVotingResults = candidateVotingResults;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<CandidateVotingResultDTO> getCandidateVotingResults() {
		return candidateVotingResults;
	}
	public void setCandidateVotingResults(List<CandidateVotingResultDTO> candidateVotingResults) {
		this.candidateVotingResults = candidateVotingResults;
	}
	@Override
	public String toString() {
		return "VotingResultDTO [position=" + position + ", candidateVotingResults=" + candidateVotingResults + "]";
	}
}