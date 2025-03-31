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
 * Removes assigned nurse from a patient.
 */
public class AssignDeleteCommand extends Command {

    public static final String COMMAND_WORD = "assign delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes assigned nurse from a patient.\n"
            + "Parameters: NURSE_NAME PATIENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " JOHN DOE 2";

    public static final String MESSAGE_SUCCESS = "Removed assigned nurse %s from patient %s.";

    public static final String MESSAGE_INVALID_PATIENT = "The person at index %d is not a patient.";

    public static final String MESSAGE_INVALID_NURSE = "Nurse %s is not assigned to patient %s.";

    private final String nurseName;

    private final Index patientIndex;

    /**
     * Creates an AssignDeleteCommand to remove assigned nurse from a patient.
     *
     * @param nurseName
     * @param patientIndex
     */
    public AssignDeleteCommand(String nurseName, Index patientIndex) {
        this.nurseName = nurseName;
        this.patientIndex = patientIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person patient = lastShownList.get(patientIndex.getZeroBased());

        if (!patient.getAppointment().toString().equals("Patient")) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATIENT, patientIndex.getOneBased()));
        }

        Set<Tag> updatedTags = new HashSet<>(patient.getTags());
        String nurseTag = "Nurse " + nurseName.toUpperCase();

        if (!updatedTags.remove(new Tag(nurseTag))) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_NURSE, nurseName.toUpperCase(), patient.getName()));
        }

        Person updatedPatient = new Person(patient.getName(), patient.getDateOfBirth(), patient.getPhone(),
                patient.getEmail(), patient.getAddress(), patient.getBloodType(), patient.getAppointment(),
                updatedTags, patient.getNextOfKin(), patient.getMedicalHistory(), patient.getCheckups());

        model.setPerson(patient, updatedPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nurseName.toUpperCase(), patient.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignDeleteCommand)) {
            return false;
        }

        AssignDeleteCommand otherAssignDeleteCommand = (AssignDeleteCommand) other;

        if (!nurseName.equals(otherAssignDeleteCommand.nurseName)) {
            return false;
        }

        if (!patientIndex.equals(otherAssignDeleteCommand.patientIndex)) {
            return false;
        }

        return true;
    }
}
