package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class BloodTypeTest {

    @Test
    public void isValidBloodTypeTest() {
        //valid blood type
        BloodType bloodType = new BloodType("O+");
        assertEquals("O+", bloodType.toString());
        bloodType = new BloodType("A+");
        assertEquals("A+", bloodType.toString());
        bloodType = new BloodType("B+");
        assertEquals("B+", bloodType.toString());
        bloodType = new BloodType("AB+");
        assertEquals("AB+", bloodType.toString());
        bloodType = new BloodType("O-");
        assertEquals("O-", bloodType.toString());
        bloodType = new BloodType("A-");
        assertEquals("A-", bloodType.toString());
        bloodType = new BloodType("B-");
        assertEquals("B-", bloodType.toString());
        bloodType = new BloodType("AB-");
        assertEquals("AB-", bloodType.toString());

        // null name
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("^")); // only non-alphanumeric characters
        assertFalse(BloodType.isValidBloodType("peter*")); // contains non-alphanumeric characters
    }

    @Test
    public void equals() {
        BloodType type = new BloodType("AB+");

        // same values -> returns true
        assertTrue(type.equals(new BloodType("AB+")));

        // same object -> returns true
        assertTrue(type.equals(type));

        // null -> returns false
        assertFalse(type.equals(null));

        // different types -> returns false
        assertFalse(type.equals(5.0f));

        // different values -> returns false
        assertFalse(type.equals(new BloodType("A-")));
    }
}
