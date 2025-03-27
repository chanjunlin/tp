package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewCommandTest {

    @Test
    public void execute_viewPatientWithMedicalHistory_successful() throws CommandException {
        Model model = new ModelManager();
        Person patient = new PersonBuilder().withName("John Doe").withAppointment("Patient")
                .withMedicalHistory("High blood pressure").build();

        model.addPerson(patient);

        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        CommandResult result = viewCommand.execute(model);

        String expected = String.format(ViewCommand.MESSAGE_SUCCESS, patient.getName()) + "\n\n"
                + String.format(ViewCommand.MESSAGE_MEDICAL_HISTORY, patient.getName(), "High blood pressure");

        assertEquals(expected, result.getFeedbackToUser());
    }
}
