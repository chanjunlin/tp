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
 * test
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an appointment for a patient with a nurse.\n"
            + "Parameters: PATIENT_INDEX DATE TIME\n"
            + "Example: " + COMMAND_WORD + " 1 01/01/2025 1400";

    public static final String MESSAGE_CHECKUP_CREATED = "Appointment for Patient %s has been "
            + "successfully created on %s at %s";
    private final Index patientIndex;
    private final LocalDate checkupDate;
    private final LocalTime checkupTime;


    /**
     * test
     * @param patientIndex test
     * @param checkupDate test
     * @param checkupTime test
     */
    public ScheduleCommand(Index patientIndex, LocalDate checkupDate,
                           LocalTime checkupTime) {
        this.patientIndex = patientIndex;
        this.checkupDate = checkupDate;
        this.checkupTime = checkupTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person patient = getPatientFromModel(model);

        if (hasConflictingCheckup(patient)) {
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

    public Checkup createCheckup() throws ParseException {
        return new Checkup(checkupDate, checkupTime);
    }

    private boolean hasConflictingCheckup(Person patient) {
        LocalDateTime newDateTime = LocalDateTime.of(checkupDate, checkupTime);
        return patient.getCheckups().stream()
                .anyMatch(existingCheckup -> existingCheckup.getDateTime().equals(newDateTime));
    }

    private void updatePatientWithCheckup(Model model, Person patient, Checkup newCheckup) {
        Set<Checkup> patientCheckups = new HashSet<>(patient.getCheckups());
        patientCheckups.add(newCheckup);
        Person updatedPatient = new Person(
                patient.getName(), patient.getPhone(), patient.getEmail(), patient.getAddress(),
                patient.getBloodType(), patient.getAppointment(), patient.getTags(), patientCheckups);
        model.setPerson(patient, updatedPatient);
    }

    private CommandResult generateSuccessMessage(Person patient) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String dateStr = this.checkupDate.format(dateFormatter);
        String timeStr = this.checkupTime.format(timeFormatter);

        return new CommandResult(String.format(MESSAGE_CHECKUP_CREATED,
                patient.getName(), dateStr, timeStr));
    }
}
