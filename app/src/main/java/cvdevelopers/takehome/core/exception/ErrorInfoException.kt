package cvdevelopers.takehome.core.exception

import cvdevelopers.takehome.core.error.ErrorCode
import cvdevelopers.takehome.core.error.ErrorInfo
import cvdevelopers.takehome.core.error.ErrorReason

class ErrorInfoException(
        val errorInfo: ErrorInfo
) : RuntimeException() {

    constructor(
            code: ErrorCode,
            reason: ErrorReason
    ) : this(ErrorInfo(code, reason))

    constructor(
            code: ErrorCode,
            reason: ErrorReason,
            extra: Any? = null
    ) : this(ErrorInfo(code, reason, extra))

    companion object {
        fun from(t: Throwable): ErrorInfoException {
            return when (t) {
                is ErrorInfoException -> {
                    t
                }

                else -> {
                    ErrorInfoException(ErrorCode.EXCEPTION, ErrorReason.NONE, t)
                }
            }
        }
    }
}
