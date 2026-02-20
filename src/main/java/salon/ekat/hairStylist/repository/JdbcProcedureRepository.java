package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Procedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcProcedureRepository implements ProcedureRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Procedure> findById(Long id) {
        String sql = "SELECT * FROM procedures WHERE id=?";
        List<Procedure> result = jdbcTemplate.query(sql, this::rowMapper, id);

        if (result.isEmpty()) {
            log.info("Услуга с id={} не найден", id);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одной услуги нашли по id: %d".formatted(id));
        }

        return Optional.of(result.getFirst());
    }

    @Override
    public List<Procedure> findAll() {
        String sql = "SELECT * FROM procedures";
        List<Procedure> procedures = jdbcTemplate.query(sql, this::rowMapper);

        log.info("Получены все услуги");
        return procedures;
    }

    @Override
    public Procedure save(Procedure procedure) {
        if (procedure.getId() == null || procedure.getId() == 0) {
            Procedure addedProcedure = addProcedure(procedure);
            log.info("Добавлена новая услуга с id={}", addedProcedure.getId());
            return addedProcedure;
        } else {
            Procedure updatedProcedure = updateProcedure(procedure);
            log.info("Обновлена услуга с id={}", updatedProcedure.getId());
            return updatedProcedure;
        }
    }

    private Procedure addProcedure(Procedure procedure) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("procedures")
                .usingGeneratedKeyColumns("id");

        Long procedureId = jdbcInsert.executeAndReturnKey(Map.of("id", procedure.getId())).longValue();
        procedure.setId(procedureId);

        return procedure;
    }

    private Procedure updateProcedure(Procedure procedure) {
        String sql = "UPDATE procedures SET name=?, description=?, price=?, duration=? WHERE id=?";
        Long id = procedure.getId();
        int rows = jdbcTemplate.update(sql, procedure.getName(), procedure.getDescription(),
                procedure.getPrice(), procedure.getDuration(), id);

        if (rows == 1) {
            return procedure;
        } else if (rows == 0) {
            throw new NoSuchElementException("Не найдена услуга с id=%d".formatted(id));
        } else {
            throw new IllegalStateException("Больше одной услуги нашли по id: %d".formatted(id));
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM procedures WHERE id=?";
        int result = jdbcTemplate.update(sql, id);

        if (result == 1) {
            log.info("Удалена услуга с id={}", id);
        } else {
            throw new NoSuchElementException("Не найдена услуга с id=%d".formatted(id));
        }
    }

    private Procedure rowMapper(ResultSet rs, int rowNum) {
        try {
            return Procedure.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getDouble("price"))
                    .duration(rs.getInt("duration"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
