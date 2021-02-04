package cvdevelopers.takehome.conn.factory.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient

abstract class IHTTPClientFactory {
    private lateinit var coreClient: OkHttpClient

    @Synchronized
    protected fun getCoreClient(): OkHttpClient {
        if (!::coreClient.isInitialized) {
            coreClient = OkHttpClient()
        }

        return coreClient
    }

    protected fun setupInterceptors(
            base: OkHttpClient,
            interceptors: List<Interceptor>?
    ): OkHttpClient {
        if (interceptors.isNullOrEmpty()) {
            return base
        }

        val builder = base.newBuilder()
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    abstract fun getHTTPClient(
            base: OkHttpClient = getCoreClient(),
            connectTimeout: Long = 60L,
            readTimeout: Long = 60L,
            writeTimeout: Long = 60L,
            interceptors: List<Interceptor>? = null
    ): OkHttpClient
}
