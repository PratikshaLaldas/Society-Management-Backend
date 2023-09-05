package com.society.service;

import static java.lang.String.*;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.isEmpty;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.society.dto.CandidateDTO;
import com.society.dto.CandidateVotingResultDTO;
import com.society.dto.ElectionPositionDTO;
import com.society.dto.ElectionResultDTO;
import com.society.dto.VotingDTO;
import com.society.entity.CurrentUserSession;
import com.society.entity.ElectionPosition;
import com.society.entity.Vote;
import com.society.exception.InvalidInputException;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ElectionPositionRepository;
import com.society.repository.VoteRepository;

@Service
public class ElectionServiceImpl implements ElectionService {

	@Autowired
	private ElectionPositionRepository electionPositionRepository;
	@Autowired
	private CurrentUserSessionRepository currSession;

	@Autowired
	private VoteRepository voteRepository;

	@Override
	public ElectionPosition registerCandidatesForPosition(ElectionPositionDTO electionPosition) {
		validateElectionPositionRequest(electionPosition);
		return electionPositionRepository.saveAndFlush(mapToElectionPosition(electionPosition));

	}

	@Override
	public ElectionPosition updateCandidatesForPosition(ElectionPositionDTO electionPosition) {
		if (electionPosition == null || isEmpty(electionPosition.getPosition())) {
			throw new InvalidInputException("Validation Error :: Invalid Election Position request");
		}
		ElectionPosition existingElectionPosition = electionPositionRepository
				.findByPosition(electionPosition.getPosition());
		if (existingElectionPosition != null) {
			updateExistingPosition(electionPosition, existingElectionPosition);
			return electionPositionRepository.saveAndFlush(existingElectionPosition);
		} else {
			throw new NotFoundException(format("Position %s not found.", electionPosition.getPosition()));
		}
	}

	@Override
	public void deletePositionDetails(ElectionPositionDTO electionPosition) {
		if(electionPosition == null || isEmpty(electionPosition.getPosition())) {
			throw new InvalidInputException("Validation Error :: Invalid Election Position request");
		}
		electionPositionRepository.deleteByPosition(electionPosition.getPosition());
	}

	@Override
	public ElectionPosition getPositionDetails(String positionName) {
		ElectionPosition electionPosition = electionPositionRepository.findByPosition(positionName);
		if (electionPosition != null) {
			return electionPosition;
		} else {
			throw new NotFoundException(format("Position %s not found.", positionName));
		}
	}

	@Override
public ElectionResultDTO getElectionResultsForPosition(ElectionPositionDTO electionPosition) {
    if (electionPosition == null || isEmpty(electionPosition.getPosition())) {
        throw new InvalidInputException("Validation Error :: Invalid Election Position Results request");
    }
    List<Object[]> result = voteRepository.findByPositionAndGroupByCandidateName(electionPosition.getPosition());
    if (CollectionUtils.isEmpty(result)) {
        throw new NotFoundException(format("No Voting data found for %s.", electionPosition.getPosition()));
    }
    return mapToVotingResult(electionPosition.getPosition(), result);
}


	@Override
	public String castVote(VotingDTO voting) throws LoginException {
		validateVoting(voting);
		CurrentUserSession currSess = currSession.findByPrivateKey(voting.getKey());
		if (currSess == null) {
			throw new LoginException("Login required");
		}
		ElectionPosition electionPosition = electionPositionRepository.findByPosition(voting.getPosition());
		if (electionPosition != null) {
			validateElectionPositionAndVotingDTO(electionPosition, voting);
		} else {
			throw new NotFoundException(format("Position %s not found.", voting.getPosition()));
		}
		if(voteRepository.findByrIdAndPosition(currSess.getResident().getrId(), voting.getPosition()) == null){
			voteRepository.saveAndFlush(mapToVote(currSess.getResident().getrId(), voting));
			return String.format("Member casted vote successfully for %s position", voting.getPosition());
		}
		return String.format("Member already casted vote for %s position", voting.getPosition());
	}

