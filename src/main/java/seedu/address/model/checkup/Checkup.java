package seedu.address.model.checkup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * test
 */
public class Checkup {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String MESSAGE_INVALID_DATETIME = "Time slot is not available";
    public static final String MESSAGE_OUTSIDE_BUSINESS_HOURS =
            "Checkup must be scheduled between 9:00 AM and 5:00 PM";
    public static final String MESSAGE_PAST_DATE =
            "Checkup cannot be scheduled in the past";
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(17, 0);

    private final Person patient;
    private final Person nurse;
    private final LocalDateTime checkupDateTime;


    /**
     * test
     * @param patient test
     * @param nurse test
     * @param checkupDate test
     * @param checkupTime test
     * @throws ParseException test
     */
    public Checkup(Person patient, Person nurse, LocalDate checkupDate, LocalTime checkupTime) throws ParseException {
        allNonNull(patient, nurse, checkupDate, checkupTime);
        checkArgument(isValidCheckup(patient, nurse, checkupDate, checkupTime), MESSAGE_CONSTRAINTS);
        this.patient = patient;
        this.nurse = nurse;
        this.checkupDateTime = createCheckupDateTime(checkupDate, checkupTime);
    }

    /**
     * test
     * @param patient test
     * @param nurse test
     * @param checkupDate test
     * @param checkupTime test
     */
    public void allNonNull(Person patient, Person nurse, LocalDate checkupDate, LocalTime checkupTime) {
        requireNonNull(patient);
        requireNonNull(nurse);
        requireNonNull(checkupDate);
        requireNonNull(checkupTime);
    }

    /**
     * Returns true if a given string is a valid checkUpDate name.
     */
    public static boolean isValidCheckup(Person patient, Person nurse, LocalDate checkupDate, LocalTime checkupTime)
            throws ParseException {
        LocalDateTime checkupDateTime = createCheckupDateTime(checkupDate, checkupTime);
        if (!isWithinBusinessHours(checkupDateTime)) {
            throw new ParseException(MESSAGE_OUTSIDE_BUSINESS_HOURS);
        }
        if (!isNotInPast(checkupDateTime)) {
            throw new ParseException(MESSAGE_PAST_DATE);
        }
        if (!isTimeSlotAvailable(patient, nurse, checkupDateTime)) {
            throw new ParseException(MESSAGE_INVALID_DATETIME);
        }
        return true;
    }

    private static LocalDateTime createCheckupDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
    private static boolean isWithinBusinessHours(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return !time.isBefore(START_TIME) && !time.isAfter(END_TIME);
    }

    private static boolean isNotInPast(LocalDateTime dateTime) {
        return !dateTime.isBefore(LocalDateTime.now());
    }
    private static boolean isTimeSlotAvailable(Person patient, Person nurse, LocalDateTime dateTime)
            throws ParseException {
        if (!isWithinBusinessHours(dateTime)) {
            throw new ParseException(MESSAGE_OUTSIDE_BUSINESS_HOURS);
        }
        if (!isNotInPast(dateTime)) {
            throw new ParseException(MESSAGE_PAST_DATE);
        }
        return !hasPatientConflict(patient, dateTime) && !hasNurseConflict(nurse, dateTime);
    }

    private static boolean hasPatientConflict(Person patient, LocalDateTime dateTime) {
        return patient.getCheckups().stream()
                .anyMatch(checkup -> checkup.getDateTime().equals(dateTime));
    }

    private static boolean hasNurseConflict(Person nurse, LocalDateTime dateTime) {
        return nurse.getCheckups().stream()
                .anyMatch(checkup -> checkup.getDateTime().equals(dateTime));
    }

    public LocalDateTime getDateTime() {
        return this.checkupDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Checkup otherCheckup)) {
            return false;
        }

        return patient.equals(otherCheckup.patient)
                && nurse.equals(otherCheckup.nurse)
                && checkupDateTime.equals(otherCheckup.checkupDateTime);
    }

    public LocalDate getCheckupDate() {
        return this.checkupDateTime.toLocalDate();
    }

    public LocalTime getCheckupTime() {
        return this.checkupDateTime.toLocalTime();
    }

    public Person getPatient() {
        return this.patient;
    }

    public Person getNurse() {
        return this.nurse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, nurse, checkupDateTime);
    }

    @Override
    public String toString() {
        return this.checkupDateTime.toString();
    }

}
