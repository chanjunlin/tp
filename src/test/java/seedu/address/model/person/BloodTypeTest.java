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
        BloodType bigCapsType = new BloodType("AB+");
        BloodType smallCapsType = new BloodType("ab+");

        // same values -> returns true
        assertTrue(bigCapsType.equals(new BloodType("AB+")));
        assertTrue(bigCapsType.equals(smallCapsType));

        // same object -> returns true
        assertTrue(bigCapsType.equals(bigCapsType));
        assertTrue(smallCapsType.equals(smallCapsType));

        // null -> returns false
        assertFalse(bigCapsType.equals(null));
        assertFalse(smallCapsType.equals(null));

        // different types -> returns false
        assertFalse(bigCapsType.equals(5.0f));
        assertFalse(smallCapsType.equals(5.0f));

        // different values -> returns false
        assertFalse(bigCapsType.equals(new BloodType("A-")));
        assertFalse(smallCapsType.equals(new BloodType("b-")));

    }
}
