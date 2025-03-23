package seedu.address.logic.commands;

import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_INVALID_NURSE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
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
            + "Parameters: PATIENT_INDEX NURSE_INDEX DATE TIME\n"
            + "Example: " + COMMAND_WORD + " 1 2 01/01/2025 1400";

    public static final String MESSAGE_CHECKUP_CREATED = "Appointment for Patient %s with Nurse %s has been "
            + "successfully created on %s at %s";
    private final Index patientIndex;
    private final Index nurseIndex;
    private final LocalDate checkupDate;
    private final LocalTime checkupTime;


    /**
     * test
     * @param patientIndex test
     * @param nurseIndex test
     * @param checkupDate test
     * @param checkupTime test
     */
    public ScheduleCommand(Index patientIndex, Index nurseIndex, LocalDate checkupDate,
                           LocalTime checkupTime) {
        this.patientIndex = patientIndex;
        this.nurseIndex = nurseIndex;
        this.checkupDate = checkupDate;
        this.checkupTime = checkupTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person patient = getPatientFromModel(model);
        Person nurse = getNurseFromModel(model);
        try {
            Set<Checkup> patientCheckups = new HashSet<>(patient.getCheckups());
            Set<Checkup> nurseCheckups = new HashSet<>(nurse.getCheckups());
            patientCheckups.add(new Checkup(patient, nurse, this.checkupDate, this.checkupTime));
            nurseCheckups.add(new Checkup(patient, nurse, this.checkupDate, this.checkupTime));
            System.out.println(nurse.toString());
            Person updatedPatient = new Person(
                    patient.getName(), patient.getPhone(), patient.getEmail(), patient.getAddress(),
                    patient.getBloodType(), patient.getAppointment(), patient.getTags(), patientCheckups);
            Person updatedNurse = new Person(
                    nurse.getName(), nurse.getPhone(), nurse.getEmail(), nurse.getAddress(), nurse.getBloodType(),
                    nurse.getAppointment(), nurse.getTags(), nurseCheckups);

            System.out.println(updatedNurse.getCheckups() + "====" + nurse.getCheckups());

            model.setPerson(patient, updatedPatient);
            model.setPerson(nurse, updatedNurse);

            Person newNurse = getNurseFromModel(model);
            System.out.println(newNurse);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String dateStr = this.checkupDate.format(dateFormatter);
            String timeStr = this.checkupTime.format(timeFormatter);

            return new CommandResult(String.format(MESSAGE_CHECKUP_CREATED,
                    patient.getName(), nurse.getName(), dateStr, timeStr));
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
            throw new CommandException(String.format(MESSAGE_INVALID_NURSE, nurseIndex.getOneBased()));
        }
        return patient;
    }

    public Person getNurseFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (nurseIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person nurse = lastShownList.get(nurseIndex.getZeroBased());

        if (!nurse.getAppointment().toString().equals("Nurse")) {
            throw new CommandException(String.format(MESSAGE_INVALID_NURSE, nurseIndex.getOneBased()));
        }
        return nurse;
    }



}
