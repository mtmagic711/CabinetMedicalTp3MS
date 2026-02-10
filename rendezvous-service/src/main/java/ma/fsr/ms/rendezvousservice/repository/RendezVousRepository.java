package ma.fsr.ms.rendezvousservice.repository;

import ma.fsr.ms.rendezvousservice.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}