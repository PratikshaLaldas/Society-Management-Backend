package com.society.dto;

import java.time.LocalDate;
import java.util.List;

public class ElectionPositionDTO {
	private String position;
	private List<CandidateDTO> candidates;
	private LocalDate electionStartDate;
	public ElectionPositionDTO() {
		super();
	}
	public ElectionPositionDTO(String position, List<CandidateDTO> candidates, LocalDate electionStartDate) {
		super();
		this.position = position;
		this.candidates = candidates;
		this.electionStartDate = electionStartDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<CandidateDTO> getCandidates() {
		return candidates;
	}
	public void setCandidates(List<CandidateDTO> candidates) {
		this.candidates = candidates;
	}
	public LocalDate getElectionStartDate() {
		return electionStartDate;
	}
	public void setElectionStartDate(LocalDate electionStartDate) {
		this.electionStartDate = electionStartDate;
	}
	@Override
	public String toString() {
		return "ElectionPositionDTO [position=" + position + ", candidates=" + candidates + ", electionStartDate="
				+ electionStartDate + "]";
	}
}