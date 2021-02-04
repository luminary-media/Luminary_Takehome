package cvdevelopers.takehome.conn.factory.retrofit

import cvdevelopers.takehome.conn.api.ClientsApi
import cvdevelopers.takehome.delegate.IURLStore
import okhttp3.OkHttpClient

interface IClientsApiFactory {
    fun getClientsApi(client: OkHttpClient, urlStore: IURLStore): ClientsApi
}
