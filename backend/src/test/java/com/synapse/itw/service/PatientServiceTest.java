package com.synapse.itw.service;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.model.Patient;
import com.synapse.itw.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

class PatientServiceTest {

    private PatientService patientService;
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = Mockito.mock(PatientRepository.class);
        patientService = new PatientService(patientRepository);
    }

    @Test
    void shouldReturnPatientById() {
        Patient patient = new Patient(1, "John", "Doe", null, 175, 80);
        Mockito.when(patientRepository.getById(anyInt())).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(1);
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void shouldReturnPatientAllergies() {
        List<Allergy> allergies = Arrays.asList(new Allergy(1, "Peanuts"));
        Mockito.when(patientRepository.getAllergies(anyInt())).thenReturn(allergies);

        List<Allergy> result = patientService.getPatientAllergies(1);
        assertEquals(1, result.size());
        assertEquals("Peanuts", result.get(0).name()); // Use .name() instead of getName()
    }

    @Test
    void shouldUpdatePatientAllergies() {
        List<Integer> newAllergies = Arrays.asList(1, 2);
        patientService.updatePatientAllergies(1, newAllergies);

        Mockito.verify(patientRepository).updateAllergies(1, newAllergies);
    }

    @Test
    void shouldReturnMoleculeResults() {
        // Arrange
        List<Allergy> mockResults = Arrays.asList(
                new Allergy(1, "Aspirin"),
                new Allergy(2, "Penicillin")
        );
        Mockito.when(patientRepository.searchMolecules(anyString())).thenReturn(mockResults);

        // Act
        List<Allergy> results = patientService.searchMolecules("pen");

        // Assert
        assertEquals(2, results.size());
        assertEquals("Aspirin", results.get(0).name());
        assertEquals("Penicillin", results.get(1).name());
    }
}
