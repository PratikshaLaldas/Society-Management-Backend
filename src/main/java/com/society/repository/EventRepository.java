package com.society.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query methods can be defined here if needed
	
	List<Event> findByDateAndStartTimeBetween(LocalDate date, LocalTime startTime, LocalTime endTime);
	  //List<Event> findByDateAndPlaceAndStartTimeBetween(LocalDate date, String place, LocalTime startTime, LocalTime endTime);

	List<Event> findByPlaceAndDateAndEndTimeGreaterThanAndStartTimeLessThan(String place, LocalDate eventDate,
			LocalTime eventStartTime, LocalTime eventEndTime);

	List<Event> findByResident_rId(Long residentId);

	List<Event> findByCommitteemember_aId(Long committeememberId);


}
