package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



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
        BloodType big_caps_type = new BloodType("AB+");
        BloodType small_caps_type = new BloodType("ab+");

        // same values -> returns true
        assertTrue(big_caps_type.equals(new BloodType("AB+")));
        assertTrue(big_caps_type.equals(small_caps_type));

        // same object -> returns true
        assertTrue(big_caps_type.equals(big_caps_type));
        assertTrue(small_caps_type.equals(small_caps_type));

        // null -> returns false
        assertFalse(big_caps_type.equals(null));
        assertFalse(small_caps_type.equals(null));

        // different types -> returns false
        assertFalse(big_caps_type.equals(5.0f));
        assertFalse(small_caps_type.equals(5.0f));

        // different values -> returns false
        assertFalse(big_caps_type.equals(new BloodType("A-")));
        assertFalse(small_caps_type.equals(new BloodType("b-")));

    }
}
