package ma.fsr.ms.consultationservice.service;

import ma.fsr.ms.consultationservice.client.RendezVousClient;
import ma.fsr.ms.consultationservice.dto.RendezVousDto;
import ma.fsr.ms.consultationservice.model.Consultation;
import ma.fsr.ms.consultationservice.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConsultationService {

    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private RendezVousClient rendezVousClient;

    public Consultation create(Consultation consultation) {
        // Rule: Rapport obligatoire (at least 10 chars)
        if (consultation.getRapport() == null || consultation.getRapport().length() < 10) {
            throw new RuntimeException("Rapport de consultation insuffisant (min 10 caractères).");
        }

        // Rule: Date consultation obligatoire
        if (consultation.getDateConsultation() == null) {
            throw new RuntimeException("La date de consultation est obligatoire.");
        }

        // Rule: Rendez-vous doit exister
        if (consultation.getRendezVousId() == null) {
            throw new RuntimeException("L'ID du rendez-vous est obligatoire.");
        }

        // Call the remote service to get RDV details
        RendezVousDto rdv = rendezVousClient.getRendezVous(consultation.getRendezVousId());

        // Rule: Date Consultation >= Date RDV
        // Note: rdv.getDateRdv() is LocalDateTime, consultation.getDateConsultation() is LocalDate.
        // We compare the date parts.
        if (consultation.getDateConsultation().isBefore(rdv.getDateRdv().toLocalDate())) {
            throw new RuntimeException("Date de consultation invalide. Elle doit être postérieure ou égale à la date du rendez-vous.");
        }

        return repository.save(consultation);
    }

    public List<Consultation> list() {
        return repository.findAll();
    }

    public Consultation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation introuvable : " + id));
    }

    public List<Consultation> getByRendezVousId(Long rdvId) {
        return repository.findByRendezVousId(rdvId);
    }

    public Consultation update(Long id, Consultation newInfo) {
        Consultation existing = getById(id);

        if (newInfo.getRapport() != null) {
            if (newInfo.getRapport().length() < 10) {
                throw new RuntimeException("Rapport de consultation insuffisant.");
            }
            existing.setRapport(newInfo.getRapport());
        }
        // Note: Usually we don't change the Date or RDV ID of a past consultation,
        // but you can add those setters here if required.

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Consultation introuvable.");
        }
        repository.deleteById(id);
    }
}