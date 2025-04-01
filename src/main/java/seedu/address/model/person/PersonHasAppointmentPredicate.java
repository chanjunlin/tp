package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s appointment matches the given appointment.
 */
public class PersonHasAppointmentPredicate implements Predicate<Person> {
    private final Appointment appointment;

    public PersonHasAppointmentPredicate(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public boolean test(Person person) {
        return person.getAppointment().equals(appointment);
    }
}
