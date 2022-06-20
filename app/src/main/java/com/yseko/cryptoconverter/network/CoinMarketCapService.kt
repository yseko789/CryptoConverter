package com.yseko.cryptoconverter.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



private const val BASE_URL = "https://pro-api.coinmarketcap.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CoinMarketCapService {
        @GET("v1/cryptocurrency/listings/latest")
        suspend fun getLatest(
            @Query("CMC_PRO_API_KEY") apikey: String,
//            @Query("limit") limit: Int
        ): ResponseLatest

        @GET("v2/cryptocurrency/info")
        suspend fun getInfo(
            @Query("CMC_PRO_API_KEY") apikey: String,
            @Query("id") id: String
        ): ResponseInfo

//        @GET("v2/cryptocurrency/quotes/latest")
//        suspend fun getPrice(
//            @Query("CMC_PRO_API_KEY") apikey: String,
//            @Query("id") id: String,
//            @Query("convert_id") convertId: String
//        ): ResponseQuote

//    @GET("v2/cryptocurrency/quotes/latest")
//    suspend fun getPrice(
//        @Query("CMC_PRO_API_KEY") apikey: String,
//        @Query("symbol") symbol: String,
//        @Query("convert") convert: String
//    ): ResponseQuote

        @GET("v2/tools/price-conversion")
        suspend fun getPrice(
            @Query("CMC_PRO_API_KEY") apikey: String,
            @Query("amount") amount: Double,
            @Query("symbol") symbol: String,
            @Query("convert") convert: String
        ): ResponseConversion
}

object CoinMarketCapApi{
    val retrofitService: CoinMarketCapService by lazy{
        retrofit.create(CoinMarketCapService::class.java)
    }
}
