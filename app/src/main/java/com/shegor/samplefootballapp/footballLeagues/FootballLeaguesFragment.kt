package com.shegor.samplefootballapp.footballLeagues

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.shegor.samplefootballapp.R
import com.shegor.samplefootballapp.base.BaseRecyclerViewFragment
import com.shegor.samplefootballapp.base.BaseViewModelFactory
import com.shegor.samplefootballapp.databinding.FragmentFootballLeaguesBinding
import com.shegor.samplefootballapp.footballDb
import com.shegor.samplefootballapp.models.League
import com.shegor.samplefootballapp.repository.FootballRepo
import com.shegor.samplefootballapp.service.FootballApi

class FootballLeaguesFragment :
    BaseRecyclerViewFragment<FootballLeaguesViewModel, FragmentFootballLeaguesBinding, LeaguesListAdapter>() {

    override val layoutId = R.layout.fragment_football_leagues

    override fun getViewModel() = FootballLeaguesViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory =
        BaseViewModelFactory(FootballLeaguesViewModel::class.java) {
            FootballLeaguesViewModel(
                FootballRepo(FootballApi.footballRetrofitService, footballDb.footballDao)
            )
        }

    override fun getRvAdapter() = LeaguesListAdapter(LeagueClickListener {
        viewModel.navigateToMatches(it)
    })

    override fun getRecyclerView() = binding.leagueRecyclerView

    override fun getRvLayoutManager() = GridLayoutManager(context, 2)

    override fun connectDataBinding() {
        super.connectDataBinding()
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {

        viewModel.leagues.observe(viewLifecycleOwner) {
            listAdapter?.submitList(it)
            setupStatusImageView(it)


            viewModel.navigationToMatchesFragment.observe(viewLifecycleOwner) {
                it?.let {
                    findNavController().navigate(
                        FootballLeaguesFragmentDirections.actionFootballLeaguesFragmentToFootballMatchesFragment(
                            it
                        )
                    )
                    viewModel.finishNavigationMatches()
                }
            }

            viewModel.navigationToLiveScoresWebViewFragment.observe(viewLifecycleOwner) {
                it?.let {
                    findNavController().navigate(
                        FootballLeaguesFragmentDirections.actionFootballLeaguesFragmentToFootballLiveScoresWebViewFragment()
                    )
                    viewModel.finishNavigationToLiveScoresWebView()
                }
            }
        }
    }

    private fun setupStatusImageView(leagues: List<League>) {
        if (leagues.isEmpty()) {
            binding.loadingStatusImageView.visibility = View.VISIBLE
            binding.loadingStatusImageView.setImageResource(R.drawable.ic_internet_error)
        }
        binding.progressBar.visibility = View.GONE
    }
}