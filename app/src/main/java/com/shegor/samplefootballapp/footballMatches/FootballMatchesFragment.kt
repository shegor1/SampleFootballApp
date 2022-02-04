package com.shegor.samplefootballapp.footballMatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shegor.samplefootballapp.R
import com.shegor.samplefootballapp.base.BaseRecyclerViewFragment
import com.shegor.samplefootballapp.base.BaseViewModelFactory
import com.shegor.samplefootballapp.databinding.FragmentFootballMatchesBinding
import com.shegor.samplefootballapp.footballDb
import com.shegor.samplefootballapp.repository.FootballRepo
import com.shegor.samplefootballapp.service.FootballApi
import com.shegor.samplefootballapp.utils.DateUtils
import java.util.*

class FootballMatchesFragment :
    BaseRecyclerViewFragment<FootballMatchesViewModel, FragmentFootballMatchesBinding, MatchesListAdapter>() {

    private lateinit var args: FootballMatchesFragmentArgs

    override val layoutId = R.layout.fragment_football_matches

    override fun getViewModel() = FootballMatchesViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory =
        BaseViewModelFactory(FootballMatchesViewModel::class.java) {
            FootballMatchesViewModel(
                FootballRepo(FootballApi.footballRetrofitService, footballDb.footballDao)
            )
        }

    override fun getRvAdapter() = MatchesListAdapter()

    override fun getRecyclerView() = binding.matchesRecyclerView

    override fun getRvLayoutManager() = LinearLayoutManager(context)

    override fun connectDataBinding() {
        super.connectDataBinding()
        binding.viewModel = viewModel
        binding.league = args.league
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getArgs()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        getRecentMatches()
    }

    private fun getArgs() {
        args = FootballMatchesFragmentArgs.fromBundle(requireArguments())
    }

    private fun setObservers() {

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            displayLoadingStatus(it)
        }

        viewModel.matches.observe(viewLifecycleOwner) {
            listAdapter?.submitList(it)
        }
    }

    private fun getRecentMatches() {
        viewModel.getMatches(
            args.league.leagueId,
            DateUtils.milliToFormattedDateString(System.currentTimeMillis() - WEEK_IN_MILLI),
            DateUtils.milliToFormattedDateString(System.currentTimeMillis()),
            TimeZone.getDefault().id
        )
    }

    private fun displayLoadingStatus(status: FootballMatchesViewModel.LoadingStatus) {
        when (status) {
            FootballMatchesViewModel.LoadingStatus.LOADING -> {
                binding.loadingStatusImageView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            FootballMatchesViewModel.LoadingStatus.DONE -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingStatusImageView.visibility = View.GONE
            }
            FootballMatchesViewModel.LoadingStatus.NO_DATA -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingStatusImageView.visibility = View.VISIBLE
                binding.loadingStatusImageView.setImageResource(R.drawable.no_results_found)
            }
            FootballMatchesViewModel.LoadingStatus.ERROR -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingStatusImageView.visibility = View.VISIBLE
                binding.loadingStatusImageView.setImageResource(R.drawable.ic_internet_error)
            }
        }
    }

    companion object {
        val WEEK_IN_MILLI = 604800000
    }
}