package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MedicalHistoryTest {

    @Test
    public void constructor_validInput_success() {
        MedicalHistory mh = new MedicalHistory("Diabetes");
        assertEquals("Diabetes", mh.medicalHistory);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MedicalHistory(null));
    }

    @Test
    public void isValidMedicalHistory() {
        assertTrue(MedicalHistory.isValidMedicalHistory("Diabetes"));

        // null medical history.
        assertThrows(NullPointerException.class, () -> MedicalHistory.isValidMedicalHistory(null));

        // Empty medical history.
        assertFalse(MedicalHistory.isValidMedicalHistory(""));
        assertFalse(MedicalHistory.isValidMedicalHistory(" "));

        // Invalid medical history (i.e. illegal character).
        assertFalse(MedicalHistory.isValidMedicalHistory("Diabetes@@!"));
    }

    @Test
    public void equals() {
        MedicalHistory mh1 = new MedicalHistory("Diabetes");
        MedicalHistory mh2 = new MedicalHistory("Hypertension");

        assertEquals(new MedicalHistory("Diabetes"), mh1);
        assertEquals(mh1.toString(), new MedicalHistory("Diabetes").toString());
        assertEquals("[Diabetes]", mh1.toString());
        assertEquals(mh1, mh1);

        assertFalse(mh1.equals(null));
        assertFalse(mh1.equals(5));
        assertFalse(mh2.equals("Fever"));
        assertFalse(mh1.equals(mh2));
        assertFalse(mh1.equals(new MedicalHistory("Diabetes ")));
        assertFalse(mh1.equals(new MedicalHistory(" Diabetes")));
    }

    @Test
    public void hashCodeTest() {
        MedicalHistory mh1 = new MedicalHistory("Diabetes");
        MedicalHistory mh2 = new MedicalHistory("Hypertension");

        assertEquals(mh1.hashCode(), new MedicalHistory("Diabetes").hashCode());
        assertEquals(mh1.hashCode(), mh1.hashCode());

        assertNotEquals(mh1.hashCode(), mh2.hashCode());
    }
}
