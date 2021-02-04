package cvdevelopers.takehome.db

object DatabaseContract {

    object Table {

        object Clients {
            const val NAME = "clients"

            object Column {
                const val ID = "_id"
                const val FIRST_NAME = "first_name"
                const val LAST_NAME = "last_name"
                const val THUMBNAIL = "thumbnail"
            }
        }
    }
}
