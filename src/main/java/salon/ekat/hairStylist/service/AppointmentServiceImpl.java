package salon.ekat.hairStylist.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.AppointmentDTO;
import salon.ekat.hairStylist.entity.Appointment;
import salon.ekat.hairStylist.entity.Procedure;
import salon.ekat.hairStylist.entity.Workday;
import salon.ekat.hairStylist.exception.ConflictingAppointmentsException;
import salon.ekat.hairStylist.mapper.AppointmentMapper;
import salon.ekat.hairStylist.repository.AppointmentRepository;
import salon.ekat.hairStylist.repository.ProcedureRepository;
import salon.ekat.hairStylist.repository.WorkdayRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final WorkdayRepository workdayRepository;
    private final ProcedureRepository procedureRepository;

    @Autowired
    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            WorkdayRepository workdayRepository,
            ProcedureRepository procedureRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.workdayRepository = workdayRepository;
        this.procedureRepository = procedureRepository;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<AppointmentDTO> findByMasterIdAndStartDateTime(Long masterId, LocalDateTime startDateTime) {
        Optional<Appointment> appointment =
                appointmentRepository.findByMasterIdAndStartDateTime(masterId, startDateTime);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<AppointmentDTO> findByClientIdAndStartDateTime(Long clientId, LocalDateTime startDateTime) {
        Optional<Appointment> appointment =
                appointmentRepository.findByClientIdAndStartDateTime(clientId, startDateTime);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<AppointmentDTO> findAllByMasterId(Long masterId) {
        return AppointmentMapper.mapToListDTO(appointmentRepository.findAllByMasterId(masterId));
    }

    @Override
    public List<AppointmentDTO> findAllByClientId(Long clientId) {
        return AppointmentMapper.mapToListDTO(appointmentRepository.findAllByClientId(clientId));
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = AppointmentMapper.mapToObject(appointmentDTO);

        if (validateAppointment(appointment)) {
            changeEndDateTime(appointment);
            return AppointmentMapper.mapToDTO(appointmentRepository.save(appointment));
        }

        String message = "Запись на %s от клиента с id=%d пересекается с другими записями"
                .formatted(appointment.getStartDateTime(), appointment.getClientId());
        throw new ConflictingAppointmentsException(message);
    }

    private void changeEndDateTime(Appointment appointment) {
        Optional<Procedure> procedure = procedureRepository.findById(appointment.getProcedureId());
        int duration = 0;

        if (procedure.isPresent()) {
            duration = procedure.get().getDuration();
        }

        LocalDateTime startDateTime = appointment.getStartDateTime();
        appointment.setEndDateTime(startDateTime.plusMinutes(duration));
    }

    private boolean validateAppointment(Appointment appointment) {
        Long masterId = appointment.getMasterId();
        LocalDate date = appointment.getStartDateTime().toLocalDate();
        Optional<Workday> workdayOptional = workdayRepository.findByMasterIdAndDayOfWork(masterId, date);

        if (workdayOptional.isPresent()) {
            Workday workday = workdayOptional.get();

            if (validateWorkday(appointment, workday)) {
                if (validateBreak(appointment, workday)) {
                    return validateNoConflicts(appointment);
                }
            }
        }

        return false;
    }

    private boolean validateWorkday(Appointment appointment, Workday workday) {
        LocalTime shiftStart = workday.getShiftStart();
        LocalTime shiftEnd = workday.getShiftEnd();
        LocalTime startTime = appointment.getStartDateTime().toLocalTime();
        LocalTime endTime = appointment.getEndDateTime().toLocalTime();

        return !shiftStart.isAfter(startTime) && !shiftEnd.isBefore(endTime);
    }

    private boolean validateBreak(Appointment appointment, Workday workday) {
        LocalTime breakStart = workday.getBreakStart();
        LocalTime breakEnd = workday.getBreakEnd();
        LocalTime startTime = appointment.getStartDateTime().toLocalTime();
        LocalTime endTime = appointment.getEndDateTime().toLocalTime();

        return breakStart.isAfter(endTime) || breakEnd.isBefore(startTime);
    }

    private boolean validateNoConflicts(Appointment appointment) {
        List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(appointment);
        return conflictingAppointments.isEmpty();
    }

    @Override
    public AppointmentDTO updateStatusById(Long id, String status) {
        // В репозитории ещё нет реализации
        Appointment updatedAppointment = appointmentRepository.updateStatusById(id, status);
        return AppointmentMapper.mapToDTO(updatedAppointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
