package cvdevelopers.takehome.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseEntity::class], version = 1, exportSchema = false)
abstract class ClientDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDictionaryDao

    companion object {
        private const val mDatabaseName = "client"

        @Volatile
        private var INSTANCE: ClientDatabase? = null

        fun getDataBase(context: Context): ClientDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ClientDatabase::class.java,
            mDatabaseName
        ).build()
    }
}