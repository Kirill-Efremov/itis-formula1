package com.kiril.raceapp.ui.race

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kiril.raceapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceDetailActivity : AppCompatActivity() {
    private val raceViewModel: RaceDetailViewModel by viewModels()
    private lateinit var raceRecyclerView: RecyclerView
    private lateinit var raceAdapter: RaceDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_race_detail)

        val season = intent.extras?.getString("season") ?: ""
        val round = intent.extras?.getString("round") ?: ""
        val name = intent.extras?.getString("name") ?: ""
        println(name)


        val raceNotFoundTextView: TextView = findViewById(R.id.not_found_text)
        val raceTopBarTextView: MaterialToolbar = findViewById(R.id.raceDetailTopAppBar)
        raceTopBarTextView.title = name

        raceTopBarTextView.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        raceRecyclerView = findViewById(R.id.drivers_recycler_view)
        raceRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        raceAdapter = RaceDetailAdapter()
        raceRecyclerView.adapter = raceAdapter

        raceViewModel.fetchRaceDetails(season, round)



        raceViewModel.raceInfo.observe(this) { races ->
            val raceDetails = races.mRData.raceTable.races
            if (raceDetails.isNotEmpty()) {
                raceAdapter.submitList(raceDetails[0].raceResults)
            } else {
                raceNotFoundTextView.visibility = VISIBLE
            }
        }

    }

}