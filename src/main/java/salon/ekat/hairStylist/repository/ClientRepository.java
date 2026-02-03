package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(Long id);

    List<Client> findAll();

    Client save(Client client);

    void deleteById(Long id);
}
