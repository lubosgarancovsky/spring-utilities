package io.github.lubosgarancovsky.springutilities.error;

public enum ErrorCodeType {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    CONFLICT(409),
    UNPROCESSABLE_ENTITY(422),
    LOCKED(423),
    INTERNAL(500);

    private int numCode;

    ErrorCodeType(int numCode) {
        this.numCode = numCode;
    }

    /**
     * Return error code number for API mapping.
     *
     * @return number of error.
     */
    public int getNumCode() {
        return numCode;
    }
}
