package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;


public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        assertEquals(BOB.hashCode(), BOB.hashCode());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different blood type -> returns false
        editedAlice = new PersonBuilder(ALICE).withBloodType("O-").build();
        assertFalse(ALICE.equals(editedAlice));

        // different appointment -> returns false
        editedAlice = new PersonBuilder(ALICE).withAppointment("Nurse").build();
        assertFalse(ALICE.equals(editedAlice));

        //different NOK -> returns false
        editedAlice = new PersonBuilder(ALICE).withNextOfKin("Jane 91234567").build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different medical history -> returns false
        editedAlice = new PersonBuilder(ALICE).withMedicalHistory("Allergic to peanuts").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", bloodType=" + ALICE.getBloodType() + ", appointment=" + ALICE.getAppointment()
                + ", nextOfKin=" + ALICE.getNextOfKin() + ", tags=" + ALICE.getTags() + ", medicalHistory="
                + ALICE.getMedicalHistory()
                + ", checkups=" + ALICE.getCheckups() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void constructor_optionalFieldsAreHandled() {
        Name name = new Name("Alice Pauline");
        DateOfBirth dob = new DateOfBirth("01/01/1990");
        Phone phone = new Phone("91234567");
        Email nilEmail = new Email("nil");
        Email emptyEmail = new Email("");
        BloodType bloodType = new BloodType("AB+");
        Address address = new Address("123, Jurong West Ave 6, #08-111");
        Appointment appointment = new Appointment("Patient");

        Person personWithNullEmail = new Person(name, dob, phone, new Email("nil"),
                new Address("123, Jurong West Ave 6, #08-111"),
                new BloodType("AB+"), new Appointment("Patient"), new HashSet<>(),
                null, new HashSet<>(), new HashSet<>());
        assertNotNull(personWithNullEmail);

        Person personWithEmptyEmail = new Person(name, dob, phone, new Email(""),
                new Address("123, Jurong West Ave 6, #08-111"),
                new BloodType("AB+"), new Appointment("Patient"), new HashSet<>(),
                null, new HashSet<>(), new HashSet<>());
        assertNotNull(personWithEmptyEmail);
    }
}
