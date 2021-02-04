package cvdevelopers.takehome.model.result

import cvdevelopers.takehome.core.error.ErrorInfo

data class Result<Data>(
        val data: Data?,
        val error: ErrorInfo? = null
)
