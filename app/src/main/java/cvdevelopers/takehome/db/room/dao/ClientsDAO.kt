package cvdevelopers.takehome.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import cvdevelopers.takehome.db.room.entity.ClientsEntity

@Dao
abstract class ClientsDAO {

    @Insert(onConflict = REPLACE)
    abstract fun insert(clients: List<ClientsEntity>)

    @Query("SELECT * FROM clients")
    abstract fun readAll(): List<ClientsEntity>?

    @Query("DELETE FROM clients")
    abstract fun deleteAll(): Int

    @Query("SELECT _id FROM clients LIMIT 1")
    abstract fun isNotEmpty(): Boolean
}
