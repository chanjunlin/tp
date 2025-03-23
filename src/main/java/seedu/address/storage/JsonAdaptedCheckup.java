package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.checkup.Checkup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.time.LocalTime;

public class JsonAdaptedCheckup {
    private final String patientName;
    private final String nurseName;
    private final String date;
    private final String time;

    private final Person patient;
    private final Person nurse;

    @JsonCreator
    public JsonAdaptedCheckup(@JsonProperty("patientName") String patientName,
                              @JsonProperty("nurseName") String nurseName,
                              @JsonProperty("date") String date,
                              @JsonProperty("time") String time) {
        this.nurse =
        this.patientName = patientName;
        this.nurseName = nurseName;
        this.date = date;
        this.time = time;
    }

    public JsonAdaptedCheckup(Checkup source) {
        patient = source.getPatient();
        nurse = source.getNurse();
        patientName = source.getPatient().getName().fullName;
        nurseName = source.getNurse().getName().fullName;
        date = source.getCheckupDate().toString();
        time = source.getCheckupTime().toString();
    }

    public Checkup toModelType() throws IllegalValueException {
        Person patient = new Person(new Name(patientName));
        Person nurse = new Person(new Name(nurseName));
        LocalDate checkupDate = LocalDate.parse(date);
        LocalTime checkupTime = LocalTime.parse(time);

        return new Checkup(patient, nurse, checkupDate, checkupTime);
    }
}