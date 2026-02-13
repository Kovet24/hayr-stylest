package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salon.ekat.hairStylist.dto.WorkdayDTO;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workdays")
@Slf4j
public class WorkdayController {
    @GetMapping("/{masterId}")
    public ResponseEntity<WorkdayDTO> getWorkdayOfMaster(@PathVariable Long masterId, @RequestParam LocalDate date) {
        log.info("Получен GET-запрос на получение рабочего дня мастера с id={}, на дату {}", masterId, date);
        return ResponseEntity.ok().build();
    }
}
