package cvdevelopers.takehome.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Client(
        @SerializedName("id")
        @Expose
        var id: Id? = null,

        @SerializedName("email")
        @Expose
        var email: String? = null,

        @SerializedName("name")
        @Expose
        var name: Name? = null,

        @SerializedName("picture")
        @Expose
        var picture: Picture? = null
)
