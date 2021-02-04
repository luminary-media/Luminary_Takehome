package cvdevelopers.takehome.koin

import androidx.room.Room
import com.squareup.picasso.Picasso
import cvdevelopers.takehome.app.App
import cvdevelopers.takehome.conn.factory.okhttp.IHTTPClientFactory
import cvdevelopers.takehome.conn.factory.okhttp.HTTPClientFactory
import cvdevelopers.takehome.conn.factory.retrofit.IClientsApiFactory
import cvdevelopers.takehome.conn.factory.retrofit.ClientsApiFactory
import cvdevelopers.takehome.db.room.AppDatabase
import cvdevelopers.takehome.delegate.IURLStore
import cvdevelopers.takehome.delegate.URLStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val koinModulesApp = module {
    single<IClientsApiFactory> { ClientsApiFactory() }
    single<IHTTPClientFactory> { HTTPClientFactory() }
    single<IURLStore> { URLStore() }
    single<Picasso> { Picasso.get() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
    }
}

fun initKoin() {
    startKoin {
        androidContext(App.INSTANCE)
        modules(listOf(koinModulesApp))
    }
}
