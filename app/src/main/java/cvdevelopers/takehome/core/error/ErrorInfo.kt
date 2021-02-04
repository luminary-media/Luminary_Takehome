package cvdevelopers.takehome.core.error

import cvdevelopers.takehome.core.exception.ErrorInfoException

data class ErrorInfo(val code: ErrorCode,
                     val reason: ErrorReason,
                     val extra: Any? = null)

fun unknownError(): ErrorInfoException {
    return ErrorInfoException(ErrorCode.UNKNOWN_ERROR, ErrorReason.NONE)
}

fun httpError(
        code: Int,
        message: String?
): ErrorInfoException {
    return ErrorInfoException(ErrorCode.HTTP_ERROR, ErrorReason.NONE, code to message)
}
