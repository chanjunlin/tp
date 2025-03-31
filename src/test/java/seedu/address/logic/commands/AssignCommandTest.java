package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

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

public class AssignCommandTest {

    @Test
    public void execute_assignNurseToPatient_successful() throws CommandException {
        Model model = new ModelManager();
        Person patient = new PersonBuilder().withName("Alice Doe").withAppointment("Patient").build();
        Person nurse = new PersonBuilder().withName("John Lee").withAppointment("Nurse").build();

        model.addPerson(patient);
        model.addPerson(nurse);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CommandResult result = assignCommand.execute(model);

        Person updatedPatient = model.getFilteredPersonList().get(0);
        Set<seedu.address.model.tag.Tag> expectedTags = new HashSet<>(patient.getTags());
        expectedTags.add(new Tag("Nurse JOHN LEE"));

        assertEquals(expectedTags, updatedPatient.getTags());
        assertEquals(String.format(AssignCommand.MESSAGE_SUCCESS, nurse.getName(), patient.getName()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_assignPatientToPatient_throwsCommandException() {
        Model model = new ModelManager();
        Person patient1 = new PersonBuilder().withName("John Doe").withAppointment("Patient").build();
        Person patient2 = new PersonBuilder().withName("James Doe").withAppointment("Patient").build();

        model.addPerson(patient1);
        model.addPerson(patient2);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(String.format(AssignCommand.MESSAGE_INVALID_NURSE, INDEX_SECOND_PERSON.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_assignNurseToNurse_throwsCommandException() {
        Model model = new ModelManager();
        Person nurse1 = new PersonBuilder().withName("John Doe").withAppointment("Nurse").build();
        Person nurse2 = new PersonBuilder().withName("James Doe").withAppointment("Nurse").build();

        model.addPerson(nurse1);
        model.addPerson(nurse2);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(String.format(AssignCommand.MESSAGE_INVALID_PATIENT, INDEX_FIRST_PERSON.getOneBased()),
                exception.getMessage());
    }

    @Test
    public void execute_assignNurseBeyondMax_throwsCommandException() {
        Model model = new ModelManager();
        Person nurse = new PersonBuilder().withName("John Doe").withAppointment("Nurse").build();
        Person patient = new PersonBuilder().withName("Alice Lee").withAppointment("Patient")
                .withTags("NurseA", "NurseB")
                .build();

        model.addPerson(nurse);
        model.addPerson(patient);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals("This patient already has 2 assigned nurses!", exception.getMessage());
    }

    @Test
    public void execute_assignIndexOutOfBounds_throwsCommandException() {
        Model model = new ModelManager();

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }
}
