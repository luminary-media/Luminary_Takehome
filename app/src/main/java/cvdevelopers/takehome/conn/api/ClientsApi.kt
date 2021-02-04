package cvdevelopers.takehome.conn.api

import cvdevelopers.takehome.model.users.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ClientsApi {

    @GET("/api")
    fun getClients(
            @QueryMap params: Map<String, String>
    ): Call<ApiResponse>
}
