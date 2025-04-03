package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s name matches the specified name.
 */
public class PersonHasSameNamePredicate implements Predicate<Person> {

    private final Name name;

    public PersonHasSameNamePredicate(Name name) {
        this.name = name;
    }

    @Override
    public boolean test(Person person) {
        return person.getName().equals(name);
    }
}
