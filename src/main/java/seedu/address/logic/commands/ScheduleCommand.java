package seedu.address.logic.commands;

import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_INVALID_PATIENT;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.checkup.Checkup;
import seedu.address.model.person.Person;

/**
 * Represent a command to schedule a checkup for a patient
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    public static final String ADD_SCHEDULE_COMMAND = "add for patient";
    public static final String DELETE_SCHEDULE_COMMAND = "delete for patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an appointment for a patient with a nurse.\n"
            + "Parameters: ACTION PATIENT_INDEX DATE TIME\n"
            + "Example: " + COMMAND_WORD + " " + ADD_SCHEDULE_COMMAND + " 1 01/01/2025 1400";

    public static final String MESSAGE_CHECKUP_CREATED = "Appointment for Patient %s has been "
            + "successfully created on %s at %s";

    public static final String MESSAGE_CHECKUP_DELETED = "Appointment for Patient %s has been "
            + "successfully deleted from %s at %s";
    public static final String MESSAGE_CHECKUP_DUPLICATE = "A checkup is already scheduled at this datetime.";
    public static final String MESSAGE_CHECKUP_DOES_NOT_EXIST = "Appointment does not exist";
    public static final String MISSING_ASSIGNED_NURSE = "Check up has been created for a patient without a nurse, "
             + "REMEMBER to assign a nurse promptly after this!";
    public static final String MESSAGE_CHECKUP_CLASH = "There's a checkup scheduled on %s! Please choose another"
        + " time / date";
    private final Index patientIndex;
    private final LocalDate checkupDate;
    private final LocalTime checkupTime;
    private final boolean isAdding;


    /**
     * Construct a ScheduleCommand
     *
     * @param patientIndex The index of the patient from the filtered list.
     * @param checkupDate The date of the scheduled checkup.
     * @param checkupTime The time of the scheduled checkup.
     */
    public ScheduleCommand(boolean isAdding, Index patientIndex, LocalDate checkupDate,
                           LocalTime checkupTime) {
        this.isAdding = isAdding;
        this.patientIndex = patientIndex;
        this.checkupDate = checkupDate;
        this.checkupTime = checkupTime;
    }

    /**
     * Executes the command and schedules for a checkup for the patient
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult containing feedback for the user.
     * @throws CommandException If the command execution fails due to invalid data or state.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person patient = getPatientFromModel(model);

        if (hasConflictingCheckup(patient) && isAdding) {
            throw new CommandException(MESSAGE_CHECKUP_DUPLICATE);
        }

        Checkup checkupClash = isWithinThirtyMinutes(patient, this.checkupDate, this.checkupTime);
        if (checkupClash != null && isAdding) {
            throw new CommandException(String.format(MESSAGE_CHECKUP_CLASH, checkupClash.toString()));
        }

        try {
            Checkup newCheckup = createCheckup();
            updatePatientWithCheckup(model, patient, newCheckup);
            return generateSuccessMessage(patient);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Retrieves the patient from the model based on the given patient index.
     *
     * @param model The model containing data and interactions for the application.
     * @return The patient corresponding to the patient index.
     * @throws CommandException If the patient index is invalid or the patient is not a valid patient.
     */
    public Person getPatientFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person patient = lastShownList.get(patientIndex.getZeroBased());

        if (!patient.getAppointment().toString().equals("Patient")) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATIENT, patientIndex.getOneBased()));
        }
        return patient;
    }

    /**
     * Creates a new Checkup object using the specified date and time.
     *
     * @return A new Checkup instance.
     * @throws ParseException If the checkup cannot be created due to invalid data.
     */
    public Checkup createCheckup() throws ParseException {
        return new Checkup(checkupDate, checkupTime, isAdding);
    }

    /**
     * Checks if the patient already has a conflicting checkup scheduled at the same date and time.
     *
     * @param patient The patient to check against.
     * @return True if a conflicting checkup exists; False otherwise.
     */
    private boolean hasConflictingCheckup(Person patient) {
        LocalDateTime newDateTime = LocalDateTime.of(checkupDate, checkupTime);
        return patient.getCheckups().stream()
                .anyMatch(existingCheckup -> existingCheckup.getDateTime().equals(newDateTime));
    }

    /**
     * Checks if the given time falls within +-30 minutes of any checkup's LocalDateTime.
     * This method compares the provided `date` and `time` (which are combined into a `LocalDateTime`)
     * against the `LocalDateTime` of each checkup in the patient's set of checkups. It returns `true`
     * if any checkup's `LocalDateTime` is within ±30 minutes of the provided `timeToCheck`, and `false` otherwise.
     *
     * @param patient The patient whose checkups are being checked.
     * @param date The date of the time to check.
     * @param time The time to check against the patient's checkups.
     * @return true if the given time falls within ±30 minutes of any checkup's LocalDateTime,
     *         false otherwise.
     */
    public Checkup isWithinThirtyMinutes(Person patient, LocalDate date, LocalTime time) {
        Set<Checkup> checkups = patient.getCheckups();

        LocalDateTime timeToCheck = date.atTime(time);

        for (Checkup checkup : checkups) {
            LocalDateTime checkupTime = checkup.getDateTime();
            Duration duration = Duration.between(checkupTime, timeToCheck);
            long seconds = duration.getSeconds();

            if (Math.abs(seconds) < 1800) {
                return checkup;
            }
        }
        return null;
    }

    /**
     * Updates the patient with the newly created checkup.
     *
     * @param model The model containing data and operations for the application.
     * @param patient The patient to update with the new checkup.
     * @param newCheckup The newly created checkup instance.
     */
    private void updatePatientWithCheckup(Model model, Person patient, Checkup newCheckup) throws CommandException {
        Set<Checkup> patientCheckups = new HashSet<>(patient.getCheckups());
        if (isAdding) {
            patientCheckups.add(newCheckup);
        } else {
            hasTargetCheckup(patientCheckups, newCheckup);
            patientCheckups.remove(newCheckup);
        }

        Person updatedPatient = new Person(
                patient.getName(), patient.getDateOfBirth(), patient.getPhone(), patient.getEmail(),
                patient.getAddress(), patient.getBloodType(), patient.getAppointment(), patient.getTags(),
                patient.getNextOfKin(), patient.getMedicalHistory(), patientCheckups);
        model.setPerson(patient, updatedPatient);
    }

    private void hasTargetCheckup(Set<Checkup> patientCheckups, Checkup newCheckup) throws CommandException {
        if (!patientCheckups.contains(newCheckup)) {
            throw new CommandException(MESSAGE_CHECKUP_DOES_NOT_EXIST);
        }
    }

    /**
     * Generates a success message after the checkup is scheduled.
     *
     * @param patient The patient for whom the checkup was scheduled.
     * @return A CommandResult containing the success message.
     */
    public CommandResult generateSuccessMessage(Person patient) {
        String dateStr = formatDate(checkupDate);
        String timeStr = formatTime(checkupTime);

        String baseMessage = isAdding ? generateCheckupCreatedMessage(patient, dateStr, timeStr)
                : generateCheckupDeletedMessage(patient, dateStr, timeStr);

        return new CommandResult(baseMessage);
    }

    /**
     * Generates a success message after a checkup is created.
     *
     * @param patient The patient for whom the checkup was created.
     * @param dateStr The date of the checkup in "dd/MM/yyyy" format.
     * @param timeStr The time of the checkup in "HH:mm" format.
     * @return A formatted success message indicating the checkup creation status.
     */
    public String generateCheckupCreatedMessage(Person patient, String dateStr, String timeStr) {
        boolean hasNurseAssigned = checkNurseAssignment(patient);

        String message = String.format(MESSAGE_CHECKUP_CREATED, patient.getName(), dateStr, timeStr);

        if (!hasNurseAssigned) {
            message += "\n" + MISSING_ASSIGNED_NURSE;
        }

        return message;
    }

    /**
     * Generates a success message after a checkup is deleted.
     *
     * @param patient The patient for whom the checkup was deleted.
     * @param dateStr The date of the checkup in "dd/MM/yyyy" format.
     * @param timeStr The time of the checkup in "HH:mm" format.
     * @return A formatted success message indicating the deletion status of the checkup.
     */
    public String generateCheckupDeletedMessage(Person patient, String dateStr, String timeStr) {
        return String.format(MESSAGE_CHECKUP_DELETED, patient.getName(), dateStr, timeStr);
    }

    /**
     * Formats a given LocalDate object into a string representation.
     *
     * @param date The LocalDate object to format.
     * @return A string representation of the date in "dd/MM/yyyy" format.
     */
    public String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(dateFormatter);
    }

    /**
     * Formats a given LocalTime object into a string representation.
     *
     * @param time The LocalTime object to format.
     * @return A string representation of the time in "HH:mm" format.
     */
    public String formatTime(LocalTime time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(timeFormatter);
    }

    /**
     * Checks if a nurse is assigned to the specified patient based on their tags.
     *
     * @param patient The patient whose tags will be checked for nurse assignments.
     * @return True if the patient has at least one nurse assigned; false otherwise.
     */
    public boolean checkNurseAssignment(Person patient) {
        List<String> nurseList = patient.getTags().stream()
                .filter(tag -> tag.tagName.startsWith("Nurse"))
                .map(tag -> tag.tagName.substring(5))
                .collect(Collectors.toList());
        return !nurseList.isEmpty();
    }
}
