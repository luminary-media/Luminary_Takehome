package cvdevelopers.takehome.rx.observable

import android.util.Log
import cvdevelopers.takehome.common.Constants.LOG_TAG
import cvdevelopers.takehome.conn.factory.okhttp.IHTTPClientFactory
import cvdevelopers.takehome.conn.factory.retrofit.IClientsApiFactory
import cvdevelopers.takehome.core.error.httpError
import cvdevelopers.takehome.core.error.unknownError
import cvdevelopers.takehome.delegate.IURLStore
import cvdevelopers.takehome.model.users.ApiResponse
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import org.koin.java.KoinJavaComponent.get
import retrofit2.Call
import java.lang.Exception

class GetClientsNetwork(
        private val clientsApi: IClientsApiFactory = get(IClientsApiFactory::class.java),
        private val urlStore: IURLStore = get(IURLStore::class.java),
        private val httpClientFactory: IHTTPClientFactory = get(IHTTPClientFactory::class.java)
) : SingleOnSubscribe<ApiResponse> {
    private val className = javaClass.simpleName

    @get:Synchronized
    @set:Synchronized
    var call: Call<ApiResponse>? = null

    override fun subscribe(emitter: SingleEmitter<ApiResponse>) {
        emitter.setCancellable {
            call?.cancel()
        }

        try {
            executeOrThrow(emitter)
        } catch (e: Exception) {
            Log.d(LOG_TAG, "[$className] Exception: ${e.message}")
            emitter.onError(e)
        }
    }

    private fun executeOrThrow(emitter: SingleEmitter<ApiResponse>) {
        val api = clientsApi.getClientsApi(httpClientFactory.getHTTPClient(), urlStore)
        call = api.getClients(mutableMapOf("page" to "1", "results" to "30"))
        val response = call?.execute() ?: throw unknownError()

        val responseCode = response.code()
        val responseMessage = response.message()
        Log.d(LOG_TAG, "[$className] $responseCode, ${responseMessage ?: "null"}")

        if (responseCode != 200) {
            throw httpError(responseCode, responseMessage)
        }

        val result = response.body() ?: throw unknownError()
        Log.d(LOG_TAG, "[$className] result: $result")
        emitter.onSuccess(result)
    }
}
