package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salon.ekat.hairStylist.dto.AppointmentDTO;
import salon.ekat.hairStylist.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        log.info("Получен GET-запрос на получение записи с id={}", id);
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/master/{masterId}/{date}")
    public ResponseEntity<AppointmentDTO> findByMasterIdAndStartDateTime(
            @PathVariable Long masterId, @PathVariable LocalDateTime date
    ) {
        log.info("Получен GET-запрос на получение записей на {} мастера с id={}", date, masterId);
        return appointmentService.findByMasterIdAndStartDateTime(masterId, date)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("client/{clientId}/{date}")
    public ResponseEntity<AppointmentDTO> findByClientIdAndStartDateTime(
            @PathVariable Long clientId, @PathVariable LocalDateTime date
    ) {
        log.info("Получен GET-запрос на получение записей на {} клиента с id={}", date, clientId);
        return appointmentService.findByClientIdAndStartDateTime(clientId, date)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/master/{masterId}")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsByMasterId(@PathVariable Long masterId) {
        log.info("Получен GET-запрос на получение всех записей мастера с id={}", masterId);
        return ResponseEntity.ok(appointmentService.findAllByMasterId(masterId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsByClientId(@PathVariable Long clientId) {
        log.info("Получен GET-запрос на получение всех записей клиента с id={}", clientId);
        return ResponseEntity.ok(appointmentService.findAllByClientId(clientId));
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        log.info("Получен POST-запрос на создании записи на {}, от клиента с id={}",
                appointmentDTO.getStartDateTime(), appointmentDTO.getClientId());
        return ResponseEntity.ok(appointmentService.save(appointmentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        log.info("Получен POST-запрос на обновление записи с id={}", appointmentDTO.getId());
        return ResponseEntity.ok(appointmentService.save(appointmentDTO));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentDTO> updateStatusById(@PathVariable Long id, @RequestParam String status) {
        log.info("Получен PUT-запрос на изменение статуса записи с id={} на {}", id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Получен DELETE-запрос на отмену записи с id={}", id);
        appointmentService.deleteById(id);
    }
}
