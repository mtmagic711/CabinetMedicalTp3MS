package ma.fsr.ms.rendezvousservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MedecinClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.gateway.url}")
    private String medecinServiceUrl;

    public void checkMedecinExists(Long medecinId) {
        try {
            restTemplate.getForObject(
                    medecinServiceUrl + "/api/medecins/" + medecinId,
                    Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Médecin introuvable.");
        }
    }
}