package com.shegor.samplefootballapp.footballLiveScoresWebView

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.shegor.samplefootballapp.App
import com.shegor.samplefootballapp.R
import com.shegor.samplefootballapp.base.BaseFragment
import com.shegor.samplefootballapp.base.BaseViewModelFactory
import com.shegor.samplefootballapp.databinding.FragmentFootballLiveScoresWebViewBinding


class FootballLiveScoresWebViewFragment :
    BaseFragment<FootballLiveScoresViewModel, FragmentFootballLiveScoresWebViewBinding>() {

    companion object {
        const val FOOTBALL_LIVE_SCORES_URL = "https://www.livescore.com/en/"
        const val HAS_SUBSCRIBES_PREFS_KEY = "hasSubscribed"
    }

    override val layoutId = R.layout.fragment_football_live_scores_web_view
    override fun getViewModel() = FootballLiveScoresViewModel::class.java

    override fun getViewModelFactory() =
        BaseViewModelFactory(FootballLiveScoresViewModel::class.java) {
            FootballLiveScoresViewModel()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
        setUpOneSignal()
    }

    private fun setupWebView() {
        binding.liveScoresWebView.apply {

            webViewClient = WebViewClient()
            loadUrl(FOOTBALL_LIVE_SCORES_URL)
            settings.setSupportZoom(true)

            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (canGoBack())
                            goBack()
                        else {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                })
        }
    }

    private fun setUpOneSignal() {
        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (prefs.getBoolean(HAS_SUBSCRIBES_PREFS_KEY, false)) return

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(getString(R.string.oneSignalDialogRequest))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                App.instance.setupOneSignal()
                with(prefs.edit()) {
                    putBoolean(HAS_SUBSCRIBES_PREFS_KEY, true)
                    apply()
                }
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        dialogBuilder.create().show()
    }
}