package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    
    @Test
	void testAppointment1() {
    	d1 = new Doctor();
    	d1.setFirstName("Miguel");
    	d1.setLastName("Garrido");
    	d1.setAge(24);
    	d1.setEmail("m.garrido@hospital.accwe");
		r1 = new Room("Neurology");
		p1 = new Patient("Miguel", "Dominguez", 67, "miguel.dom@email.com");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        Appointment appointment = new Appointment(p1, d1, r1, startsAt, finishesAt);
		a1 = entityManager.persist(appointment);
		entityManager.flush();
		appointment.setId(a1.getId());

		assertThat(d1.getFirstName()).isEqualTo(p1.getFirstName());
		assertThat(d1.getLastName()).isNotEqualTo(p1.getLastName());
		assertThat(appointment.getId()).isNotNull();
	}
    
    @Test
    void testDoctor() {
    	 d1.setId(a1.getDoctor().getId());
    	 assertThat(d1.getId()).isNotNull();
    	 assertThat(d1.getEmail()).contains("@hospital.accwe");
    	 assertThat(d1.getAge()).isGreaterThan(18);
    }
    
	@Test
	void testRoom() {
		r1 = new Room();
		r1 = a1.getRoom();

		assertThat(r1.getRoomName()).isNotNull();
		assertThat(r1.getRoomName()).doesNotMatch("Dermatology");
	}
	
	@Test
	void testPatient() {
		p1.setId(a1.getPatient().getId());

		assertThat(d1.getFirstName()).isEqualTo(p1.getFirstName());
		assertThat(d1.getLastName()).isNotEqualTo(p1.getLastName());
		assertThat(p1.getId()).isNotNull();
	}
	
	@Test
	void testAppointment2() {
		Doctor doctor = new Doctor ("Marta", "Pons", 34, "m.pons@hospital.accwe");
		Patient patient = new Patient("Ruth", "Osorio", 45, "rosorio@email.com");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:00 24/04/2023", formatter);

        Appointment appointment = new Appointment(patient, doctor, r1, startsAt, finishesAt);
		a2 = entityManager.persist(appointment);
		entityManager.flush();
		
		assertThat(a2.getId()).isNotNull();
	}
	
	@Test
	void testAppointment3() {
		Doctor doctor = new Doctor ("Raul", "Figueroa", 40, "r.figueroa@hospital.accwe");
		Patient patient = new Patient("Pedro", "Rivera", 50, "p.rivera@email.com");
		
        LocalDateTime startsAt= LocalDateTime.parse("19:45 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:15 24/04/2023", formatter);
        
        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setRoom(r1);
        appointment.setStartsAt(startsAt);
        appointment.setFinishesAt(finishesAt);
		a3 = entityManager.persist(appointment);
		entityManager.flush();
		
		assertThat(a3.getId()).isNotNull();
	}
	
	@Test
	void testOverlaps1() {
		boolean overlaps = a1.overlaps(a2);
		assertThat(overlaps).isTrue();
	}
	
	@Test
	void testOverlaps2() {
		boolean overlaps = a3.overlaps(a2);
		assertThat(overlaps).isTrue();
	}
	
	@Test
	void testOverlaps3() {
		boolean overlaps = a2.overlaps(a3);
		assertThat(overlaps).isTrue();
	}
	
	@Test
	void testOverlaps4() {
		LocalDateTime startsAt = LocalDateTime.parse("20:00 24/04/2023", formatter);
		a3.setStartsAt(startsAt);
		boolean overlaps = a2.overlaps(a3);
		assertThat(overlaps).isFalse();
	}
	

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
