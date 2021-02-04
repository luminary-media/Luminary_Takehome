package cvdevelopers.takehome.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cvdevelopers.takehome.db.room.dao.ClientsDAO
import cvdevelopers.takehome.db.room.entity.ClientsEntity

@Database(
        entities = [ClientsEntity::class],
        version = AppDatabase.VERSION,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientsDAO(): ClientsDAO

    companion object {
        const val NAME = "main.db"
        const val VERSION = 1
    }
}
