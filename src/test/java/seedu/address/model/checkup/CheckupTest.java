package seedu.address.model.checkup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class CheckupTest {
    @Test
    public void constructor_validInputs_createsCheckup() {
        LocalDate checkupDate = LocalDate.of(2025, 12, 24);
        LocalTime checkupTime = LocalTime.of(10, 0);
        try {
            Checkup checkup = new Checkup(checkupDate, checkupTime, true);
            assertNotNull(checkup);
        } catch (Exception e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void constructor_outsideBusinessHours_throwsParseException() {
        LocalDate checkupDate = LocalDate.of(2025, 12, 24);
        LocalTime checkupTime1 = LocalTime.of(8, 0);
        LocalTime checkupTime2 = LocalTime.of(21, 0);
        assertThrows(ParseException.class, () -> new Checkup(checkupDate, checkupTime1, true));
        assertThrows(ParseException.class, () -> new Checkup(checkupDate, checkupTime2, true));
    }

    @Test
    public void constructor_pastDate_throwsParseException() {
        LocalDate checkupDate = LocalDate.of(2020, 1, 1);
        LocalTime checkupTime = LocalTime.of(10, 0);
        assertThrows(ParseException.class, () -> new Checkup(checkupDate, checkupTime, true));
    }

    @Test
    public void constructor_isNotFifteen_throwsParseException() {
        LocalDate checkupDate = LocalDate.of(2020, 1, 1);
        LocalTime checkupTime = LocalTime.of(10, 11);
        assertThrows(ParseException.class, () -> new Checkup(checkupDate, checkupTime, true));
    }

    @Test
    public void equals_sameCheckup_returnsTrue() throws ParseException {
        Checkup checkup1 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(10, 0), true);
        Checkup checkup2 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(10, 0), true);
        assertEquals(checkup1, checkup2);
        assertEquals(checkup1, checkup1);
    }

    @Test
    public void equals_differentCheckup_returnsFalse() throws ParseException {
        Checkup checkup1 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(10, 0), true);
        Checkup checkup2 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(11, 0), true);
        assertNotEquals(checkup1, checkup2);
    }

    @Test
    public void hashCode_sameCheckup_returnsSameHash() throws ParseException {
        Checkup checkup1 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(10, 0), true);
        Checkup checkup2 = new Checkup(LocalDate.of(2025, 12, 24),
                LocalTime.of(10, 0), true);
        assertEquals(checkup1.hashCode(), checkup2.hashCode());
    }

    @Test
    public void getCheckupDate_returnsCorrectDate() throws ParseException {
        LocalDate checkupDate = LocalDate.of(2025, 12, 24);
        LocalTime checkupTime = LocalTime.of(10, 0);
        Checkup checkup = new Checkup(checkupDate, checkupTime, true);
        assertEquals(checkupDate, checkup.getCheckupDate());
    }

    @Test
    public void getCheckupTime_returnsCorrectTime() throws ParseException {
        LocalDate checkupDate = LocalDate.of(2025, 12, 24);
        LocalTime checkupTime = LocalTime.of(10, 0);
        Checkup checkup = new Checkup(checkupDate, checkupTime, true);
        assertEquals(checkupTime, checkup.getCheckupTime());
    }

    @Test
    public void toString_returnsFormattedString() throws ParseException {
        LocalDate checkupDate = LocalDate.of(2025, 12, 24);
        LocalTime checkupTime = LocalTime.of(10, 0);
        Checkup checkup = new Checkup(checkupDate, checkupTime, true);
        assertEquals("24/12/2025 10:00", checkup.toString());
    }
}
