package salon.ekat.hairStylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salon.ekat.hairStylist.dto.ClientDTO;
import salon.ekat.hairStylist.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        log.info("Получен GET-запрос на получение клиента c id={}", id);
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        log.info("Получен GET-запрос на получение всех клиентов");
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        log.info("Получен POST-запрос на добавление клиента");
        return new ResponseEntity<>(clientService.save(clientDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO) {
        log.info("Получен PUT-запрос на обновление клиента с id={}", clientDTO.getId());
        return ResponseEntity.ok(clientService.save(clientDTO));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(Long id) {
        log.info("Получен DELETE-запрос на удаление клиента с id={}", id);
        clientService.deleteById(id);
    }
}
