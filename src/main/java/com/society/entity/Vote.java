package com.society.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vote")
public class Vote {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;
	private Long rId;
	private String position;
	private String candidate;
	public Vote() {
		super();
	}
	public Vote(Long voteId, Long rId, String position, String candidate) {
		super();
		this.voteId = voteId;
		this.rId = rId;
		this.position = position;
		this.candidate = candidate;
	}
	public Long getVoteId() {
		return voteId;
	}
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCandidate() {
		return candidate;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", rId=" + rId + ", position=" + position + ", candidate=" + candidate + "]";
	}
}