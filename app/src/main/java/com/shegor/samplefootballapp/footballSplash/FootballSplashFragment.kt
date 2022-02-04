package com.shegor.samplefootballapp.footballSplash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.shegor.samplefootballapp.R
import com.shegor.samplefootballapp.base.BaseFragment
import com.shegor.samplefootballapp.base.BaseViewModelFactory
import com.shegor.samplefootballapp.databinding.FragmentFootballSplashBinding
import com.shegor.samplefootballapp.footballDb
import com.shegor.samplefootballapp.repository.FootballRepo
import com.shegor.samplefootballapp.service.FootballApi

class FootballSplashFragment :
    BaseFragment<FootballSplashViewModel, FragmentFootballSplashBinding>() {

    override val layoutId = R.layout.fragment_football_splash

    override fun getViewModel(): Class<FootballSplashViewModel> =
        FootballSplashViewModel::class.java

    override fun getViewModelFactory(): BaseViewModelFactory<FootballSplashViewModel> =
        BaseViewModelFactory(FootballSplashViewModel::class.java) {
            FootballSplashViewModel(
                FootballRepo(FootballApi.footballRetrofitService, footballDb.footballDao)
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preloadData()
        setObservers()
    }

    private fun preloadData() {
        viewModel.preloadCountriesAndLeaguesData()
    }

    private fun setObservers() {
        viewModel.navigationToLeaguesFragment.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    FootballSplashFragmentDirections.actionFootballSplashFragmentToFootballLeaguesFragment()
                )
                viewModel.finishNavigationToLeaguesFragment()
            }
        }
    }
}