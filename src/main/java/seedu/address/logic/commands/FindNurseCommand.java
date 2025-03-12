package seedu.address.logic.commands;

import java.util.Arrays;

import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Changes the remark of an existing person in the address book.
 */
public class FindNurseCommand extends FindCommand {

    public static final String COMMAND_WORD = "find nurse";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients assigned to the nurse at "
            + "the specified index and displays them as a list with index numbers.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Find nurse command not implemented yet";

    private final int patientIndex;

    /**
     * Test
     * @param patientIndex Test
     */
    public FindNurseCommand(int patientIndex) {
        super(new NameContainsKeywordsPredicate(Arrays.asList("nurse")));
        this.patientIndex = patientIndex;
    }
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from findNurse");
    }
}
