package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Assigns a nurse to a patient.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a nurse to a patient.\n"
            + "Parameters: PATIENT_INDEX NURSE_INDEX\n"
            + "Example: " + COMMAND_WORD + " 2 1";

    public static final String MESSAGE_SUCCESS = "Assigned nurse %s to patient %s.";

    public static final String MESSAGE_INVALID_PATIENT = "The person at index %d is not a patient.";

    public static final String MESSAGE_INVALID_NURSE = "The person at index %d is not a nurse.";

    public static final String ROLE_PATIENT = "Patient";

    public static final String ROLE_NURSE = "Nurse";

    private static final int MAX_NURSES_PER_PATIENT = 2;

    private final Index patientIndex;

    private final Index nurseIndex;

    /**
     * Creates an AssignCommand to assign a nurse to a patient.
     *
     * @param patientIndex
     * @param nurseIndex
     */
    public AssignCommand(Index patientIndex, Index nurseIndex) {
        this.patientIndex = patientIndex;
        this.nurseIndex = nurseIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person patient = getPersonFromIndex(lastShownList, patientIndex);
        Person nurse = getPersonFromIndex(lastShownList, nurseIndex);

        validateAppointmentType(patient, ROLE_PATIENT, patientIndex, MESSAGE_INVALID_PATIENT);
        validateAppointmentType(nurse, ROLE_NURSE, nurseIndex, MESSAGE_INVALID_NURSE);

        long nurseCount = patient.getTags().stream().filter(tag -> tag.tagName.startsWith("Nurse")).count();

        if (nurseCount >= MAX_NURSES_PER_PATIENT) {
            throw new CommandException("This patient already has " + MAX_NURSES_PER_PATIENT + " assigned nurses!");
        }

        Set<Tag> updatedTags = new HashSet<>(patient.getTags());
        updatedTags.add(new Tag("Nurse " + nurse.getName().fullName));

        Person updatedPatient = new Person(
                patient.getName(), patient.getDateOfBirth(), patient.getPhone(), patient.getEmail(),
                patient.getAddress(), patient.getBloodType(), patient.getAppointment(), updatedTags,
                patient.getNextOfKin(), patient.getMedicalHistory(), patient.getCheckups());
        model.setPerson(patient, updatedPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nurse.getName(), patient.getName()));
    }

    /**
     * Returns the person at the specified index from the list or throws if index is out of bounds.
     */
    private Person getPersonFromIndex(List<Person> list, Index index) throws CommandException {
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return list.get(index.getZeroBased());
    }

    /**
     * Validates that the given person has the expected appointment type.
     *
     * @param person the person to validate
     * @param expectedType the expected appointment string (e.g., "Patient", "Nurse")
     * @param index the index of the person (for error messaging)
     * @param errorMessageFormat the error message to throw if validation fails
     * @throws CommandException if the appointment type does not match
     */
    private void validateAppointmentType(Person person, String expectedType, Index index, String errorMessageFormat)
            throws CommandException {
        if (!person.getAppointment().toString().equalsIgnoreCase(expectedType)) {
            throw new CommandException(String.format(errorMessageFormat, index.getOneBased()));
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;

        if (!patientIndex.equals(otherAssignCommand.patientIndex)) {
            return false;
        }

        if (!nurseIndex.equals(otherAssignCommand.nurseIndex)) {
            return false;
        }

        return true;
    }
}
