package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_INVALID_PATIENT;
import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_NO_NURSE_ASSIGNED;
import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_NURSE_FOUND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */

public class FindNurseCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Index validPatientIndex = Index.fromZeroBased(0);
        FindNurseCommand command = new FindNurseCommand(validPatientIndex);
        try {
            CommandResult result = command.execute(model);
            assertEquals(String.format(MESSAGE_NURSE_FOUND, "ALICE PAULINE", "BensonMeier"),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void execute_validIndexNoNurseAssigned_throwsCommandException() {
        Index validPatientIndex = Index.fromZeroBased(2);
        FindNurseCommand command = new FindNurseCommand(validPatientIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals(String.format(MESSAGE_NO_NURSE_ASSIGNED, validPatientIndex.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_invalidIndexNotPatient_throwsCommandException() {
        Index invalidPatientIndex = Index.fromZeroBased(1);
        FindNurseCommand command = new FindNurseCommand(invalidPatientIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals(String.format(MESSAGE_INVALID_PATIENT, invalidPatientIndex.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_invalidIndexIndexOutOfBounds_throwsCommandException() {
        Index invalidPatientIndex = Index.fromZeroBased(100000);
        FindNurseCommand command = new FindNurseCommand(invalidPatientIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals("The person index provided is invalid", exception.getMessage());
    }
}
