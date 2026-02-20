package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salon.ekat.hairStylist.dto.WorkdayDTO;
import salon.ekat.hairStylist.service.WorkdayService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workdays")
@Slf4j
public class WorkdayController {
    private final WorkdayService workdayService;

    @Autowired
    public WorkdayController(WorkdayService workdayService) {
        this.workdayService = workdayService;
    }

    @GetMapping("/{masterId}")
    public ResponseEntity<WorkdayDTO> getWorkday(@PathVariable Long masterId, @RequestParam LocalDate date) {
        log.info("Получен GET-запрос на получение рабочего дня мастера с id={}, на дату {}", masterId, date);
        return workdayService.getWorkday(masterId, date)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all/{masterId}")
    public ResponseEntity<List<WorkdayDTO>> getAllWorkdays(@PathVariable Long masterId) {
        log.info("Получен GET-запрос на получение всех рабочих дней мастера с id={}", masterId);
        return ResponseEntity.ok(workdayService.getAllWorkdays(masterId));
    }

    @PostMapping
    public ResponseEntity<WorkdayDTO> addWorkday(@RequestBody WorkdayDTO workdayDTO) {
        log.info("Получен POST-запрос на создания рабочего дня для мастера с id={}, на дату {}",
                workdayDTO.getMasterId(), workdayDTO.getDayOfWork());
        return ResponseEntity.ok(workdayService.saveWorkday(workdayDTO));
    }

/*    @PutMapping
    public ResponseEntity<WorkdayDTO> updateWorkday(@RequestBody WorkdayDTO workdayDTO) {
        log.info("Получен PUT-запрос на обновление рабочего дня для мастера с id={}, на дату {}",
                workdayDTO.getMasterId(), workdayDTO.getDayOfWork());
        return ResponseEntity.ok(workdayService.saveWorkday(workdayDTO));
    }*/

    @DeleteMapping("/{masterId}")
    public void deleteWorkday(@PathVariable Long masterId, @RequestParam LocalDate date) {
        log.info("Получен DELETE-запрос на удаление рабочего дня для мастера с id={}, на дату {}", masterId, date);
        workdayService.deleteWorkday(masterId, date);
    }
}
