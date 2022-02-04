package com.shegor.samplefootballapp.footballMatches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shegor.samplefootballapp.databinding.MatchItemBinding
import com.shegor.samplefootballapp.models.MatchModel

class MatchesListAdapter :
    ListAdapter<MatchModel, MatchesListAdapter.MatchesItemViewHolder>(MatchesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesItemViewHolder {
        return MatchesItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MatchesItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MatchesItemViewHolder private constructor(private val binding: MatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchModel) {
            binding.match = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MatchesItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MatchItemBinding.inflate(layoutInflater, parent, false)
                return MatchesItemViewHolder(binding)
            }
        }
    }
}

class MatchesDiffCallback : DiffUtil.ItemCallback<MatchModel>() {
    override fun areItemsTheSame(oldItem: MatchModel, newItem: MatchModel): Boolean {
        return oldItem.matchId == newItem.matchId
    }

    override fun areContentsTheSame(oldItem: MatchModel, newItem: MatchModel): Boolean {
        return oldItem == newItem
    }
}

