package ma.fsr.ms.consultationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateConsultation;

    @Column(nullable = false, length = 5000) // "Rapport est obligatoire"
    private String rapport;

    @Column(nullable = false)
    private Long rendezVousId;
}