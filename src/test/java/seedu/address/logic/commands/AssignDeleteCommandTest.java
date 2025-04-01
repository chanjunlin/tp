package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AssignDeleteCommandTest {
    @Test
    public void execute_removeAssignedNurse_successful() throws CommandException {
        Model model = new ModelManager();
        Person patient = new PersonBuilder().withName("Alice Doe").withAppointment("Patient")
                .withTags("Nurse JOHN LEE").build();
        model.addPerson(patient);

        AssignDeleteCommand assignDeleteCommand = new AssignDeleteCommand("john lee", INDEX_FIRST_PERSON);
        CommandResult result = assignDeleteCommand.execute(model);

        Person updatedPatient = model.getFilteredPersonList().get(0);

        Set<Tag> expectedTags = new HashSet<>();

        assertEquals(expectedTags, updatedPatient.getTags());
        assertEquals(String.format(AssignDeleteCommand.MESSAGE_SUCCESS, "JOHN LEE", patient.getName()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_removeNonExistentNurse_throwsCommandException() {
        Model model = new ModelManager();
        Person patient = new PersonBuilder().withName("Alice Doe").withAppointment("Patient").build();
        model.addPerson(patient);

        AssignDeleteCommand assignDeleteCommand = new AssignDeleteCommand("john lee", INDEX_FIRST_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignDeleteCommand.execute(model));
        assertEquals(String.format(AssignDeleteCommand.MESSAGE_INVALID_NURSE, "JOHN LEE", patient.getName()),
                exception.getMessage());
    }

    @Test
    public void execute_removeNurseFromNonPatient_throwsCommandException() {
        Model model = new ModelManager();
        Person nurse = new PersonBuilder().withName("john lee").withAppointment("Nurse").build();
        model.addPerson(nurse);

        AssignDeleteCommand assignDeleteCommand = new AssignDeleteCommand("john lee", INDEX_FIRST_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignDeleteCommand.execute(model));
        assertEquals(String.format(AssignDeleteCommand.MESSAGE_INVALID_PATIENT, INDEX_FIRST_PERSON.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_removeNurseFromInvalidPatientIndex_throwsCommandException() {
        Model model = new ModelManager();

        AssignDeleteCommand assignDeleteCommand = new AssignDeleteCommand("john lee", INDEX_FIRST_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignDeleteCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }
}
