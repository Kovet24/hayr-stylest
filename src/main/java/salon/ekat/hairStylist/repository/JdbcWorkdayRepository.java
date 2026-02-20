package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Workday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcWorkdayRepository implements WorkdayRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcWorkdayRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Workday> findByMasterIdAndDayOfWork(Long masterId, LocalDate date) {
        String sql = "SELECT * FROM workdays WHERE master_id=? AND day_of_work=?";
        List<Workday> result = jdbcTemplate.query(sql, this::rowMapper, masterId, date);

        if (result.isEmpty()) {
            log.info("Рабочий день на {}, мастера с id={}, не найден", date, masterId);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException(
                    "Больше одного рабочего дня на %s, мастера с id=%d".formatted(date, masterId));
        }

        log.info("Получен рабочий день на {}, мастера с id={}", date, masterId);
        return Optional.of(result.getFirst());
    }

    @Override
    public List<Workday> findAllByMasterId(Long masterId) {
        String sql = "SELECT * FROM workdays WHERE master_id=?";
        List<Workday> workdays = jdbcTemplate.query(sql, this::rowMapper, masterId);

        log.info("Получены рабочие дни мастера с id={}", masterId);
        return workdays;
    }

    @Override
    public Workday save(Workday workday) {
        addWorkday(workday);
        log.info("Добавлен новый рабочий день на {}, мастера с id={}", workday.getDayOfWork(), workday.getMasterId());
        return workday;
    }

    private Workday addWorkday(Workday workday) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("workdays");
        jdbcInsert.execute(new BeanPropertySqlParameterSource(workday));

        return workday;
    }

    // Вопрос как проверять на обновление.
    private Workday update(Workday workday) {
        String sql = """
                UPDATE workdays SET shift_start=?, shift_end=?, break_start=?, break_end=?
                WHERE master_id=? AND day_of_work=?
                """;
        Long masterId = workday.getMasterId();
        LocalDate dayOfWork = workday.getDayOfWork();
        LocalTime shiftStart = workday.getShiftStart();
        LocalTime shiftEnd = workday.getShiftEnd();
        LocalTime breakStart = workday.getBreakStart();
        LocalTime breakEnd = workday.getBreakEnd();
        int rows = jdbcTemplate.update(sql, shiftStart, shiftEnd, breakStart, breakEnd, masterId, dayOfWork);

        if (rows == 1) {
            log.info("Обновлен рабочий день мастера с id={} на {}", masterId, dayOfWork);
            return workday;
        } else if (rows == 0) {
            throw new NoSuchElementException("Не найден рабочий день мастера с id=%d на %s"
                    .formatted(masterId, dayOfWork));
        } else {
            throw new IllegalStateException("Больше одного рабочего дня мастера с id=%d на %s"
                    .formatted(masterId, dayOfWork));
        }
    }

    @Override
    public void deleteByMasterIdAndDayOfWork(Long masterId, LocalDate date) {
        String sql = "DELETE FROM workdays WHERE masterId=? AND day_of_work=?";
        int result = jdbcTemplate.update(sql, masterId, date);

        if (result == 1) {
            log.info("Удален рабочий день на {}, мастера с id={}", date, masterId);
        } else {
            throw new NoSuchElementException(
                    "Не найден рабочий день на %s, мастера с id=%d".formatted(date, masterId));
        }
    }

    private Workday rowMapper(ResultSet rs, int rowNum) {
        try {
            return Workday.builder()
                    .masterId(rs.getLong("master_id"))
                    .dayOfWork(rs.getDate("day_of_work").toLocalDate())
                    .shiftStart(rs.getTime("shift_start").toLocalTime())
                    .shiftEnd(rs.getTime("shift_end").toLocalTime())
                    .breakStart(rs.getTime("break_start").toLocalTime())
                    .breakEnd(rs.getTime("break_end").toLocalTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
