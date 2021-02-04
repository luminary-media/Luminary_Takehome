package cvdevelopers.takehome.conn.factory.retrofit

import cvdevelopers.takehome.conn.api.ClientsApi
import cvdevelopers.takehome.delegate.IURLStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ClientsApiFactory : IClientsApiFactory {

    override fun getClientsApi(client: OkHttpClient,
                               urlStore: IURLStore): ClientsApi {

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(urlStore.getClientApiBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ClientsApi::class.java)
    }
}
