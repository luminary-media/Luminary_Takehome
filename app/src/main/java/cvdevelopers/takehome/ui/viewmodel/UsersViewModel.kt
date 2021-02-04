package cvdevelopers.takehome.ui.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import cvdevelopers.takehome.app.App
import cvdevelopers.takehome.common.Constants
import cvdevelopers.takehome.core.exception.ErrorInfoException
import cvdevelopers.takehome.db.room.AppDatabase
import cvdevelopers.takehome.db.room.entity.ClientsEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import cvdevelopers.takehome.model.result.Result
import cvdevelopers.takehome.model.users.Client
import cvdevelopers.takehome.rx.observable.GetClientsDB
import cvdevelopers.takehome.rx.observable.GetClientsNetwork
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class UsersViewModel : AndroidViewModel(App.INSTANCE) {
    private val className = javaClass.simpleName

    private val compositeDisposable = CompositeDisposable()
    private val requestClientsSubject = PublishSubject.create<Result<List<Client>>>()
    private val clientsMemoryCache = mutableListOf<Client>()

    private val appDB by inject(AppDatabase::class.java)

    fun getClientsNetwork(): Disposable {
        val disposable = Single.create(GetClientsNetwork())
                .map { Result(it.results) }
                .doOnSuccess { cacheClients(it) }
                .onErrorReturn { Result(null, ErrorInfoException.from(it).errorInfo) }
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {
                    requestClientsSubject.onNext(it)
                })

        compositeDisposable.add(disposable)
        return disposable
    }

    private fun cacheClients(result: Result<List<Client>>) {
        Log.d(Constants.LOG_TAG, "[$className] cache clients")

        val clients = result.data
        if (clients.isNullOrEmpty()) {
            Log.d(Constants.LOG_TAG, "[$className] no clients to cache")
            return
        }

        Log.d(Constants.LOG_TAG, "[$className] clients count: ${clients.size}")

        clientsMemoryCache.clear()
        appDB.clientsDAO().deleteAll()

        val clientEntries = mutableListOf<ClientsEntity>()
        clients.forEach {
            clientsMemoryCache.add(it.copy())

            clientEntries.add(ClientsEntity()
                    .apply {
                        firstName = it.name?.first
                        lastName = it.name?.last
                        thumbnail = it.picture?.thumbnail
                    })
        }

        appDB.clientsDAO().insert(clientEntries)
    }

    fun observeClients(): PublishSubject<Result<List<Client>>> {
        return requestClientsSubject
    }

    fun loadClients() {
        if (clientsMemoryCache.isNotEmpty()) {
            requestClientsSubject.onNext(Result(clientsMemoryCache))
            return
        }

        if (appDB.clientsDAO().isNotEmpty()) {
            compositeDisposable.add(Single.create(GetClientsDB())
                    .subscribeOn(Schedulers.io())
                    .subscribe(Consumer {
                        requestClientsSubject.onNext(Result(it))
                    }))
            return
        }

        getClientsNetwork()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
