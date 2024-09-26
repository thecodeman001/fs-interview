package com.synapse.itw.repository;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldGetPatientById() {
        Optional<Patient> patient = patientRepository.getById(1);
        assertTrue(patient.isPresent());
        assertEquals("John", patient.get().getFirstName());
    }

    @Test
    void shouldReturnPatientAllergies() {
        patientRepository.updateAllergies(1, Arrays.asList(1));  // Associate allergy (ID 1) with patient ID 1

        // Act: Fetch patient allergies
        List<Allergy> allergies = patientRepository.getAllergies(1);

        // Assert: Validate the fetched data
        assertEquals(1, allergies.size());
        assertEquals("nadroparin calcium", allergies.get(0).name());
    }

    @Test
    void shouldUpdatePatientAllergies() {
        patientRepository.updateAllergies(1, List.of(1, 2));
        List<Allergy> allergies = patientRepository.getAllergies(1);
        assertEquals(2, allergies.size());
    }

    @Test
    void shouldSearchMoleculesSuccessfully() {
        List<Allergy> result = patientRepository.searchMolecules("pen");

        // Ensure at least one result contains "Penicillin"
        assertTrue(result.stream().anyMatch(molecule -> molecule.name().equals("penicillin g")));
    }

}
