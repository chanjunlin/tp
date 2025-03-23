package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.time.LocalDate;
import java.time.LocalTime;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void validPatientIndex_nonConflicting() {

        ScheduleCommand command = new ScheduleCommand(Index.fromOneBased(1),
                LocalDate.of(2025, 3, 24),
                LocalTime.of(10, 0));

    }
}
