package cvdevelopers.takehome.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRetrofit {

    companion object {
        var userApiEndpoint: RandomUserApiEndpoint = Retrofit.Builder()
                .baseUrl(RandomUserApiEndpoint.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RandomUserApiEndpoint::class.java)
    }
}