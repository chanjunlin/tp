package seedu.address.logic.commands;

import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_INVALID_PATIENT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static final String ADD_SCHEDULE_COMMAND = "add";
    public static final String DELETE_SCHEDULE_COMMAND = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an appointment for a patient with a nurse.\n"
            + "Parameters: ACTION PATIENT_INDEX DATE TIME\n"
            + "Example: " + COMMAND_WORD + " add 1 01/01/2025 1400";

    public static final String MESSAGE_CHECKUP_CREATED = "Appointment for Patient %s has been "
            + "successfully created on %s at %s";

    public static final String MESSAGE_CHECKUP_DELETED = "Appointment for Patient %s has been "
            + "successfully deleted from %s at %s";
    public static final String MESSAGE_CHECKUP_DOES_NOT_EXIST = "Appointment does not exist";
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
            throw new CommandException("A checkup is already scheduled at this datetime.");
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
        return new Checkup(checkupDate, checkupTime);
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
                patient.getName(), patient.getPhone(), patient.getEmail(), patient.getAddress(),
                patient.getBloodType(), patient.getAppointment(), patient.getTags(), patient.getNextOfKin(),
                patient.getMedicalHistory(), patientCheckups);
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
    private CommandResult generateSuccessMessage(Person patient) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String dateStr = this.checkupDate.format(dateFormatter);
        String timeStr = this.checkupTime.format(timeFormatter);

        return new CommandResult(String.format((isAdding) ? MESSAGE_CHECKUP_CREATED : MESSAGE_CHECKUP_DELETED,
                patient.getName(), dateStr, timeStr));
    }
}
