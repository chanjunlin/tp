package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withDateOfBirth("01/01/2001")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withCheckups("12/12/2025 10:00")
            .withNextOfKin("John 92231333")
            .withTags("NurseBensonMeier")
            .withMedicalHistory("Diabetes")
            .withDateOfBirth("01/01/2001").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withDateOfBirth("01/01/2001")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withBloodType("AB+")
            .withAppointment("Nurse")
            .withNextOfKin("John 92231333")
            .withTags("owesMoney", "friends")
            .withNextOfKin("Jane 82342322").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withDateOfBirth("01/01/2001")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withNextOfKin("John 92231333")
            .withMedicalHistory("Diabetes").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withDateOfBirth("01/01/2001")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withNextOfKin("John 92231333")
            .withMedicalHistory("Diabetes").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withDateOfBirth("01/01/2001")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withAddress("michegan ave")
            .withNextOfKin("John 92231333")
            .withMedicalHistory("Diabetes").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withDateOfBirth("01/01/2001")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withNextOfKin("John 92231333")
            .withMedicalHistory("Diabetes").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withDateOfBirth("01/01/2001")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withBloodType("AB+")
            .withAppointment("Nurse")
            .withNextOfKin("John 92231333").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withDateOfBirth("01/01/2001")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withBloodType("AB+")
            .withAppointment("Patient")
            .withNextOfKin("John 92231333")
            .withMedicalHistory("Diabetes").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withDateOfBirth("01/01/2001")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withBloodType("AB+")
            .withAppointment("Nurse")
            .withNextOfKin("John 92231333").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withDateOfBirth(VALID_DOB_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withBloodType(VALID_BLOOD_TYPE_AMY).withAppointment(VALID_APPOINTMENT_AMY).withNextOfKin(VALID_NOK_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withDateOfBirth(VALID_DOB_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withBloodType(VALID_BLOOD_TYPE_BOB).withAppointment(VALID_APPOINTMENT_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withNextOfKin(VALID_NOK_BOB)
            .withMedicalHistory("Cancer").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
