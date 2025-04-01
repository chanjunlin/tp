package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.checkup.Checkup;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new DateOfBirth("01/01/2001"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new BloodType("AB+"), new Appointment("Nurse"), getTagSet("Manager"),
                    new NextOfKin("Alexis 91023434"), new HashSet<>(), new HashSet<>()),
            new Person(new Name("Bernice Yu"), new DateOfBirth("01/01/1900"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new BloodType("AB+"), new Appointment("Nurse"), getTagSet("Newcomer"),
                    new NextOfKin("Alexis 91023434"), new HashSet<>(), new HashSet<>()),
            new Person(new Name("Charlotte Oliveiro"), new DateOfBirth("12/05/1989"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new BloodType("AB+"), new Appointment("Nurse"), getTagSet("Newcomer"),
                    new NextOfKin("Alexis 91023434"), new HashSet<>(), new HashSet<>()),
            new Person(new Name("David Li"), new DateOfBirth("14/07/1989"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new BloodType("AB+"), new Appointment("Nurse"), getTagSet("Manager"),
                    new NextOfKin("Alexis 91023434"), new HashSet<>(), new HashSet<>()),
            new Person(new Name("Irfan Ibrahim"), new DateOfBirth("04/04/2004"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new BloodType("AB+"), new Appointment("Nurse"), getTagSet("Expert in drawing blood"),
                    new NextOfKin("Alexis 91023434"), new HashSet<>(), new HashSet<>()),
            new Person(new Name("Roy Balakrishnan"), new DateOfBirth("11/09/2001"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new BloodType("AB+"), new Appointment("Patient"), getTagSet("Throws tantrum"),
                    new NextOfKin("Alexis 91023434"), getMedicalHistorySet("Diabetes"),
                    new HashSet<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<Checkup> getCheckupSet(String ... dateTimeStrings) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return Arrays.stream(dateTimeStrings)
                .map(dateTimeString -> {
                    String[] parts = dateTimeString.split(" ");
                    LocalDate date = LocalDate.parse(parts[0], dateFormatter);
                    LocalTime time = LocalTime.parse(parts[1], timeFormatter);
                    try {
                        Checkup checkup = new Checkup(date, time, true);
                        return checkup;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    public static Set<MedicalHistory> getMedicalHistorySet(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalHistory::new)
                .collect(Collectors.toSet());
    }
}
