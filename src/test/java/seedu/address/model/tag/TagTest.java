package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        // same values -> returns true
        assertEquals(new Tag("tag1"), tag1);

        // same object -> returns true
        assertEquals(tag1, tag1);

        // null -> returns false
        assertNotEquals(null, tag1);

        // different types -> returns false
        assertNotEquals("Hi There", tag1);

        // different object -> returns false
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_differentObjectTypes() {
        Tag tag1 = new Tag("tag1");
        String tag2 = "tag2";
        // different types -> returns false
        assertFalse(tag1.equals(tag2));
    }
}
