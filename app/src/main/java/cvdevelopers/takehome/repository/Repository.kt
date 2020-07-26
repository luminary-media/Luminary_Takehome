package cvdevelopers.takehome.repository

import android.util.Log
import cvdevelopers.takehome.database.ClientDatabase
import cvdevelopers.takehome.database.DatabaseEntity
import cvdevelopers.takehome.utils.listFromJson
import cvdevelopers.takehome.utils.listToJson
import cvdevelopers.takehome.api.RecipeRetrofit
import cvdevelopers.takehome.models.Client
import java.util.*

class Repository(private val clientDatabase: ClientDatabase) {
    private var isRetrofitRequestInProgress = false
    private var mDataset: List<Client> = emptyList()

    suspend fun clearDatabase() {
        clientDatabase.clientDao().clearDatabase()
    }

    suspend fun getSearchResults(page: String): List<Client> {
        try {
            Log.d("getting search::", "ma")
            mDataset = getClientsFromDatabase(page)
        } catch (e: Exception) {
            Log.e("ERROR GETTING DATA ", e.message ?: "")
            getClientsFromDatabase(page)
        }
        return mDataset
    }

    private suspend fun getClientsFromDatabase(page: String): List<Client> {
        val databaseData = clientDatabase.clientDao().getClientsByName()
        return if (databaseData.isNotEmpty()) {
            Log.d("get data from db::", "db is not empty")
            databaseData[0].run { listFromJson(this.json) }
        } else {
            Log.d("db is empty::", "db is empty")
            getDefinitionsFromServiceCall(page)
            insertUsersIntoDatabase()
            mDataset
        }
    }

    private suspend fun getDefinitionsFromServiceCall(page: String) {
        if (isRetrofitRequestInProgress) return
        else {
            isRetrofitRequestInProgress = true
            Log.d("fetch data from api::", "fetching data")
            mDataset = RecipeRetrofit.userApiEndpoint.getClient(page).results
            isRetrofitRequestInProgress = false
        }
    }

    private fun insertUsersIntoDatabase() {
        if (mDataset.isEmpty()) return
        else {
            val lastName = mDataset[0].name.last.toLowerCase(Locale.getDefault())
            val jsonStringData = listToJson(mDataset)
            Log.d("insert data to db::", "inserting users")
            clientDatabase.clientDao()
                    .insertWordDefinition(DatabaseEntity(lastName, jsonStringData))
        }
    }
}