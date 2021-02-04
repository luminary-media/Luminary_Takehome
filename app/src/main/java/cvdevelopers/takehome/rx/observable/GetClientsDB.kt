package cvdevelopers.takehome.rx.observable

import android.util.Log
import cvdevelopers.takehome.common.Constants.LOG_TAG
import cvdevelopers.takehome.db.room.AppDatabase
import cvdevelopers.takehome.model.users.Client
import cvdevelopers.takehome.model.users.Name
import cvdevelopers.takehome.model.users.Picture
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import org.koin.java.KoinJavaComponent.get
import java.lang.Exception

class GetClientsDB(
        private val appDB: AppDatabase = get(AppDatabase::class.java)
) : SingleOnSubscribe<List<Client>> {
    private val className = javaClass.simpleName

    override fun subscribe(emitter: SingleEmitter<List<Client>>) {
        try {
            executeOrThrow(emitter)
        } catch (e: Exception) {
            Log.d(LOG_TAG, "[$className] Exception: ${e.message}")
            emitter.onError(e)
        }
    }

    private fun executeOrThrow(emitter: SingleEmitter<List<Client>>) {
        val entities = appDB.clientsDAO().readAll()
        if (entities.isNullOrEmpty()) {
            emitter.onSuccess(listOf())
            return
        }

        val clients = mutableListOf<Client>()
        entities.forEach {
            val client = Client()
                    .apply {
                        name = Name(first = it.firstName, last = it.lastName)
                        picture = Picture(thumbnail = it.thumbnail)
                    }

            clients.add(client)
        }

        emitter.onSuccess(clients)
    }
}
