package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Procedure;

import java.util.List;
import java.util.Optional;

public interface ProcedureRepository {
    Optional<Procedure> findById(Long id);

    List<Procedure> findAll();

    Procedure save(Procedure procedure);

    void deleteById(Long id);
}
