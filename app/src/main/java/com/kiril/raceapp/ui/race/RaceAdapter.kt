package com.kiril.raceapp.ui.race

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.trusted.sharing.ShareTarget.Params
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiril.raceapp.R
import com.kiril.raceapp.data.race.model.Race
import com.kiril.raceapp.ui.util.ParamsKey

class RaceAdapter : ListAdapter<Race, RaceAdapter.RaceViewHolder>(RaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val race = getItem(position)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RaceDetailActivity::class.java).apply {
                putExtra(ParamsKey.SEASON_KEY, race.season)
                putExtra(ParamsKey.ROUND_KEY, race.round)
                putExtra(ParamsKey.NAME_KEY, race.raceName)
            }
            context.startActivity(intent)
        }

        holder.bind(race)
    }

    class RaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val raceNameTextView: TextView = itemView.findViewById(R.id.race_name)
        private val raceDateTextView: TextView = itemView.findViewById(R.id.race_date)


        fun bind(race: Race) {
            raceNameTextView.text = race.raceName
            raceDateTextView.text = race.date
        }
    }

    class RaceDiffCallback : DiffUtil.ItemCallback<Race>() {
        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem.raceName == newItem.raceName
        }

        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem == newItem
        }
    }
}
