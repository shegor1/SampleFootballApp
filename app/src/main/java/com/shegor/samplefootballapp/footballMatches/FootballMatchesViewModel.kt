package com.shegor.samplefootballapp.footballMatches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegor.samplefootballapp.models.MatchModel
import com.shegor.samplefootballapp.repository.FootballRepo
import com.shegor.samplefootballapp.utils.DateUtils
import kotlinx.coroutines.launch

class FootballMatchesViewModel(private val repo: FootballRepo) : ViewModel() {

    enum class LoadingStatus { LOADING, DONE, ERROR, NO_DATA }

    private val _matches = MutableLiveData<List<MatchModel>>()
    val matches: LiveData<List<MatchModel>>
        get() = _matches

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    fun getMatches(leagueId: Int, from: String, to: String, timeZone: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            _matches.value =
                repo.getLeagueMatches(leagueId, from, to, timeZone)?.sortedByDescending {
                    DateUtils.apiDateStringToLocalDate(it.matchDate)
                }.also { setLoadingStatus(it) }
        }
    }

    private fun setLoadingStatus(matches: List<MatchModel>?) {
        when {
            matches == null -> _loadingStatus.value = LoadingStatus.ERROR
            matches.isEmpty() -> _loadingStatus.value = LoadingStatus.NO_DATA
            else -> _loadingStatus.value = LoadingStatus.DONE
        }
    }
}