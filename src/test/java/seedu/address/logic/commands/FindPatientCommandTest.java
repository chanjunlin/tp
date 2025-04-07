package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_INVALID_NURSE;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_NO_PATIENT_ASSIGNED;
import static seedu.address.logic.commands.FindPatientCommand.MESSAGE_PATIENT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */

public class FindPatientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws CommandException {
        Model model1 = new ModelManager();
        Person nurse = new PersonBuilder().withName("john lee").withAppointment("Nurse").build();
        Person patient = new PersonBuilder().withName("alice doe").withAppointment("Patient")
                .withTags("Nurse JOHN LEE").build();

        model1.addPerson(nurse);
        model1.addPerson(patient);

        Index validNurseIndex = Index.fromZeroBased(1);
        FindPatientCommand command = new FindPatientCommand(validNurseIndex);

        CommandResult result = command.execute(model1);
        assertEquals(String.format(MESSAGE_PATIENT_FOUND, "JOHN LEE", "ALICE DOE"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndexNoPatientAssigned_throwsCommandException() {
        Index validNurseIndex = Index.fromZeroBased(6);
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
        String msg =  String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 7);

        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void execute_withoutFilter() throws CommandException {
        Model model1 = new ModelManager();
        Person nurse = new PersonBuilder().withName("john lee").withAppointment("Nurse").build();
        Person patient = new PersonBuilder().withName("alice doe").withAppointment("Patient")
                .withTags("Nurse JOHN LEE").build();

        model1.addPerson(nurse);
        model1.addPerson(patient);

        Index validNurseIndex = Index.fromZeroBased(1);
        FindPatientCommand findCommand = new FindPatientCommand(validNurseIndex);

        Command listCommand = new ListCommand(null);
        listCommand.execute(model1);
        CommandResult result1 = findCommand.execute(model1);
        assertEquals(String.format(MESSAGE_PATIENT_FOUND, "JOHN LEE", "ALICE DOE"),
                result1.getFeedbackToUser());
    }

    @Test
    public void execute_withFilter() throws CommandException {
        Model model1 = new ModelManager();
        Person nurse = new PersonBuilder().withName("john lee").withAppointment("Nurse").build();
        Person patient = new PersonBuilder().withName("alice doe").withAppointment("Patient")
                .withTags("Nurse JOHN LEE").build();

        model1.addPerson(nurse);
        model1.addPerson(patient);

        Index validNurseIndex = Index.fromZeroBased(0);
        FindPatientCommand findCommand = new FindPatientCommand(validNurseIndex);

        Command listNurseCommand = new ListCommand(new Appointment("nurse"));
        listNurseCommand.execute(model1);
        CommandResult result2 = findCommand.execute(model1);
        assertEquals(String.format(MESSAGE_PATIENT_FOUND, "JOHN LEE", "ALICE DOE"),
                result2.getFeedbackToUser());
    }
}
