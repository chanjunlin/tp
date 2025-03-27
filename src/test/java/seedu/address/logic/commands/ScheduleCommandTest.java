package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.FindNurseCommand.MESSAGE_INVALID_PATIENT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_CHECKUP_CREATED;
import static seedu.address.model.checkup.Checkup.MESSAGE_PAST_DATE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person patient;
    private ScheduleCommand command;
    private Index invalidIndex;
    private Index invalidPatientIndex;
    private Index patientIndex;
    private LocalDate checkupDate;
    private LocalDate pastDate;
    private LocalTime checkupTime;
    private DateTimeFormatter dateFormatter;
    private Boolean addCheckup;
    private Boolean deleteCheckup;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        patient = model.getFilteredPersonList().get(0);

        invalidIndex = Index.fromZeroBased(1000000);
        invalidPatientIndex = Index.fromZeroBased(1);
        patientIndex = Index.fromZeroBased(0);
        checkupDate = LocalDate.of(2025, 12, 24);
        pastDate = LocalDate.of(2025, 1, 1);
        checkupTime = LocalTime.of(10, 0);
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        addCheckup = true;
        deleteCheckup = false;
        ;
        command = new ScheduleCommand(addCheckup, patientIndex, checkupDate, checkupTime);
    }

    @Test
    public void validPatientIndex_nonConflictingCheckup() {
        Person patient = model.getFilteredPersonList().get(0);
        try {
            CommandResult result = command.execute(model);
            assertEquals(String.format(MESSAGE_CHECKUP_CREATED, patient.getName(),
                            checkupDate.format(dateFormatter),
                            checkupTime),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void validPatientIndex_conflictingCheckup() {
        try {
            CommandResult result = command.execute(model);
            assertEquals(String.format(MESSAGE_CHECKUP_CREATED, patient.getName(),
                            checkupDate.format(dateFormatter),
                            checkupTime),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }

        CommandException exception = assertThrows(CommandException.class, () -> {
            command.execute(model);
        });
        assertEquals("A checkup is already scheduled at this datetime.", exception.getMessage());
    }

    @Test
    public void validPatientIndex_pastDateTime() {
        Command pastDateCommand = new ScheduleCommand(addCheckup, patientIndex, pastDate, checkupTime);
        CommandException exception = assertThrows(CommandException.class, () -> {
            pastDateCommand.execute(model);
        });
        assertEquals(MESSAGE_PAST_DATE, exception.getMessage());

    }

    @Test
    public void invalidPatientIndex() {
        Command invalidPatientCommand = new ScheduleCommand(addCheckup, invalidPatientIndex, checkupDate, checkupTime);
        CommandException exception = assertThrows(CommandException.class, () -> {
            invalidPatientCommand.execute(model);
        });
        assertEquals(String.format(MESSAGE_INVALID_PATIENT, invalidPatientIndex.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void invalidIndex() {
        Command invalidIndexCommand = new ScheduleCommand(addCheckup, invalidIndex, checkupDate, checkupTime);
        CommandException exception = assertThrows(CommandException.class, () -> {
            invalidIndexCommand.execute(model);
        });
        assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                exception.getMessage());
    }
}
