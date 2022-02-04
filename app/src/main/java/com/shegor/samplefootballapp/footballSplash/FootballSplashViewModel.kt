package com.shegor.samplefootballapp.footballSplash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegor.samplefootballapp.repository.FootballRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FootballSplashViewModel(val repo: FootballRepo) : ViewModel() {

    private val _navigationToLeaguesFragment = MutableLiveData<Boolean?>()
    val navigationToLeaguesFragment: LiveData<Boolean?>
        get() = _navigationToLeaguesFragment

    fun preloadCountriesAndLeaguesData() {
        viewModelScope.launch {

            repo.refreshLeagues()

            withContext(Dispatchers.IO) {
                delay(300)
            }
            _navigationToLeaguesFragment.value = true
        }
    }

    fun finishNavigationToLeaguesFragment() {
        _navigationToLeaguesFragment.value = null
    }

}