package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salon.ekat.hairStylist.dto.ServiceDTO;
import salon.ekat.hairStylist.service.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Slf4j
public class ServiceController {
    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable Long id) {
        log.info("Получен GET-запрос на получение услуги с id={}", id);
        return serviceService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> findAll() {
        log.info("Получен GET-запрос на получение всех услуг");
        return ResponseEntity.ok(serviceService.findAll());
    }

    // POST метод на добавление, но может быть будет в AdminController'е
}
