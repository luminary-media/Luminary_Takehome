package cvdevelopers.takehome.api

import cvdevelopers.takehome.models.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApiEndpoint {

  @GET("/api/")
  suspend fun getClient(@Query("page") page: String, @Query("results") results: String = "15"): ApiResponse

  companion object {
    const val SERVER = "https://randomuser.me"
  }
}