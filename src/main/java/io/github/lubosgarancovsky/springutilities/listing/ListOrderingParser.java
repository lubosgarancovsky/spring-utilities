package io.github.lubosgarancovsky.springutilities.listing;

import io.github.lubosgarancovsky.springutilities.listing.ImmutableListOrdering;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ListOrderingParser {

    public static final Character ASCENDING = ';';
    public static final Character DESCENDING = ':';
    public static final String DELIMITER = ",";
    private static final List<Character> SUPPORTED_SUFFIXES = List.of(ASCENDING, DESCENDING);

    // Add LanguageCode[] languageCodes as method parameter from i18n library to support translations
    public <T extends ListingAttribute> ListOrderings<T> parse(
            String source, List<T> attributes) {
        if (source == null || source.isEmpty()) {
            return ListOrderings.empty();
        }
        final List<T> supportedAttributes =
                attributes.stream().filter(ListingAttribute::isForSorting).toList();

        final List<ListOrdering<T>> orderings =
                Stream.of(source.split(DELIMITER))
                        .map(paramWithSuffix -> parseParam(paramWithSuffix, supportedAttributes))
                        .collect(Collectors.toList());

        final List<T> translatedAttributes =
                supportedAttributes.stream()
                        .filter(ListingAttribute::isTranslated)
                        .filter(attribute -> compareAttributeWithOrderings(orderings, attribute))
                        .toList();

//        if (!translatedAttributes.isEmpty() && (languageCodes == null || languageCodes.length == 0)) {
//            throw MISSING_LANGUAGE_CODE.createError(translatedAttributes).convertToException();
//        }

        return ListOrderings.create(orderings);
    }

    private <T extends ListingAttribute> boolean compareAttributeWithOrderings(
            List<ListOrdering<T>> orderings, ListingAttribute attribute) {
        return orderings.stream()
                .anyMatch(ordering -> ordering.attribute().apiName().equals(attribute.apiName()));
    }

    private <T extends ListingAttribute> ListOrdering<T> parseParam(
            String paramWithSuffix, List<T> supportedAttributes) {
        final Character suffix = paramWithSuffix.charAt(paramWithSuffix.length() - 1);

        if (!SUPPORTED_SUFFIXES.contains(suffix)) {
            throw ListParserErrorCode.MISSING_SUPPORTED_SORTING_SUFFIX
                    .createError(
                            SUPPORTED_SUFFIXES.stream().map(Objects::toString).collect(Collectors.joining(",")))
                    .convertToException();
        }
        final String param = paramWithSuffix.replace(suffix.toString(), "");

        return createOrdering(param, suffix.equals(ASCENDING), supportedAttributes);
    }

    private <T extends ListingAttribute> ListOrdering<T> createOrdering(
            String param, boolean ascending, List<T> supportedAttributes) {
        return supportedAttributes.stream()
                .filter(attribute -> attribute.apiName().equalsIgnoreCase(param))
                .findFirst()
                .map(
                        attribute ->
                                ImmutableListOrdering.<T>builder()
                                        .attribute(attribute)
                                        .ascending(ascending)
                                        .build())
                .orElseThrow(
                        () -> {
                            final List<String> namesOfSupportedAttributes =
                                    supportedAttributes.stream()
                                            .map(ListingAttribute::apiName)
                                            .collect(Collectors.toList());

                            return ListParserErrorCode.UNSUPPORTED_SORTING_ATTR
                                    .createError(param, String.join(",", namesOfSupportedAttributes))
                                    .convertToException();
                        });
    }
}