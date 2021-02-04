package cvdevelopers.takehome.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("results")
        @Expose
        var results: List<Client>? = null
)
