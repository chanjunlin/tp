package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void isValidAppointment() {

        //Valid appointment strings
        Appointment appt = new Appointment("Nurse");
        assertEquals("Nurse", appt.appointment);
        Appointment appt2 = new Appointment("Patient");
        assertEquals("Patient", appt2.appointment);

        //null appointment
        assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        //Empty appointment
        assertFalse(Appointment.isValidAppointment(""));

        //Invalid Appointment strings
        assertFalse(Appointment.isValidAppointment(""));
        assertFalse(Appointment.isValidAppointment("Manager"));
        assertFalse(Appointment.isValidAppointment("Potato"));
        assertFalse(Appointment.isValidAppointment("nurs3"));
    }

    @Test
    public void equals() {
        Appointment appt1 = new Appointment("Nurse");
        Appointment appt2 = new Appointment("Patient");

        assertTrue(appt1.equals(new Appointment("Nurse")));
        assertTrue(appt1.equals(appt1));
        assertFalse(appt1.equals(null));
        assertFalse(appt1.equals(5));
        assertFalse(appt1.equals(appt2));
    }
}
