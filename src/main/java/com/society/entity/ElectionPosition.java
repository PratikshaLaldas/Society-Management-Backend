package com.society.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ElectionPosition")
public class ElectionPosition {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingPositionId;
	private String candidate1;
	private String candidate2;
	private String position;
	private LocalDate electionStartDate;
	private LocalDate electionEndDate;
	public ElectionPosition() {
		super();
	}
	public ElectionPosition(Long votingPositionId, String candidate1, String candidate2, String position,
			LocalDate electionStartDate, LocalDate electionEndDate) {
		super();
		this.votingPositionId = votingPositionId;
		this.candidate1 = candidate1;
		this.candidate2 = candidate2;
		this.position = position;
		this.electionStartDate = electionStartDate;
		this.electionEndDate = electionEndDate;
	}
	public Long getVotingPositionId() {
		return votingPositionId;
	}
	public void setVotingPositionId(Long votingPositionId) {
		this.votingPositionId = votingPositionId;
	}
	public String getCandidate1() {
		return candidate1;
	}
	public void setCandidate1(String candidate1) {
		this.candidate1 = candidate1;
	}
	public String getCandidate2() {
		return candidate2;
	}
	public void setCandidate2(String candidate2) {
		this.candidate2 = candidate2;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public LocalDate getElectionStartDate() {
		return electionStartDate;
	}
	public void setElectionStartDate(LocalDate electionStartDate) {
		this.electionStartDate = electionStartDate;
		setElectionEndDate(electionStartDate.plusDays(1));
	}
	public LocalDate getElectionEndDate() {
		return electionEndDate;
	}
	private void setElectionEndDate(LocalDate electionEndDate) {
		this.electionEndDate = electionEndDate;
	}
	@Override
	public String toString() {
		return "ElectionPosition [votingPositionId=" + votingPositionId + ", candidate1=" + candidate1 + ", candidate2="
				+ candidate2 + ", position=" + position + ", electionStartDate=" + electionStartDate
				+ ", electionEndDate=" + electionEndDate + "]";
	}
}