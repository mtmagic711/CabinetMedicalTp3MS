package ma.fsr.ms.rendezvousservice.service;

import ma.fsr.ms.rendezvousservice.client.MedecinClient;
import ma.fsr.ms.rendezvousservice.client.PatientClient;
import ma.fsr.ms.rendezvousservice.model.RendezVous;
import ma.fsr.ms.rendezvousservice.model.StatutRdv;
import ma.fsr.ms.rendezvousservice.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository repository;

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private MedecinClient medecinClient;


    public RendezVous create(RendezVous rdv) {

        if (rdv.getDateRdv() == null) {
            throw new RuntimeException("La date du rendez-vous est obligatoire.");
        }

        if (rdv.getDateRdv().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La date du rendez-vous doit être future.");
        }

        patientClient.checkPatientExists(rdv.getPatientId());
        medecinClient.checkMedecinExists(rdv.getMedecinId());

        rdv.setStatut(StatutRdv.PLANIFIE);
        return repository.save(rdv);
    }

    public List<RendezVous> list() {
        return repository.findAll();
    }

    public RendezVous getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rendez-vous introuvable : id = " + id)
                );
    }

    public RendezVous update(Long id, RendezVous rdv) {

        RendezVous existing = getById(id);

        if (rdv.getDateRdv() != null) {
            if (rdv.getDateRdv().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("La date du rendez-vous doit être future.");
            }
            existing.setDateRdv(rdv.getDateRdv());
        }

        if (rdv.getPatientId() != null) {
            patientClient.checkPatientExists(rdv.getPatientId());
            existing.setPatientId(rdv.getPatientId());
        }

        if (rdv.getMedecinId() != null) {
            medecinClient.checkMedecinExists(rdv.getMedecinId());
            existing.setMedecinId(rdv.getMedecinId());
        }

        return repository.save(existing);
    }

    public RendezVous updateStatut(Long id, StatutRdv statut) {

        if (statut == null) {
            throw new RuntimeException(
                    "Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE."
            );
        }

        RendezVous rdv = getById(id);
        rdv.setStatut(statut);
        return repository.save(rdv);
    }

    public void delete(Long id) {
        RendezVous rdv = getById(id);
        repository.delete(rdv);
    }
}