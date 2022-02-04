package com.shegor.samplefootballapp.footballLeagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shegor.samplefootballapp.models.League
import com.shegor.samplefootballapp.repository.FootballRepo

class FootballLeaguesViewModel(val repo: FootballRepo) : ViewModel() {

    val leagues = repo.leagues

    private val _navigationToLiveScoresWebViewFragment = MutableLiveData<Boolean?>()
    val navigationToLiveScoresWebViewFragment: LiveData<Boolean?>
        get() = _navigationToLiveScoresWebViewFragment

    private val _navigationToMatchesFragment = MutableLiveData<League?>()
    val navigationToMatchesFragment: LiveData<League?>
        get() = _navigationToMatchesFragment

    fun navigateToLiveScoresWebView() {
        _navigationToLiveScoresWebViewFragment.value = true
    }

    fun finishNavigationToLiveScoresWebView() {
        _navigationToLiveScoresWebViewFragment.value = null
    }

    fun navigateToMatches(league: League) {
        _navigationToMatchesFragment.value = league
    }

    fun finishNavigationMatches() {
        _navigationToMatchesFragment.value = null
    }
}