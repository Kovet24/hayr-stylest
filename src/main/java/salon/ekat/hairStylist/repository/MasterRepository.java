package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Master;

import java.util.List;
import java.util.Optional;

public interface MasterRepository {
    Optional<Master> findById(Long id);

    List<Master> findAll();

    Master save(Master master);

    void deleteById(Long id);
}
