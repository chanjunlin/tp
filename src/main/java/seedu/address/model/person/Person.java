package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Appointment appointment;

    // Data fields
    private final Address address;
    private final BloodType bloodType;
    private final Set<Tag> tags = new HashSet<>();
    private final NextOfKin nextOfKin;

    /**
     * Every field must be present and not null.
     * TODO add in blood type parameter
     */
    public Person(Name name, Phone phone, Email email, Address address, BloodType bloodType,
                  Appointment appointment, Set<Tag> tags, NextOfKin nextOfKin) {
        requireAllNonNull(name, phone, email, address, tags, bloodType, nextOfKin);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.bloodType = bloodType;
        this.appointment = appointment;
        this.nextOfKin = nextOfKin;
    }

    public Name getName() {
        return name;
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
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the next of kin of the person, if available.
     * May be {@code null} if not specified.
     */
    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && bloodType.equals(otherPerson.bloodType)
                && appointment.equals(otherPerson.appointment)
                && nextOfKin.equals(otherPerson.nextOfKin)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, bloodType, appointment, tags, nextOfKin);
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
                .toString();
    }

}
