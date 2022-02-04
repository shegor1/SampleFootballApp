package com.shegor.samplefootballapp.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shegor.samplefootballapp.models.League
import com.shegor.samplefootballapp.models.MatchModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://apiv3.apifootball.com/"
private const val API_KEY = "548ccf78ee970f3399699a57b6748670bf91dfda3ca79d821344993a7f02edb3"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface FootballApiService {

    @GET(".")
    suspend fun getAllLeagues(
        @Query("action") action: String = "get_leagues",
        @Query("APIkey") apiKey: String = API_KEY
    ): Response<List<League>>

    @GET(".")
    suspend fun getLeagueMatches(
        @Query("league_id") leagueId: Int,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("timezone") timezone: String,
        @Query("action") action: String = "get_events",
        @Query("APIkey") apiKey: String = API_KEY
    ): Response<List<MatchModel>>
}

object FootballApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val footballRetrofitService: FootballApiService by lazy {
        retrofit.create(FootballApiService::class.java)
    }
}
