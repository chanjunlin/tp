package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
import seedu.address.model.person.PersonHasAppointmentPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DOB + "DOB] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BLOODTYPE + "BLOODTYPE] "
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT] "
            + "[" + PREFIX_NOK + "NEXTOFKIN] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_MEDICAL_HISTORY + "MEDICAL_HISTORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_MEDICAL_HISTORY = "Medical history should not be added to a nurse.";
    public static final String MESSAGE_INVALID_MEDICAL_HISTORY_DELETE = "Delete medical history in order "
                                                                      + "to change to nurse appointment."
                                                                      + " (e.g. edit 1 mh/ to remove medical history).";
    public static final String MESSAGE_UNABLE_TO_CHANGE_APPOINTMENT_TO_PATIENT = "Unable to change appointment to a "
                                                                               + "patient, as "
                                                                               + "this nurse is assigned to a patient."
                                                                               + "\n"
                                                                               + "Please remove all assigned patients "
                                                                               + "to this nurse before changing "
                                                                               + "appointment to patient.";
    public static final String MESSAGE_UNABLE_TO_CHANGE_NAME = "Unable to change name, "
                                                              + "as this nurse is assigned to a patient."
                                                              + "\n"
                                                              + "Please remove all assigned patients to this nurse "
                                                              + "before changing name.";
    public static final String MESSAGE_UNABLE_TO_CHANGE_APPOINTMENT_TO_NURSE = "Unable to change appointment to a "
                                                                             + "nurse, as this patient is assigned "
                                                                             + "to a nurse." + "\n"
                                                                             + "Please remove all assigned nurses "
                                                                             + "before changing appointment to nurse.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit.
     * @param editPersonDescriptor details to edit the person with.
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        ensureOnlyPatientCanHaveMedicalHistory(editedPerson);

        if (editPersonDescriptor.getAppointment().isPresent()) {
            ensurePatientHasNoAssignedNurse(personToEdit, model);
            ensureNurseHasNoPatient(personToEdit, editedPerson, model);
        }

        if (editPersonDescriptor.getName().isPresent()) {
            ensureChangeNameNurseIfNoPatient(personToEdit, model);
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        updateModelList(model);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    // Ensure that a nurse can only change appointment to a patient if they have no patients assigned.
    private void ensureNurseHasNoPatient(Person personToEdit, Person editedPerson,
                                         Model personModel) throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        Appointment appointmentBeforeEdit = personToEdit.getAppointment();
        boolean isNurse = appointmentBeforeEdit.toString().equalsIgnoreCase("nurse");
        Appointment appointmentAfterEdit = editedPerson.getAppointment();
        if (isNurse && appointmentAfterEdit.isPatient()) {
            String name = personToEdit.getName().toString();
            boolean patientHasEditedNurse = personModel.getFilteredPersonList()
                                                       .stream()
                                                       .filter(person -> person.getAppointment().isPatient())
                                                       .anyMatch(person -> person.getTags().stream()
                                                       .anyMatch(tag -> tag.tagName.equals("Nurse " + name)));

            if (patientHasEditedNurse) {
                throw new CommandException(MESSAGE_UNABLE_TO_CHANGE_APPOINTMENT_TO_PATIENT);
            }
        }
    }

    // To ensure that a Nurse can only change name if they have no patients assigned.
    private void ensureChangeNameNurseIfNoPatient(Person personToEdit, Model model) throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(model);

        Appointment appointmentBeforeEdit = personToEdit.getAppointment();
        boolean isNurse = appointmentBeforeEdit.toString().equalsIgnoreCase("nurse");
        String name = personToEdit.getName().toString();
        boolean nurseHasPatientAssigned = model.getFilteredPersonList()
                                               .stream()
                                               .filter(person -> person.getAppointment().isPatient())
                                               .anyMatch(person -> person.getTags().stream()
                                               .anyMatch(tag -> tag.tagName.equals("Nurse " + name)));

        if (isNurse && nurseHasPatientAssigned) {
            throw new CommandException(MESSAGE_UNABLE_TO_CHANGE_NAME);
        }
    }

    // Ensure that a patient can change to a nurse if they have no medical history.
    private void ensureOnlyPatientCanHaveMedicalHistory(Person editedPerson) throws CommandException {
        requireNonNull(editedPerson);
        boolean editedPersonIsNurse = editedPerson.isNurse();
        boolean editedPersonHasMedicalHistory = editedPerson.hasMedicalHistory();
        if (editedPersonIsNurse && editedPersonHasMedicalHistory) {
            throw new CommandException(MESSAGE_INVALID_MEDICAL_HISTORY + "\n"
                                                                       + MESSAGE_INVALID_MEDICAL_HISTORY_DELETE);
        }
    }

    // Ensure that a patient can change to a nurse if they have no assigned nurse.
    private void ensurePatientHasNoAssignedNurse(Person personToEdit, Model personModel) throws CommandException {
        String name = personToEdit.getName().toString();
        boolean hasNurseAssigned = personModel.getFilteredPersonList()
                                              .stream()
                                              .filter(person -> person.getName()
                                                                             .toString()
                                                                             .equalsIgnoreCase(name))
                                              .anyMatch(person -> person.getTags().stream()
                                              .anyMatch(tag -> tag.tagName.startsWith("Nurse ")));
        boolean changeToPatient = editPersonDescriptor.getAppointment().get().isPatient();
        if (personToEdit.isPatient() && hasNurseAssigned && !changeToPatient) {
            throw new CommandException(MESSAGE_UNABLE_TO_CHANGE_APPOINTMENT_TO_NURSE);
        }
    }

    /**
     * Updates the model list based on the current filter for appointments.
     *
     * @param model Model to be updated.
     */
    private void updateModelList(Model model) {
        if (ListCommand.getAppointmentFilter() != null) {
            // If list is filtered by appointment.
            model.updateFilteredPersonList(new PersonHasAppointmentPredicate(ListCommand.getAppointmentFilter()));
        } else {
            // If list isn't filtered by appointment.
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        DateOfBirth updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        BloodType updatedBloodType = editPersonDescriptor.getBloodType().orElse(personToEdit.getBloodType());
        Appointment updatedAppointment = editPersonDescriptor.getAppointment().orElse(personToEdit.getAppointment());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().map(HashSet::new)
                                                             .orElse(new HashSet<>(personToEdit.getTags()));
        NextOfKin nextOfKin = editPersonDescriptor.getNextOfKin().orElse(personToEdit.getNextOfKin());
        Set<MedicalHistory> updatedMedicalHistory = editPersonDescriptor.getMedicalHistory()
                                                                        .orElse(personToEdit.getMedicalHistory());
        Set<Checkup> currentCheckups = editPersonDescriptor.getCheckups().orElse(personToEdit.getCheckups());

        if (personToEdit.isPatient()) {
            updatedTags.addAll(personToEdit.getTags().stream().filter(tag -> tag.tagName.startsWith("Nurse"))
                                       .collect(Collectors.toSet()));
        }

        return new Person(updatedName, updatedDateOfBirth, updatedPhone, updatedEmail, updatedAddress, updatedBloodType,
                          updatedAppointment, updatedTags, nextOfKin, updatedMedicalHistory, currentCheckups);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private DateOfBirth dob;
        private Phone phone;
        private Email email;
        private Address address;
        private BloodType bloodType;
        private Appointment appointment;
        private Set<Tag> tags;
        private NextOfKin nextOfKin;
        private Set<MedicalHistory> medicalHistory;
        private Set<Checkup> checkups;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setDateOfBirth(toCopy.dob);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setBloodType(toCopy.bloodType);
            setAppointment(toCopy.appointment);
            setNextOfKin(toCopy.nextOfKin);
            setMedicalHistory(toCopy.medicalHistory);
            setCheckups(toCopy.checkups);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, dob, phone, email, address,
                    bloodType, appointment, tags, nextOfKin, medicalHistory);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDateOfBirth(DateOfBirth dob) {
            this.dob = dob;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dob);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        public Optional<Appointment> getAppointment() {
            return Optional.ofNullable(appointment);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setNextOfKin(NextOfKin nextOfKin) {
            this.nextOfKin = nextOfKin;
        }

        /**
         * Returns the next of kin of the person.
         * Returns {@code Optional#empty()} if {@code nextOfKin} is null.
         */
        public Optional<NextOfKin> getNextOfKin() {
            return Optional.ofNullable(nextOfKin);
        }


        public void setMedicalHistory(Set<MedicalHistory> medicalHistory) {
            this.medicalHistory = (medicalHistory != null) ? new HashSet<>(medicalHistory) : null;
        }

        public Optional<Set<MedicalHistory>> getMedicalHistory() {
            return (medicalHistory != null) ? Optional.of(Collections.unmodifiableSet(medicalHistory))
                                            : Optional.empty();
        }

        public void setCheckups(Set<Checkup> checkups) {
            this.checkups = (checkups != null) ? new HashSet<>(checkups) : null;
        }

        public Optional<Set<Checkup>> getCheckups() {
            return (checkups != null) ? Optional.of(Collections.unmodifiableSet(checkups))
                                      : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(dob, otherEditPersonDescriptor.dob)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(bloodType, otherEditPersonDescriptor.bloodType)
                    && Objects.equals(appointment, otherEditPersonDescriptor.appointment)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(nextOfKin, otherEditPersonDescriptor.nextOfKin)
                    && Objects.equals(medicalHistory, otherEditPersonDescriptor.medicalHistory)
                    && Objects.equals(checkups, otherEditPersonDescriptor.checkups);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("dob", dob)
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
}
