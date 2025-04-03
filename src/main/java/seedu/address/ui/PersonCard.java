package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private VBox container;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label dob;
    @FXML
    private Label dob1;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label bloodType;
    @FXML
    private Label appointment;
    @FXML
    private Label nextOfKin;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane checkups;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        setDobText(person);
        setAddressText(person);
        setPhoneText(person);
        setEmailText(person);
        setBloodTypeText(person);
        appointment.setText(person.getAppointment().appointment.toUpperCase());
        setAppointmentLabel(appointment.getText());
        setNextOfKin(person);
        displayTags(person);
        displayCheckups(person);
    }

    /**
     * test
     * @param person test
     */
    public void displayCheckups(Person person) {
        checkups.getChildren().clear();

        long nonEmptyCheckupCount = person.getCheckups().stream()
                .filter(checkup -> checkup != null && !checkup.toString().trim().isEmpty())
                .count();

        if (nonEmptyCheckupCount == 0) {
            checkups.setVisible(false);
            return;
        } else {
            checkups.setVisible(true);
        }

        person.getCheckups().stream()
                .filter(checkup -> checkup != null && !checkup.toString().trim().isEmpty())
                .sorted(Comparator.comparing(checkup -> checkup.checkupDateTime))
                .forEach(checkup -> {
                    Label checkupLabel = new Label(checkup.toString());
                    checkupLabel.getStyleClass().add("checkup-label");
                    checkups.getChildren().add(checkupLabel);
                });
    }


    /**
     * test
     * @param person test
     */
    public void displayTags(Person person) {
        tags.getChildren().clear();

        long nonEmptyTagsCount = person.getTags().stream()
                .filter(tag -> tag != null && !tag.tagName.trim().isEmpty())
                .count();

        if (nonEmptyTagsCount == 0) {
            tags.setVisible(false);
            return;
        } else {
            tags.setVisible(true);
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (tag.tagName.contains("Nurse")) {
                        tagLabel.getStyleClass().add("tags-nurse-label");
                    } else {
                        tagLabel.getStyleClass().add("tags-label");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }


    public void setAddressText(Person person) {
        Text addressLabelText = new Text("Address: ");
        addressLabelText.setStyle("-fx-font-weight: bold;");
        Text addressValueText = new Text(person.getAddress().toString());
        TextFlow textFlow = new TextFlow(addressLabelText, addressValueText);
        address.setGraphic(textFlow);
    }

    public void setAppointmentLabel(String appointmentType) {
        if (appointmentType.equals("NURSE")) {
            appointment.getStyleClass().add("nurse");
            appointment.getStyleClass().remove("patient");
        } else if (appointmentType.equals("PATIENT")) {
            appointment.getStyleClass().add("patient");
            appointment.getStyleClass().remove("nurse");
        } else {
            appointment.getStyleClass().add("default");
        }
    }

    public void setBloodTypeText(Person person) {
        Text bloodTypeLabelText = new Text("Bloodtype: ");
        bloodTypeLabelText.setStyle("-fx-font-weight: bold;");
        Text bloodTypeValueText = new Text(person.getBloodType().toString());
        TextFlow textFlow = new TextFlow(bloodTypeLabelText, bloodTypeValueText);
        bloodType.setGraphic(textFlow);
    }

    public void setDobText(Person person) {
        Text dobLabelText = new Text("Dob: ");
        dobLabelText.setStyle("-fx-font-weight: bold;");
        Text dobValueText = new Text(person.getDateOfBirth().toString());
        TextFlow textFlow = new TextFlow(dobLabelText, dobValueText);
        dob.setGraphic(textFlow);
    }

    public void setEmailText(Person person) {
        Text emailLabelText = new Text("Email: ");
        emailLabelText.setStyle("-fx-font-weight: bold;");
        Text emailValueText = new Text(person.getEmail().toString());
        TextFlow textFlow = new TextFlow(emailLabelText, emailValueText);
        email.setGraphic(textFlow);
    }

    public void setPhoneText(Person person) {
        Text phoneLabelText = new Text("Phone: ");
        phoneLabelText.setStyle("-fx-font-weight: bold;");
        Text phoneValueText = new Text(person.getPhone().toString());
        TextFlow textFlow = new TextFlow(phoneLabelText, phoneValueText);
        phone.setGraphic(textFlow);
    }

    public void setNextOfKin(Person person) {
        Text nextOfKinLabelText = new Text("Next of kin: ");
        nextOfKinLabelText.setStyle("-fx-font-weight: bold;");
        Text nextOfKindValueText = new Text(person.getNextOfKin().toString());
        TextFlow textFlow = new TextFlow(nextOfKinLabelText, nextOfKindValueText);
        nextOfKin.setGraphic(textFlow);
    }
}
