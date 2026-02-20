package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salon.ekat.hairStylist.dto.ProcedureDTO;
import salon.ekat.hairStylist.service.ProcedureService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Slf4j
public class ProcedureController {
    private final ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProcedureDTO> findById(@PathVariable Long id) {
        log.info("Получен GET-запрос на получение услуги с id={}", id);
        return procedureService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProcedureDTO>> findAll() {
        log.info("Получен GET-запрос на получение всех услуг");
        return ResponseEntity.ok(procedureService.findAll());
    }

    // POST метод на добавление, но может быть будет в AdminController'е
}
