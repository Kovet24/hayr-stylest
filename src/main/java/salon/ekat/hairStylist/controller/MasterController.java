package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salon.ekat.hairStylist.dto.MasterDTO;
import salon.ekat.hairStylist.service.MasterService;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
@Slf4j
public class MasterController {
    private final MasterService masterService;

    @Autowired
    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterDTO> findById(@PathVariable Long id) {
        log.info("Получен GET-запрос на получение мастера c id={}", id);
        return masterService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MasterDTO>> findAll() {
        log.info("Получен GET-запрос на получение всех мастеров");
        return ResponseEntity.ok(masterService.findAll());
    }

 /*   @GetMapping("/{id}/services")

    @GetMapping("/{id}/shedule")*/
}
