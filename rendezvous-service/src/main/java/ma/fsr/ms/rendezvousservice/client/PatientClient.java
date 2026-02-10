package ma.fsr.ms.rendezvousservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.gateway.url}")
    private String patientServiceUrl;

    public void checkPatientExists(Long patientId) {
        try {
            restTemplate.getForObject(
                    patientServiceUrl + "/api/patients/" + patientId,
                    Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Patient introuvable.");
        }
    }

}