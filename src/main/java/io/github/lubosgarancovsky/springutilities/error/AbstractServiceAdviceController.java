package io.github.lubosgarancovsky.springutilities.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;

import io.github.lubosgarancovsky.springutilities.dto.ErrorDetailResponse;
import static io.github.lubosgarancovsky.springutilities.error.BaseApiErrorCode.BAD_JSON_FORMAT;
import static io.github.lubosgarancovsky.springutilities.error.ServiceErrorCode.UNKNOWN_SERVICE_ERROR;

public abstract class AbstractServiceAdviceController {

    private static final Logger log = LoggerFactory.getLogger(AbstractServiceAdviceController.class);

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorDetailResponse> handle(JsonParseException e) {
        log.error("Handle {}", e.getClass().getName(), e);
        return mapErrorToResponse(BAD_JSON_FORMAT.createError());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDetailResponse> handle(BusinessException e) {
        log.error("Handle {}", e.getClass().getName(), e);
        return mapErrorToResponse(e.getError());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailResponse> handle(Exception e) {
        log.error("Handle {}", e.getClass().getName(), e);
        return mapErrorToResponse(UNKNOWN_SERVICE_ERROR.createError());
    }

    public abstract ResponseEntity<ErrorDetailResponse> mapErrorToResponse(
            BusinessError businessError);
}
