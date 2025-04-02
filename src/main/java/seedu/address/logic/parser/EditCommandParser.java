package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedicalHistory;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_BLOODTYPE, PREFIX_APPOINTMENT, PREFIX_NOK, PREFIX_TAG, PREFIX_MEDICAL_HISTORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DOB, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                                                 PREFIX_BLOODTYPE, PREFIX_APPOINTMENT);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        parseName(editPersonDescriptor, argMultimap);
        parseDateOfBirth(editPersonDescriptor, argMultimap);
        parsePhone(editPersonDescriptor, argMultimap);
        parseEmail(editPersonDescriptor, argMultimap);
        parseAddress(editPersonDescriptor, argMultimap);
        parseBloodType(editPersonDescriptor, argMultimap);
        parseAppointment(editPersonDescriptor, argMultimap);
        parseNextOfKin(editPersonDescriptor, argMultimap);

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        parseMedicalHistoryForEdit(argMultimap.getAllValues(PREFIX_MEDICAL_HISTORY))
                                   .ifPresent(editPersonDescriptor::setMedicalHistory);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    private void parseName(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
    }

    private void parseDateOfBirth(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            descriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get()));
        }
    }

    private void parsePhone(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
    }

    private void parseEmail(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
    }

    private void parseAddress(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
    }

    private void parseBloodType(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_BLOODTYPE).isPresent()) {
            descriptor.setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get()));
        }
    }

    private void parseAppointment(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            descriptor.setAppointment(ParserUtil.parseAppointment(argMultimap.getValue(PREFIX_APPOINTMENT).get()));
        }
    }

    private void parseNextOfKin(EditPersonDescriptor descriptor, ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NOK).isPresent()) {
            descriptor.setNextOfKin(ParserUtil.parseNextOfKin(argMultimap.getValue(PREFIX_NOK).get()));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    private Optional<Set<MedicalHistory>> parseMedicalHistoryForEdit(Collection<String> medicalHistories)
                                                                                                throws ParseException {
        assert medicalHistories != null;

        if (medicalHistories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> medicalHistorySet = medicalHistories.size() == 1 && medicalHistories.contains("")
                                               ? Collections.emptySet()
                                               : medicalHistories;
        return Optional.of(ParserUtil.parseMedicalHistories(medicalHistorySet));
    }

}
