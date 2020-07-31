package cvdevelopers.takehome.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEntity(
        @PrimaryKey val lastName: String,
        @ColumnInfo(name = "json") val json: String
)