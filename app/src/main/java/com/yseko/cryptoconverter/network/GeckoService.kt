package com.yseko.cryptoconverter.network


import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.coingecko.com/api/v3/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GeckoService {
    @GET("search")
    suspend fun getCoins(
        @Query("query") query: String
    ): ResponseCoins

    @GET("simple/price")
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies")vsCurrency: String
    ): Map<String, Price>


}

object GeckoApi{
    val retrofitService: GeckoService by lazy{
        retrofit.create(GeckoService::class.java)
    }
}