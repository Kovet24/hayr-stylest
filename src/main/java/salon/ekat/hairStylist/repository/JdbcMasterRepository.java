package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Master;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcMasterRepository implements MasterRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMasterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Master> findById(Long id) {
        String sql = "SELECT * FROM masters WHERE id=?";
        List<Master> result = jdbcTemplate.query(sql, this::rowMapper, id);

        if (result.isEmpty()) {
            log.info("Мастер с id={} не найден", id);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одно мастера нашли по id: %d".formatted(id));
        }

        log.info("Получен мастер с id={}", id);
        return Optional.of(result.getFirst());
    }

    @Override
    public List<Master> findAll() {
        String sql = "SELECT * FROM masters";
        List<Master> masters = jdbcTemplate.query(sql, this::rowMapper);

        log.info("Получены все мастера");
        return masters;
    }

    @Override
    public Master save(Master master) {
        if (master.getId() == null || master.getId() == 0) {
            Master addedMaster = addMaster(master);
            log.info("Добавлен новый мастер с id={}", addedMaster.getId());
            return addedMaster;
        } else {
            Master updatedMaster = updateMaster(master);
            log.info("Обновлен мастер с id={}", master.getId());
            return updatedMaster;
        }
    }

    private Master addMaster(Master master) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("master")
                .usingGeneratedKeyColumns("id");

        Long masterId = jdbcInsert.executeAndReturnKey(Map.of("id", master.getId())).longValue();
        master.setId(masterId);

        return master;
    }

    private Master updateMaster(Master master) {
        String sql = "UPDATE masters SET name=?, number=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, master.getName(), master.getNumber(), master.getId());

        if (rows == 1) {
            return master;
        } else {
            throw new IllegalStateException("Больше одно мастера нашли по id: %d".formatted(master.getId()));
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM masters WHERE id=?";
        int result = jdbcTemplate.update(sql, id);

        if (result == 1) {
            log.info("Удален мастер с id={}", id);
        } else {
            throw new NoSuchElementException("Не удален мастер с id=%d".formatted(id));
        }
    }

    private Master rowMapper(ResultSet rs, int rowNum) {
        try {
            return Master.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .number(rs.getInt("number"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
