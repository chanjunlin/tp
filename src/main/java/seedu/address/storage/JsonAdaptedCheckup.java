package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.checkup.Checkup;


/**
 * test
 */
public class JsonAdaptedCheckup {
    private final String date;
    private final String time;

    /**
     * test
     * @param date test
     * @param time test
     */
    @JsonCreator
    public JsonAdaptedCheckup(@JsonProperty("date") String date,
                              @JsonProperty("time") String time) {
        this.date = date;
        this.time = time;
    }

    /**
     * test
     * @param source test
     */
    public JsonAdaptedCheckup(Checkup source) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        date = source.getCheckupDate().format(dateFormatter);
        time = source.getCheckupTime().format(timeFormatter);
    }

    /**
     * test
     * @return test
     * @throws IllegalValueException test
     */
    public Checkup toModelType() throws IllegalValueException {
        LocalDate checkupDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime checkupTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

        return new Checkup(checkupDate, checkupTime);
    }
}
