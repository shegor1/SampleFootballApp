package com.shegor.samplefootballapp.footballLeagues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shegor.samplefootballapp.databinding.LeagueItemBinding
import com.shegor.samplefootballapp.models.League

class LeaguesListAdapter(private val clickListener: LeagueClickListener) :
    ListAdapter<League, LeaguesListAdapter.LeagueItemViewHolder>(LeaguesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueItemViewHolder {
        return LeagueItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LeagueItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class LeagueItemViewHolder private constructor(val binding: LeagueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: League, clickListener: LeagueClickListener) {
            binding.league = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LeagueItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeagueItemBinding.inflate(layoutInflater, parent, false)
                return LeagueItemViewHolder(binding)
            }
        }
    }
}

class LeaguesDiffCallback : DiffUtil.ItemCallback<League>() {
    override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem.leagueId == newItem.leagueId
    }

    override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem == newItem
    }
}

class LeagueClickListener(
    val leagueClickListener: (league: League) -> Unit
) {
    fun onClick(league: League) = leagueClickListener(league)
}