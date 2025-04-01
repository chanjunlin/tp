package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building a new Person object
 * with updated tags based on the original person's tags that starts with "Nurse".
 */
public class CheckTagFromPreviousPerson {

    private final Person originalPerson;
    private final Person editedPerson;

    /**
     * Returns a {@code CheckTagFromPreviousPerson} with fields containing {@code editedPerson}'s details with
     * {@code originalPerson}'s tags that starts with "Nurse".
     *
     * @param originalPerson The original person before editing.
     * @param editedPerson The edited person after editing.
     */
    public CheckTagFromPreviousPerson(Person originalPerson, Person editedPerson) {
        requireNonNull(originalPerson);
        requireNonNull(editedPerson);
        this.originalPerson = originalPerson;
        this.editedPerson = editedPerson;
    }

    /**
     * Checks if the original person has a tag that starts with "Nurse",
     * it will be added to the updated tags for the edited person.
     *
     * @return a new Person object with updated tags.
     */
    public Person build() {
        Set<Tag> updatedTags = new HashSet<>(editedPerson.getTags());

        for (Tag tag : originalPerson.getTags()) {
            if (tag.tagName.startsWith("Nurse")) {
                updatedTags.add(tag);
            }
        }

        return new Person(editedPerson.getName(),
                          editedPerson.getDateOfBirth(),
                          editedPerson.getPhone(),
                          editedPerson.getEmail(),
                          editedPerson.getAddress(),
                          editedPerson.getBloodType(),
                          editedPerson.getAppointment(),
                          updatedTags,
                          editedPerson.getNextOfKin(),
                          editedPerson.getMedicalHistory(),
                          editedPerson.getCheckups());
    }
}
