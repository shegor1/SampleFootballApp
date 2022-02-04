package com.shegor.samplefootballapp.repository

import androidx.lifecycle.LiveData
import com.shegor.samplefootballapp.base.BaseRepository
import com.shegor.samplefootballapp.models.League
import com.shegor.samplefootballapp.models.MatchModel
import com.shegor.samplefootballapp.persistantStorage.FootballDao
import com.shegor.samplefootballapp.service.FootballApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FootballRepo(
    private val apiService: FootballApiService,
    private val footballDatabase: FootballDao
) : BaseRepository() {

    companion object {
        private const val networkErrorMessage = "Fetching error"
    }

    val leagues: LiveData<List<League>> = footballDatabase.getAllLeagues()

    suspend fun refreshLeagues() {
        val leaguesList = apiCall({ apiService.getAllLeagues() }, networkErrorMessage)
        withContext(Dispatchers.IO) {
            saveLeagues(leaguesList)
        }
    }

    private fun saveLeagues(leagues: List<League>?) =
        leagues?.let {
            for (league in it)
                footballDatabase.insertLeague(league)
        }

    suspend fun getLeagueMatches(
        leagueId: Int,
        from: String,
        to: String,
        timezone: String
    ): List<MatchModel>? {
        return apiCall(
            { apiService.getLeagueMatches(leagueId, from, to, timezone) },
            networkErrorMessage
        )
    }
}



