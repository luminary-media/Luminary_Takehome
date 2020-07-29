package cvdevelopers.takehome.models

data class Client(
        val email: String,
        val id: Id,
        val gender: String,
        val name: Name,
        val picture: Picture
)