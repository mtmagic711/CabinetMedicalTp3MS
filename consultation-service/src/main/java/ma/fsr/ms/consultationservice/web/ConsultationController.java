package ma.fsr.ms.consultationservice.web;

import ma.fsr.ms.consultationservice.model.Consultation;
import ma.fsr.ms.consultationservice.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService service;

    @GetMapping
    public ResponseEntity<List<Consultation>> getAll() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @GetMapping("/rendezvous/{rdvId}")
    public ResponseEntity<List<Consultation>> getByRendezVousId(@PathVariable Long rdvId) {
        return ResponseEntity.ok(service.getByRendezVousId(rdvId));
    }

    @PostMapping
    public ResponseEntity<Consultation> create(@RequestBody Consultation consultation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(consultation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> update(@PathVariable Long id, @RequestBody Consultation consultation) {
        return ResponseEntity.ok(service.update(id, consultation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}