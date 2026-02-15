package ma.fsr.ms.consultationservice.client;

import ma.fsr.ms.consultationservice.dto.RendezVousDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RendezVousClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${rendezvous.service.url}")
    private String rendezVousServiceUrl;

    // I renamed this method to 'getRendezVous' to match your Service call
    public RendezVousDto getRendezVous(Long id) {
        try {
            return restTemplate.getForObject(
                    rendezVousServiceUrl + "/internal/api/v1/rendezvous/" + id,
                    RendezVousDto.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Rendez-vous introuvable.");
        }
    }
}