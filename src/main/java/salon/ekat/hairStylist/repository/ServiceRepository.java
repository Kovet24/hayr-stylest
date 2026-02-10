package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    Optional<Service> findById(Long id);

    List<Service> findAll();

    Service save(Service service);

    void deleteById(Long id);
}
