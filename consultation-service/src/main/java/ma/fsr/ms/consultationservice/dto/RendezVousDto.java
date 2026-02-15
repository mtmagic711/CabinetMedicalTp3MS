package ma.fsr.ms.consultationservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RendezVousDto {
    private Long id;
    private LocalDateTime dateRdv;
    private String statut;
}