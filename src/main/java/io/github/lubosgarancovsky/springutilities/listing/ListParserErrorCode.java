package io.github.lubosgarancovsky.springutilities.listing;

import io.github.lubosgarancovsky.springutilities.error.ErrorCode;
import io.github.lubosgarancovsky.springutilities.error.ErrorCodeType;

public enum ListParserErrorCode implements ErrorCode {
    UNSUPPORTED_FILTERING_ATTR("Filtering attribute '%s' is not supported, supported are '%s'"),
    UNSUPPORTED_SORTING_ATTR("Sorting attribute '%s' is not supported, supported are '%s'"),
    MISSING_SUPPORTED_SORTING_SUFFIX("Missing supported sorting suffix. Supported suffixes are '%s'"),
    INVALID_FILTERING_QUERY("Filtering query is invalid."),
    FILTERING_ATTR_REQUIRES_MIN_SIZE("Filtering attribute '%s' requires a minimum size of '%s'"),
    MISSING_LANGUAGE_CODE(
            "Translated attribute '%s' for sorting or filtering expect one language code on the input in Accept-Language header.");

    final String template;

    ListParserErrorCode(String template) {
        this.template = template;
    }

    @Override
    public String template() {
        return template;
    }

    @Override
    public ErrorCodeType type() {
        return ErrorCodeType.BAD_REQUEST;
    }
}