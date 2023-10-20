
package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.PatientController;
import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO Implement all the unit test in its corresponding class. Make sure to be
 * as exhaustive as possible. Coverage is checked ;)
 */

@RunWith(Suite.class)
@SuiteClasses({DoctorControllerUnitTest.class, PatientControllerUnitTest.class, RoomControllerUnitTest.class})
class EntityControllerUnitTest {
	
}

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {
	@MockBean
	private DoctorRepository doctorRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void shouldCreateDoctor() throws Exception {
		Doctor doctor = new Doctor("Marta", "Pons", 34, "m.pons@hospital.accwe");

		mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(doctor))).andExpect(status().isCreated());
	}

	@Test
	void shouldGetNoDoctors() throws Exception {
		List<Doctor> doctors = new ArrayList<Doctor>();
		when(doctorRepository.findAll()).thenReturn(doctors);
		mockMvc.perform(get("/api/doctors")).andExpect(status().isNoContent());
	}

	@Test
	void shouldGetTwoDoctors() throws Exception {
		Doctor doctor = new Doctor("Marta", "Pons", 34, "m.pons@hospital.accwe");
		Doctor doctor1 = new Doctor("Raul", "Figueroa", 40, "r.figueroa@hospital.accwe");

		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		doctors.add(doctor1);

		when(doctorRepository.findAll()).thenReturn(doctors);
		mockMvc.perform(get("/api/doctors")).andExpect(status().isOk());
	}

	@Test
	void shouldGetDoctorById() throws Exception {
		Doctor doctor = new Doctor("Raul", "Figueroa", 40, "r.figueroa@hospital.accwe");
		doctor.setId(1);

		Optional<Doctor> opt = Optional.of(doctor);

		when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
		mockMvc.perform(get("/api/doctors/" + doctor.getId())).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetDoctorById() throws Exception {
		long id = 0;
		mockMvc.perform(get("/api/doctors/" + id)).andExpect(status().isNotFound());

	}

	@Test
	void shouldDeleteDoctorById() throws Exception {
		Doctor doctor = new Doctor("Raul", "Figueroa", 40, "r.figueroa@hospital.accwe");
		doctor.setId(1);

		Optional<Doctor> opt = Optional.of(doctor);

		when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
		mockMvc.perform(delete("/api/doctors/" + doctor.getId())).andExpect(status().isOk());
	}

	@Test
	void shouldNotDeleteDoctor() throws Exception {
		long id = 0;
		mockMvc.perform(delete("/api/doctors/" + id)).andExpect(status().isNotFound());

	}

	@Test
	void shouldDeleteAllDoctors() throws Exception {
		mockMvc.perform(delete("/api/doctors")).andExpect(status().isOk());
	}
}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

	@MockBean
	private PatientRepository patientRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreatePatient() throws Exception {
		Patient patient = new Patient("Pedro", "Rivera", 50, "p.rivera@email.com");

		mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patient))).andExpect(status().isCreated());
	}

	@Test
	void shouldGetNoPatients() throws Exception {
		List<Patient> patients = new ArrayList<Patient>();
		when(patientRepository.findAll()).thenReturn(patients);
		mockMvc.perform(get("/api/patients")).andExpect(status().isNoContent());
	}

	@Test
	void shouldGetTwoPatients() throws Exception {
		Patient patient1 = new Patient("Pedro", "Rivera", 50, "p.rivera@email.com");
		Patient patient2 = new Patient("Ruth", "Osorio", 45, "rosorio@email.com");

		List<Patient> patients = new ArrayList<Patient>();
		patients.add(patient1);
		patients.add(patient2);

		when(patientRepository.findAll()).thenReturn(patients);
		mockMvc.perform(get("/api/patients")).andExpect(status().isOk());
	}

	@Test
	void shouldGetPatientById() throws Exception {
		Patient patient = new Patient("Pedro", "Rivera", 50, "p.rivera@email.com");
		patient.setId(1);

		Optional<Patient> opt = Optional.of(patient);

		when(patientRepository.findById(patient.getId())).thenReturn(opt);
		mockMvc.perform(get("/api/patients/" + patient.getId())).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetPatientById() throws Exception {
		long id = 0;
		mockMvc.perform(get("/api/patients/" + id)).andExpect(status().isNotFound());

	}

	@Test
	void shouldDeletePatientById() throws Exception {
		Patient patient = new Patient("Ruth", "Osorio", 45, "rosorio@email.com");
		patient.setId(1);

		Optional<Patient> opt = Optional.of(patient);

		when(patientRepository.findById(patient.getId())).thenReturn(opt);
		mockMvc.perform(delete("/api/patients/" + patient.getId())).andExpect(status().isOk());
	}

	@Test
	void shouldNotDeletePatient() throws Exception {
		long id = 0;
		mockMvc.perform(delete("/api/patients/" + id)).andExpect(status().isNotFound());

	}

	@Test
	void shouldDeleteAllPatients() throws Exception {
		mockMvc.perform(delete("/api/patients")).andExpect(status().isOk());
	}

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

	@MockBean
	private RoomRepository roomRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateRoom() throws Exception {
		Room room = new Room("Neurology");

		mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(room))).andExpect(status().isCreated());
	}

	@Test
	void shouldGetNoRooms() throws Exception {
		List<Room> rooms = new ArrayList<Room>();
		when(roomRepository.findAll()).thenReturn(rooms);
		mockMvc.perform(get("/api/rooms")).andExpect(status().isNoContent());
	}

	@Test
	void shouldGetTwoRooms() throws Exception {
		Room room1 = new Room("Neurology");
		Room room2 = new Room("Pedriatics");

		List<Room> rooms = new ArrayList<Room>();
		rooms.add(room1);
		rooms.add(room2);

		when(roomRepository.findAll()).thenReturn(rooms);
		mockMvc.perform(get("/api/rooms")).andExpect(status().isOk());
	}

	@Test
	void shouldGetRoomByName() throws Exception {
		Room room = new Room("Pedriatics");

		Optional<Room> opt = Optional.of(room);

		when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
		mockMvc.perform(get("/api/rooms/" + room.getRoomName())).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetRoomByName() throws Exception {
		String roomName = "Dermatology";
		mockMvc.perform(get("/api/rooms/" + roomName)).andExpect(status().isNotFound());
	}

	@Test
	void shouldDeleteRoomByName() throws Exception {
		Room room = new Room("Pedriatics");
		Optional<Room> opt = Optional.of(room);

		when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
		mockMvc.perform(delete("/api/rooms/" + room.getRoomName())).andExpect(status().isOk());
	}

	@Test
	void shouldNotDeleteRoom() throws Exception {
		String roomName = "Dermatology";
		mockMvc.perform(delete("/api/rooms/" + roomName)).andExpect(status().isNotFound());

	}

	@Test
	void shouldDeleteAllRooms() throws Exception {
		mockMvc.perform(delete("/api/rooms")).andExpect(status().isOk());
	}
}
