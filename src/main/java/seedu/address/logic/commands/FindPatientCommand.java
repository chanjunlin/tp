package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasAppointmentPredicate;
import seedu.address.model.tag.Tag;

/**
 * Changes the remark of an existing person in the address book.
 */
public class FindPatientCommand extends FindCommand {

    public static final String COMMAND_WORD = "find patient of nurse ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients assigned to the nurse at "
            + "the specified index and displays them as a list with index numbers.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_NURSE = "The person at index %d is not a nurse.";
    public static final String MESSAGE_NO_PATIENT_ASSIGNED = "No patient assigned to the nurse at index %d.";
    public static final String MESSAGE_PATIENT_FOUND = "Patient(s) assigned to nurse %s: %s.";

    private final Index nurseIndex;

    /**
     * Creates a FindPatientCommand to find patients assigned to the nurse at the specified index.
     *
     * @param nurseIndex The index of the nurse whose patients need to be found.
     */
    public FindPatientCommand(Index nurseIndex) {
        super(new NameContainsKeywordsPredicate(Arrays.asList("patient")));
        this.nurseIndex = nurseIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        PersonHasAppointmentPredicate currentPredicate = getCurrentPredicate();
        Person nurse = getNurseFromModel(model);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<String> patientNames = getAssignedPatientNames(nurse, model);

        model.updateFilteredPersonList(Objects.requireNonNullElse(currentPredicate, PREDICATE_SHOW_ALL_PERSONS));

        if (patientNames.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_PATIENT_ASSIGNED, nurseIndex.getOneBased()));
        }

        return new CommandResult(String.format(MESSAGE_PATIENT_FOUND, nurse.getName(),
                String.join(", ", patientNames)));
    }

    /**
     * Gets the current appointment filter, if any, from the ListCommand.
     *
     * @return The current PersonHasAppointmentPredicate, or null if no filter is set.
     */
    private PersonHasAppointmentPredicate getCurrentPredicate() {
        return ListCommand.getAppointmentFilter() != null
                ? new PersonHasAppointmentPredicate(ListCommand.getAppointmentFilter())
                : null;
    }

    /**
     * Retrieves the nurse from the model based on the nurse index.
     *
     * @param model The model to fetch the filtered list of persons from.
     * @return The person representing the nurse at the specified index.
     * @throws CommandException If the nurse index is invalid or the person at the index is not a nurse.
     */
    private Person getNurseFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (nurseIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        Person nurse = lastShownList.get(nurseIndex.getZeroBased());

        if (!nurse.getAppointment().toString().equals("Nurse")) {
            throw new CommandException(String.format(MESSAGE_INVALID_NURSE, nurseIndex.getOneBased()));
        }

        return nurse;
    }

    /**
     * Gets the names of the patients assigned to the given nurse.
     *
     * @param nurse The nurse whose assigned patients need to be found.
     * @param model The model to get the list of persons from.
     * @return A list of patient names assigned to the nurse.
     */
    private List<String> getAssignedPatientNames(Person nurse, Model model) {
        return model.getFilteredPersonList().stream()
                .filter(person -> isPatientAssignedToNurse(person, nurse))
                .map(person -> person.getName().toString())
                .collect(Collectors.toList());
    }


    /**
     * Checks if a patient is assigned to a nurse based on tags.
     *
     * @param patient The patient to check.
     * @param nurse The nurse to check if the patient is assigned to.
     * @return true if the patient is assigned to the nurse, false otherwise.
     */
    private boolean isPatientAssignedToNurse(Person patient, Person nurse) {
        Set<Tag> tags = patient.getTags();
        String nurseName = nurse.getName().toString().toUpperCase();
        return tags.stream()
                .anyMatch(tag -> tag.tagName.toUpperCase().equals("NURSE " + nurseName));
    }
}
