package org.d3if1135.ngampusrek.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if1135.ngampusrek.model.Kampus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "fahrial14/test/mobpro/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KampusApiService {
    @GET("static-api.json")
    suspend fun getKampus(): List<Kampus>
}

object KampusApi {
    val service: KampusApiService by lazy {
        retrofit.create(KampusApiService::class.java)
    }

    fun getKampusUrl(nama: String): String {
        return "$BASE_URL$nama.jpg"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
