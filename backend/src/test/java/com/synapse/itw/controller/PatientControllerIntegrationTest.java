package com.synapse.itw.controller;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.model.Patient;
import com.synapse.itw.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private Patient patient;
    private Allergy allergy;

    @BeforeEach
    void setUp() {
        patient = new Patient(1, "John", "Doe", null, 175, 80);
        allergy = new Allergy(1, "Peanuts");

        Mockito.when(patientService.getPatientById(anyInt())).thenReturn(Optional.of(patient));
        Mockito.when(patientService.getPatientAllergies(anyInt())).thenReturn(Arrays.asList(allergy));
    }

    @Test
    void shouldGetPatientById() throws Exception {
        mockMvc.perform(get("/api/v1/patients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.last_name").value("Doe"));
    }

    @Test
    void shouldReturnPatientAllergies() throws Exception {
        mockMvc.perform(get("/api/v1/patients/1/allergies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Peanuts"));
    }

    @Test
    void shouldUpdatePatientAllergies() throws Exception {
        mockMvc.perform(put("/api/v1/patients/1/allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldSearchMoleculesSuccessfully() throws Exception {
        // Arrange
        Mockito.when(patientService.searchMolecules(anyString()))
                .thenReturn(Arrays.asList(new Allergy(1, "Aspirin"), new Allergy(2, "Penicillin")));

        // Act & Assert
        mockMvc.perform(get("/api/v1/patients/molecules/search?query=pen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Aspirin"))
                .andExpect(jsonPath("$[1].name").value("Penicillin"));
    }

}
