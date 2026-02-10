package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcServiceRepository implements ServiceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcServiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Service> findById(Long id) {
        String sql = "SELECT * FROM services WHERE id=?";
        List<Service> result = jdbcTemplate.query(sql, this::rowMapper, id);

        if (result.isEmpty()) {
            log.info("Услуга с id={} не найден", id);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одной услуги нашли по id: %d".formatted(id));
        }

        return Optional.of(result.getFirst());
    }

    @Override
    public List<Service> findAll() {
        String sql = "SELECT * FROM services";
        List<Service> services = jdbcTemplate.query(sql, this::rowMapper);

        log.info("Получены все услуги");
        return services;
    }

    @Override
    public Service save(Service service) {
        if (service.getId() == null || service.getId() == 0) {
            Service addedService = addService(service);
            log.info("Добавлена новая услуга с id={}", addedService.getId());
            return addedService;
        } else {
            Service updatedService = updateService(service);
            log.info("Обновлена услуга с id={}", updatedService.getId());
            return updatedService;
        }
    }

    private Service addService(Service service) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("services")
                .usingGeneratedKeyColumns("id");

        Long serviceId = jdbcInsert.executeAndReturnKey(Map.of("id", service.getId())).longValue();
        service.setId(serviceId);

        return service;
    }

    private Service updateService(Service service) {
        String sql = "UPDATE service SET name=?, description=?, price=?, duration=? WHERE id=?";
        Long id = service.getId();
        int rows = jdbcTemplate.update(sql, service.getName(), service.getDescription(),
                service.getPrice(), service.getDuration(), id);

        if (rows == 1) {
            return service;
        } else if (rows == 0) {
            throw new NoSuchElementException("Не найдена услуга с id=%d".formatted(id));
        } else {
            throw new IllegalStateException("Больше одной услуги нашли по id: %d".formatted(id));
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM services WHERE id=?";
        int result = jdbcTemplate.update(sql, id);

        if (result == 1) {
            log.info("Удалена услуга с id={}", id);
        } else {
            throw new NoSuchElementException("Не найдена услуга с id=%d".formatted(id));
        }
    }

    private Service rowMapper(ResultSet rs, int rowNum) {
        try {
            return Service.builder()
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
