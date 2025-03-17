package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_INVALID_NURSE;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_NO_PATIENT_ASSIGNED;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_PATIENT_FOUND;
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

public class FindPatientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Index validNurseIndex = Index.fromZeroBased(1);
        FindPatientCommand command = new FindPatientCommand(validNurseIndex);

        try {
            CommandResult result = command.execute(model);
            assertEquals(String.format(MESSAGE_PATIENT_FOUND, "Benson Meier", "Alice Pauline"),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void execute_validIndexNoPatientAssigned_throwsCommandException() {
        Index validNurseIndex = Index.fromZeroBased(8);
        FindPatientCommand command = new FindPatientCommand(validNurseIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals(String.format(MESSAGE_NO_PATIENT_ASSIGNED, validNurseIndex.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_invalidIndexNotNurse_throwsCommandException() {
        Index invalidNurseIndex = Index.fromZeroBased(0);
        FindPatientCommand command = new FindPatientCommand(invalidNurseIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals(String.format(MESSAGE_INVALID_NURSE, invalidNurseIndex.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_invalidIndexIndexOutOfBounds_throwsCommandException() {
        Index invalidPatientIndex = Index.fromZeroBased(100000);
        FindPatientCommand command = new FindPatientCommand(invalidPatientIndex);

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });

        assertEquals("The person index provided is invalid", exception.getMessage());
    }
}
