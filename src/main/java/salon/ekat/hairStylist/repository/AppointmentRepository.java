package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Optional<Appointment> findById(Long id);

    Optional<Appointment> findByMasterIdAndStartDateTime(Long masterId, LocalDateTime startDateTime);

    Optional<Appointment> findByClientIdAndStartDateTime(Long masterId, LocalDateTime startDateTime);

    List<Appointment> findAll();

    List<Appointment> findConflictingAppointments(Appointment appointment);

    List<Appointment> findAllByMasterId(Long masterId);

    List<Appointment> findAllByClientId(Long clientId);

    Appointment save(Appointment appointment);

    Appointment updateStatusById(Long id, String status);

    void deleteById(Long id);
}
