package cvdevelopers.takehome.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Name(
        @SerializedName("first")
        @Expose
        var first: String? = null,

        @SerializedName("last")
        @Expose
        var last: String? = null,

        @SerializedName("title")
        @Expose
        var title: String? = null
)
