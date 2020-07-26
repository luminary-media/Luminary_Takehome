package cvdevelopers.takehome.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClientDictionaryDao {
    @Query("SELECT * FROM DatabaseEntity")
    suspend fun getClientsByName(): List<DatabaseEntity>

    @Query("DELETE FROM DatabaseEntity")
    suspend fun clearDatabase()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWordDefinition(vararg databaseEntity: DatabaseEntity)
}