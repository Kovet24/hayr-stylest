package salon.ekat.hairStylist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import salon.ekat.hairStylist.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcClientRepository implements ClientRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM clients WHERE id=?";
        List<Client> result = jdbcTemplate.query(sql, this::rowMapper, id);
        Optional<Client> client;

        if (result.isEmpty()) {
            log.info("Клиент с id={} не найден", id);
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new IllegalStateException("Больше одно клиента нашли по id: %d".formatted(id));
        }

        log.info("Получен клиент с id={}", id);
        return client = Optional.of(result.getFirst());
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = jdbcTemplate.query(sql, this::rowMapper);

        log.info("Получены все клиенты");
        return clients;
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == null || client.getId() == 0) {
            Client addedClient = addClient(client);
            log.info("Добавлен новый клиент с id={}", addedClient.getId());
            return addedClient;
        } else {
            Client updatedClient = updateClient(client);
            log.info("Обновлен клиент с id={}", client.getId());
            return updatedClient;
        }
    }

    private Client addClient(Client client) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("clients")
                .usingGeneratedKeyColumns("id");

        Long clientId = jdbcInsert.executeAndReturnKey(Map.of("id", client.getId())).longValue();
        client.setId(clientId);

        return client;
    }

    private Client updateClient(Client client) {
        String sql = "UPDATE clients SET name=?, number=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, client.getName(), client.getNumber(), client.getId());

        if (rows == 1) {
            return client;
        } else {
            throw new IllegalStateException("Больше одно клиента нашли по id: %d".formatted(client.getId()));
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM client WHERE id=?";
        int result = jdbcTemplate.update(sql, id);

        if (result == 1) {
            log.info("Удален клиент с id={}", id);
        } else {
            throw new NoSuchElementException("Не удален фильм с id=%d".formatted(id));
        }
    }

    private Client rowMapper(ResultSet rs, int rowNum) {
        try {
            return Client.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .number(rs.getInt("number"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
