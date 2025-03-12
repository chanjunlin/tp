package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Changes the remark of an existing person in the address book.
 */
public class FindNurseCommand extends FindCommand {

    public static final String COMMAND_WORD = "find nurse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients assigned to the nurse at "
            + "the specified index and displays them as a list with index numbers.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_PATIENT = "The person at index %d is not a patient.";
    public static final String MESSAGE_INVALID_NURSE = "The person at index %d is not a nurse.";

    public static final String MESSAGE_NO_NURSE_ASSIGNED = "No nurse assigned to the patient at index %d.";
    public static final String MESSAGE_NURSE_FOUND = "Nurse assigned to patient %s is %s.";

    private final Index patientIndex;

    /**
     * Test
     *
     * @param patientIndex Test
     */
    public FindNurseCommand(Index patientIndex) {
        super(new NameContainsKeywordsPredicate(Arrays.asList("nurse")));
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

        String nurseName = null;

        for (Tag tag : updatedTags) {
            if (tag.tagName.startsWith("Nurse")) {
                nurseName = tag.tagName.substring(5);
                break;
            }
        }

        if (nurseName != null) {
            return new CommandResult(String.format(MESSAGE_NURSE_FOUND, patient.getName(), nurseName));
        } else {
            throw new CommandException(String.format(MESSAGE_NO_NURSE_ASSIGNED, patientIndex.getOneBased()));
        }
    }
}
