package salon.ekat.hairStylist.validator;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.entity.Appointment;
import salon.ekat.hairStylist.entity.Workday;
import salon.ekat.hairStylist.exception.ValidationAppointmentException;
import salon.ekat.hairStylist.repository.AppointmentRepository;
import salon.ekat.hairStylist.repository.WorkdayRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class AppointmentValidator {
    public void validateAppointment(
            Appointment appointment,
            AppointmentRepository appointmentRepository,
            WorkdayRepository workdayRepository
    ) {
        Long masterId = appointment.getMasterId();
        LocalDate date = appointment.getStartDateTime().toLocalDate();
        Optional<Workday> workdayOptional = workdayRepository.findByMasterIdAndDayOfWork(masterId, date);

        if (workdayOptional.isPresent()) {
            Workday workday = workdayOptional.get();
            validateWorkday(appointment, workday);
            validateBreak(appointment, workday);
            validateNoConflicts(appointment, appointmentRepository);
        } else {
            String message = "Нет рабочего дня мастера с id=%d на %s".formatted(masterId, date);
            throw new ValidationAppointmentException(message);
        }
    }

    public void validateWorkday(Appointment appointment, Workday workday) {
        LocalTime shiftStart = workday.getShiftStart();
        LocalTime shiftEnd = workday.getShiftEnd();
        LocalTime startTime = appointment.getStartDateTime().toLocalTime();
        LocalTime endTime = appointment.getEndDateTime().toLocalTime();

        if (shiftStart.isAfter(startTime) || shiftEnd.isBefore(endTime)) {
            String message = "Запись на %s, клиента с id=%d, выходит за рамки рабочего дня мастера с id=%d"
                    .formatted(appointment.getStartDateTime(), appointment.getClientId(), appointment.getMasterId());
            throw new ValidationAppointmentException(message);
        }
    }

    public void validateBreak(Appointment appointment, Workday workday) {
        LocalTime breakStart = workday.getBreakStart();
        LocalTime breakEnd = workday.getBreakEnd();
        LocalTime startTime = appointment.getStartDateTime().toLocalTime();
        LocalTime endTime = appointment.getEndDateTime().toLocalTime();

        if (breakStart.isBefore(endTime) && breakEnd.isAfter(startTime)) {
            String message = "Запись на %s, клиента с id=%d, пересекает перерыв мастера с id=%d"
                    .formatted(appointment.getStartDateTime(), appointment.getClientId(), appointment.getMasterId());
            throw new ValidationAppointmentException(message);
        }
    }

    public void validateNoConflicts(Appointment appointment, AppointmentRepository appointmentRepository) {
        List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(appointment);

        if (!conflictingAppointments.isEmpty()) {
            String message = "Запись на %s, клиента с id=%d, пересекается с другими записями"
                    .formatted(appointment.getStartDateTime(), appointment.getClientId());
            throw new ValidationAppointmentException(message);
        }
    }
}
