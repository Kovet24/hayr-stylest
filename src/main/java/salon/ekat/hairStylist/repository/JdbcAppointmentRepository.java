package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcAppointmentRepository implements AppointmentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAppointmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        String sql = "SELECT * FROM appointments WHERE id=?";
        List<Appointment> result = jdbcTemplate.query(sql, this::rowMapper, id);

        if (result.isEmpty()) {
            log.info("Не найдена запись с id={}", id);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одной записи найдено с id=%d".formatted(id));
        }

        log.info("Получена запись с id={}", id);
        return Optional.of(result.getFirst());
    }

    @Override
    public Optional<Appointment> findByMasterIdAndStartDateTime(Long masterId, LocalDateTime startDateTime) {
        String sql = "SELECT * FROM appointments WHERE master_id=? AND start_date_time=?";
        List<Appointment> result = jdbcTemplate.query(sql, this::rowMapper, masterId, startDateTime);

        if (result.isEmpty()) {
            log.info("Не найдена запись мастера с id={}, на {}", masterId, startDateTime);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одной записи найдено мастера с id=%d на %s"
                    .formatted(masterId, startDateTime));
        }

        log.info("Получена запись мастера с id={} на {}", masterId, startDateTime);
        return Optional.of(result.getFirst());
    }

    @Override
    public Optional<Appointment> findByClientIdAndStartDateTime(Long clientId, LocalDateTime startDateTime) {
        String sql = "SELECT * FROM appointments WHERE client_id=? AND start_date_time=?";
        List<Appointment> result = jdbcTemplate.query(sql, this::rowMapper, clientId, startDateTime);

        if (result.isEmpty()) {
            log.info("Не найдена запись клиента с id={}, на {}", clientId, startDateTime);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одной записи найдено клиента с id=%d на %s"
                    .formatted(clientId, startDateTime));
        }

        log.info("Получена запись клиента с id={} на {}", clientId, startDateTime);
        return Optional.of(result.getFirst());
    }

    @Override
    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointments";
        List<Appointment> appointments = jdbcTemplate.query(sql, this::rowMapper);

        log.info("Получены все записи");
        return appointments;
    }

    @Override
    public List<Appointment> findConflictingAppointments(Appointment appointment) {
        String sql = "SELECT * FROM appointments WHERE start_date_time < ? AND end_date_time > ?";
        LocalDateTime startDateTime = appointment.getStartDateTime();
        LocalDateTime endDateTime = appointment.getEndDateTime();
        List<Appointment> result = jdbcTemplate.query(sql, this::rowMapper, endDateTime, startDateTime);

        log.info("Получены пересекающиеся записи");
        return result;
    }

    @Override
    public List<Appointment> findAllByMasterId(Long masterId) {
        String sql = "SELECT * FROM appointments WHERE master_id=?";
        List<Appointment> appointments = jdbcTemplate.query(sql, this::rowMapper, masterId);

        log.info("Получены все записи мастера с id={}", masterId);
        return appointments;
    }

    @Override
    public List<Appointment> findAllByClientId(Long clientId) {
        String sql = "SELECT * FROM appointments WHERE client_id=?";
        List<Appointment> appointments = jdbcTemplate.query(sql, this::rowMapper, clientId);

        log.info("Получены все записи клиента с id={}", clientId);
        return appointments;
    }

    @Override
    public Appointment save(Appointment appointment) {
        Long id = appointment.getId();

        if (id == null || id == 0) {
            Appointment addedAppointment = addAppointment(appointment);
            Long clientId = addedAppointment.getClientId();
            LocalDateTime startDateTime = addedAppointment.getStartDateTime();
            log.info("Добавлена запись на {} от клиента с id={}", clientId, startDateTime);
            return addedAppointment;
        } else {
            Appointment updatedAppointment = updateAppointment(appointment);
            log.info("Обновлена запись с id={}", updatedAppointment.getId());
            return updatedAppointment;
        }
    }

    private Appointment addAppointment(Appointment appointment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("appointments")
                .usingGeneratedKeyColumns("id");
        Long id = jdbcInsert.executeAndReturnKey(Map.of("id", appointment.getId())).longValue();
        appointment.setId(id);

        return appointment;
    }

    private Appointment updateAppointment(Appointment appointment) {
        String sql = """
                UPDATE appointments SET master_id=?, client_id=?, procedure_id=?, start_date_time=?, end_date_time=?
                WHERE id=?
                """;
        Long id = appointment.getId();
        Long masterId = appointment.getMasterId();
        Long clientId = appointment.getClientId();
        Long serviceId = appointment.getProcedureId();
        LocalDateTime startDateTime = appointment.getStartDateTime();
        LocalDateTime endDateTime = appointment.getEndDateTime();
        int rows = jdbcTemplate.update(sql, masterId, clientId, serviceId, startDateTime, endDateTime, id);

        if (rows == 1) {
            return appointment;
        } else if (rows == 0) {
            throw new NoSuchElementException("Не найдена запись с id=%d".formatted(id));
        } else {
            throw new IllegalStateException("Больше одной записи найдено с id=%d".formatted(id));
        }
    }

    // Статус ещё не добавлен в Appointment
    @Override
    public Appointment updateStatusById(Long id, String status) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM appointments WHERE id=?";
        int result = jdbcTemplate.update(sql, id);

        if (result == 1) {
            log.info("Удалена запись с id={}", id);
        } else {
            throw new NoSuchElementException("Не найдена запись с id=%d".formatted(id));
        }
    }

    private Appointment rowMapper(ResultSet rs, int rowNum) {
        try {
            return Appointment.builder()
                    .id(rs.getLong("id"))
                    .masterId(rs.getLong("master_id"))
                    .clientId(rs.getLong("client_id"))
                    .procedureId(rs.getLong("procedure_id"))
                    .startDateTime(rs.getTimestamp("start_date_time").toLocalDateTime())
                    .endDateTime(rs.getTimestamp("end_date_time").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
