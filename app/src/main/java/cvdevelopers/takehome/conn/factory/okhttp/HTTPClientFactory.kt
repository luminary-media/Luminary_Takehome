package cvdevelopers.takehome.conn.factory.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HTTPClientFactory : IHTTPClientFactory() {

    override fun getHTTPClient(
            base: OkHttpClient,
            connectTimeout: Long,
            readTimeout: Long,
            writeTimeout: Long,
            interceptors: List<Interceptor>?
    ): OkHttpClient {
        var client = base.newBuilder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build()

        client = setupInterceptors(client, interceptors)
        return client
    }
}
