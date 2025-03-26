package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the nurse or patient at specified index. "
            + "If the person at the index is a patient and has medical history, the history will be shown.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Displaying details for: %s.";
    public static final String MESSAGE_MEDICAL_HISTORY = "Medical History for %s: %s";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());

        if (personToView.getAppointment().toString().equals("Patient")) {
            String medicalHistory = personToView.getMedicalHistory().isEmpty()
                    ? "No medical history available."
                    : personToView.getMedicalHistory().toString();
            //return new CommandResult(String.format(MESSAGE_SUCCESS, personToView.getName()), false, false, medicalHistory);
        }
    }
}
