package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests for PersonHasAppointmentPredicate.
 */
public class PersonHasAppointmentPredicateTest {

    @Test
    public void test_validAppointment_returnsTrue() {
        PersonHasAppointmentPredicate predicate = new PersonHasAppointmentPredicate(new Appointment("Nurse"));
        assertTrue(predicate.test(new PersonBuilder().withAppointment("Nurse").build()));
    }

    @Test
    public void test_invalidAppointment_returnsFalse() {
        PersonHasAppointmentPredicate predicate = new PersonHasAppointmentPredicate(new Appointment("Nurse"));
        assertFalse(predicate.test(new PersonBuilder().withAppointment("Patient").build()));
    }
}