	private void validateElectionPositionAndVotingDTO(ElectionPosition electionPosition, VotingDTO voting) {
		if (!electionPosition.getCandidate1().equalsIgnoreCase(voting.getCandidateName())
				&& !electionPosition.getCandidate2().equalsIgnoreCase(voting.getCandidateName())) {
			throw new InvalidInputException(format("Candidate %s is not present for %s Position",
					voting.getCandidateName(), voting.getPosition()));
		}
		if (LocalDate.now().isBefore(electionPosition.getElectionStartDate())
				|| LocalDate.now().isAfter(electionPosition.getElectionEndDate())) {
			throw new InvalidInputException(
					format("Voting can be done within Start Date %s and End Date %s for %s Position",
							electionPosition.getElectionStartDate().toString(),
							electionPosition.getElectionEndDate().toString(), electionPosition.getPosition()));
		}
	}

	private ElectionPosition mapToElectionPosition(ElectionPositionDTO electionPosition) {
		ElectionPosition votingPosition = new ElectionPosition();
		votingPosition.setPosition(electionPosition.getPosition());
		List<CandidateDTO> candidates = electionPosition.getCandidates();
		votingPosition.setCandidate1(candidates.get(0).getCandidateName());
		votingPosition.setCandidate2(candidates.get(1).getCandidateName());
		votingPosition.setElectionStartDate(electionPosition.getElectionStartDate());
		return votingPosition;
	}

	private Vote mapToVote(Long rId, VotingDTO voting) {
		Vote vote = new Vote();
		vote.setrId(rId);
		vote.setPosition(voting.getPosition());
		vote.setCandidate(voting.getCandidateName());
		return vote;
	}

	private ElectionResultDTO mapToVotingResult(String positionName, List<Object[]> result) {
		ElectionResultDTO votingResult = new ElectionResultDTO();
		votingResult.setPosition(positionName);
		List<CandidateVotingResultDTO> candidateResults = result.stream().map(v -> {
			CandidateVotingResultDTO candidateResult = new CandidateVotingResultDTO();
			candidateResult.setCandidateName((String) v[0]);
			candidateResult.setVotesReceived(((Long) v[1]).intValue());
			return candidateResult;
		}).collect(Collectors.toList());
		votingResult.setCandidateVotingResults(candidateResults);
		return votingResult;
	}

	private void validateElectionPositionRequest(ElectionPositionDTO electionPosition) {
		boolean isValid = false;
		if (electionPosition != null && electionPosition.getElectionStartDate() != null
				&& !isEmpty(electionPosition.getPosition())) {
			List<CandidateDTO> candidates = electionPosition.getCandidates();
			if (!isEmpty(candidates) && candidates.size() == 2
					&& candidates.stream().allMatch(c -> !isEmpty(c.getCandidateName()))) {
				isValid = true;
			}
		}
		if (!isValid) {
			throw new InvalidInputException("Validation Error :: Invalid Election Position request");
		}
	}

	private void validateVoting(VotingDTO voting) {
		if (voting == null || isEmpty(voting.getKey()) || isEmpty(voting.getPosition()) || isEmpty(voting.getCandidateName())) {
			throw new InvalidInputException("Validation Error :: Invalid voting object in request");
		}
	}

	private void updateExistingPosition(ElectionPositionDTO electionPosition,
			ElectionPosition existingElectionPosition) {
		List<CandidateDTO> candidates = electionPosition.getCandidates();
		if (!isEmpty(candidates.get(0).getCandidateName())) {
			existingElectionPosition.setCandidate1(candidates.get(0).getCandidateName());
		}
		if (!isEmpty(candidates.get(1).getCandidateName())) {
			existingElectionPosition.setCandidate2(candidates.get(1).getCandidateName());
		}
		if (electionPosition.getElectionStartDate() != null) {
			existingElectionPosition.setElectionStartDate(electionPosition.getElectionStartDate());
		}
	}

	 @Override
	    public List<ElectionPosition> getAllPositions() {
	        return electionPositionRepository.findAll();
	    }
	}
