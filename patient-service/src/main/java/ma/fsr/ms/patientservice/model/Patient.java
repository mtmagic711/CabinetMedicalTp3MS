package ma.fsr.ms.patientservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String telephone;
}