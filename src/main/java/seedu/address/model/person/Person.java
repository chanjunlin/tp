package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.checkup.Checkup;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final DateOfBirth dob;
    private final Phone phone;
    private final Email email;
    private final Appointment appointment;

    // Data fields
    private final Address address;
    private final BloodType bloodType;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Checkup> checkups = new HashSet<>();
    private final NextOfKin nextOfKin;
    private final Set<MedicalHistory> medicalHistory = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, DateOfBirth dob, Phone phone, Email email, Address address, BloodType bloodType,
                  Appointment appointment, Set<Tag> tags, NextOfKin nextOfKin,
                  Set<MedicalHistory> medicalHistory, Set<Checkup> checkups) {
        requireAllNonNull(name, dob, phone, email, address, bloodType, appointment, tags, medicalHistory, checkups);
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bloodType = bloodType;
        this.appointment = appointment;
        this.tags.addAll(tags);
        this.nextOfKin = nextOfKin;
        this.medicalHistory.addAll(medicalHistory);
        this.checkups.addAll(checkups);
    }

    /**
     * Optional Checkup field, email present
     */
    public Person(Name name, DateOfBirth dob, Phone phone, Email email, Address address, BloodType bloodType,
                  Appointment appointment, Set<Tag> tags, NextOfKin nextOfKin, Set<MedicalHistory> medicalHistory) {
        requireAllNonNull(name, dob, phone, email, address, bloodType, appointment, tags, medicalHistory, nextOfKin);
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bloodType = bloodType;
        this.appointment = appointment;
        this.nextOfKin = nextOfKin;
        this.tags.addAll(tags);
        this.medicalHistory.addAll(medicalHistory);
        this.checkups.addAll(new HashSet<>());
    }


    public Name getName() {
        return name;
    }
    public DateOfBirth getDateOfBirth() {
        return dob;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        if (tags.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(tags);
    }

    public Set<Checkup> getCheckups() {
        return Collections.unmodifiableSet(checkups);
    }

    public boolean hasCheckup() {
        return !checkups.isEmpty();
    }
    /**
     * Returns the next of kin of the person, if available.
     * May be {@code null} if not specified.
     */
    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    /**
     * Returns true if tags is empty and false otherwise.
     */
    public boolean checkIfTagsIsEmpty() {
        return getTags().isEmpty();
    }

    /**
     * Returns an immutable medical history set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MedicalHistory> getMedicalHistory() {
        if (medicalHistory.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(medicalHistory);
    }

    /**
     * Returns true if medical history is empty and false otherwise.
     */
    public boolean checkIfMedicalHistoryIsEmpty() {
        return getMedicalHistory().isEmpty();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }

        String otherPersonPhone = otherPerson.getPhone().toString().replaceAll(" ", "");
        String thisPersonPhone = getPhone().toString().replaceAll(" ", "");

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPersonPhone.equals(thisPersonPhone)
                && otherPerson.getDateOfBirth().equals(getDateOfBirth());
    }


    /**
     * Returns true if the appointment is nurse and false otherwise.
     */
    public boolean isNurse() {
        return this.getAppointment().toString().equalsIgnoreCase("nurse");
    }

    /**
     * Returns true if the appointment is patient and false otherwise.
     */
    public boolean isPatient() {
        return this.getAppointment().toString().equalsIgnoreCase("patient");
    }

    /**
     * Returns true if the person has medical history and false otherwise.
     */
    public boolean hasMedicalHistory() {
        return !this.getMedicalHistory().isEmpty();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && dob.equals(otherPerson.dob)
                && bloodType.equals(otherPerson.bloodType)
                && (address.equals(otherPerson.address)
                || phone.equals(otherPerson.phone)
                || email.equals(otherPerson.email));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, bloodType, appointment, tags, nextOfKin, medicalHistory,
                checkups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("bloodType", bloodType)
                .add("appointment", appointment)
                .add("nextOfKin", nextOfKin)
                .add("tags", tags)
                .add("medicalHistory", medicalHistory)
                .add("checkups", checkups)
                .toString();
    }
}
