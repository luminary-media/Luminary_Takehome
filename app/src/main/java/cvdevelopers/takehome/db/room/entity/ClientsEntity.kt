package cvdevelopers.takehome.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cvdevelopers.takehome.db.DatabaseContract

@Entity(tableName = DatabaseContract.Table.Clients.NAME)
class ClientsEntity(
        @PrimaryKey(autoGenerate = true) var _id: Int = 0,
        @ColumnInfo(name = DatabaseContract.Table.Clients.Column.FIRST_NAME) var firstName: String? = null,
        @ColumnInfo(name = DatabaseContract.Table.Clients.Column.LAST_NAME) var lastName: String? = null,
        @ColumnInfo(name = DatabaseContract.Table.Clients.Column.THUMBNAIL) var thumbnail: String? = null,
)
