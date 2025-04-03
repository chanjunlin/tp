package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.PersonHasAppointmentPredicate;
import seedu.address.model.person.PersonHasCheckupPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    /**
     * Tests filtering the list by appointment = Nurse.
     */
    @Test
    public void execute_filterByNurse_showsOnlyNurses() {
        Appointment nurseAppointment = new Appointment("Nurse");
        ListCommand command = new ListCommand(nurseAppointment);
        expectedModel.updateFilteredPersonList(new PersonHasAppointmentPredicate(nurseAppointment));

        assertCommandSuccess(command, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, nurseAppointment), expectedModel);
    }

    /**
     * Tests filtering the list by appointment = Patient.
     */
    @Test
    public void execute_filterByPatient_showsOnlyPatients() {
        Appointment patientAppointment = new Appointment("Patient");
        ListCommand command = new ListCommand(patientAppointment);
        expectedModel.updateFilteredPersonList(new PersonHasAppointmentPredicate(patientAppointment));

        assertCommandSuccess(command, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, patientAppointment), expectedModel);
    }

    /**
     * Tests filtering the list by checkup presence.
     */
    @Test
    public void execute_filterByCheckup_showsOnlyWithCheckup() {
        ListCommand command = new ListCommand(true);
        expectedModel.updateFilteredPersonListByEarliestCheckup(new PersonHasCheckupPredicate());

        assertCommandSuccess(command, model,
                ListCommand.MESSAGE_SUCCESS_CHECKUP, expectedModel);
    }
}
