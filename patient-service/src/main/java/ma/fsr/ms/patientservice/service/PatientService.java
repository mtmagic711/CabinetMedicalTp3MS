package ma.fsr.ms.patientservice.service;


import ma.fsr.ms.patientservice.model.Patient;
import ma.fsr.ms.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient create(Patient patient) throws Exception {
        if (patient.getNom() == null || patient.getNom().isBlank()) {
            throw new Exception("Le nom du patient est obligatoire.");
        }
        if (patient.getTelephone() == null || patient.getTelephone().isBlank()) {
            throw new Exception("Le téléphone du patient est obligatoire.");
        }
        if (patient.getDateNaissance().isAfter(LocalDate.now())) {
            throw new Exception("La date de naissance ne peut pas être future.");
        }
        return patientRepository.save(patient);
    }

    public Patient getPatient(Long id) throws Exception {
        return patientRepository.findById(id)
                .orElseThrow(() -> new Exception("Patient introuvable : id = " + id));
    }

    public List<Patient> list() {
        return patientRepository.findAll();
    }

    public void delete(Long id) throws Exception {
        Patient patient = getPatient(id);
        patientRepository.delete(patient);
    }
}
