package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NextOfKinTest {

    @Test
    public void constructor_validInput_success() {
        NextOfKin nok = new NextOfKin("Jane 91234567");
        assertEquals("Jane 91234567", nok.value);
    }

    @Test
    public void constructor_emptyInput_setsDefaultValue() {
        NextOfKin nok1 = new NextOfKin("");
        assertEquals("Next of Kin not provided", nok1.value);

        NextOfKin nok2 = new NextOfKin("   ");
        assertEquals("Next of Kin not provided", nok2.value);

        NextOfKin nok3 = new NextOfKin(null);
        assertEquals("Next of Kin not provided", nok3.value);
    }

    @Test
    public void isValidNextOfKin_validFormat_returnsTrue() {
        assertTrue(NextOfKin.isValidNextOfKin("Jane 91234567"));
        assertTrue(NextOfKin.isValidNextOfKin("JohnDoe 98765432"));
    }

    @Test
    public void isValidNextOfKin_invalidFormat_returnsFalse() {
        assertFalse(NextOfKin.isValidNextOfKin("Jane")); // missing phone
        assertFalse(NextOfKin.isValidNextOfKin("91234567")); // missing name
        assertFalse(NextOfKin.isValidNextOfKin("")); // empty
        assertFalse(NextOfKin.isValidNextOfKin("Jane abcdefg")); // invalid phone
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        NextOfKin nok1 = new NextOfKin("Jane 91234567");
        NextOfKin nok2 = new NextOfKin("Jane 91234567");
        assertEquals(nok1, nok2);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        NextOfKin nok1 = new NextOfKin("Jane 91234567");
        NextOfKin nok2 = new NextOfKin("John 91234567");
        assertNotEquals(nok1, nok2);
    }

    @Test
    public void constructor_whitespaceInInput_trimsAndNormalizes() {
        NextOfKin nok = new NextOfKin("  Jane   91234567  ");
        assertEquals("Jane 91234567", nok.value);
    }

    @Test
    public void isValidNextOfKin_edgeCases() {
        assertFalse(NextOfKin.isValidNextOfKin("Jane91234567")); // No space
        assertFalse(NextOfKin.isValidNextOfKin("Jane 91")); // Invalid phone
        assertFalse(NextOfKin.isValidNextOfKin("12345 91234567")); // Invalid name
    }

    @Test
    public void toString_validInput_correctFormat() {
        NextOfKin nok = new NextOfKin("Jane 91234567");
        assertEquals("Jane 91234567", nok.toString());
    }
}
