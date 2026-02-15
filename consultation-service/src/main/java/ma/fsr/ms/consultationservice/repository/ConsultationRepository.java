package ma.fsr.ms.consultationservice.repository;

import ma.fsr.ms.consultationservice.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    // You must declare this method so Spring can generate the query
    // This works because your Consultation model has a field named 'rendezVousId'
    List<Consultation> findByRendezVousId(Long rendezVousId);
}