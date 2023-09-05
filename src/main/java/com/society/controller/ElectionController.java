package com.society.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.ElectionPositionDTO;
import com.society.dto.ElectionResultDTO;
import com.society.dto.VotingDTO;
import com.society.entity.ElectionPosition;
import com.society.exception.LoginException;
import com.society.service.ElectionService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/election")
public class ElectionController {

	@Autowired
	private ElectionService electionService;

	@PostMapping("/position")
    public ResponseEntity<String> registerCandidatesForPosition(@RequestBody ElectionPositionDTO electionPosition) {
		electionService.registerCandidatesForPosition(electionPosition);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Candidate for position %s registered successfully", electionPosition.getPosition()));
    }

	@PutMapping("/position")
    public ResponseEntity<String> updateCandidateForPosition(@RequestBody ElectionPositionDTO electionPosition) {
		electionService.updateCandidatesForPosition(electionPosition);
        return ResponseEntity.ok(String.format("Candidate for position %s updated successfully", electionPosition.getPosition()));
    }

	@DeleteMapping("/position")
    public ResponseEntity<String> deletePositionDetails(@RequestBody ElectionPositionDTO electionPosition) {
		electionService.deletePositionDetails(electionPosition);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Position %s data deleted successfully", electionPosition.getPosition()));
    }

	@GetMapping("/position/{positionName}")
    public ResponseEntity<ElectionPosition> getPositionDetails(@PathVariable String positionName) {
        return ResponseEntity.ok(electionService.getPositionDetails(positionName));
    }
	@GetMapping("/positions")
    public ResponseEntity<List<ElectionPosition>> getAllPositions() {
        List<ElectionPosition> positions = electionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @PostMapping("/position/cast-vote")
    public ResponseEntity<String> castVote(@RequestBody VotingDTO voting) throws LoginException {
        return ResponseEntity.ok(electionService.castVote(voting));
    }
    
   @GetMapping("/position/results")
public ResponseEntity<ElectionResultDTO> getElectionResultsForPosition(@RequestParam String position) throws LoginException {
    ElectionPositionDTO electionPosition = new ElectionPositionDTO();
    electionPosition.setPosition(position);
    return ResponseEntity.ok(electionService.getElectionResultsForPosition(electionPosition));
}
}