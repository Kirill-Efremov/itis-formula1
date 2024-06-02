package com.kiril.raceapp.ui.standings.driver

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.kiril.raceapp.R
import com.kiril.raceapp.ui.util.ParamsKey
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DriverDetailActivity : AppCompatActivity() {
    private val raceViewModel: DriverDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drive_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.extras?.getString(ParamsKey.NAME_KEY) ?: ""
        println(name)

        val raceTopBarTextView: MaterialToolbar = findViewById(R.id.driver_detail_top_bar)

        raceTopBarTextView.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val driverNameTextView: TextView = findViewById(R.id.detail_driver_name)
        val driverDTBTextView: TextView = findViewById(R.id.detail_driver_dtb)
        val driverNationalityTextView: TextView = findViewById(R.id.detail_driver_nationality)
        val driverPermanentTextView: TextView = findViewById(R.id.detail_driver_permant)
        raceViewModel.raceInfo.observe(this) { driver ->
            val mDriver = driver.mRData.driverTable.drivers.firstOrNull()
            driverNameTextView.text ="${mDriver?.givenName} " + " ${mDriver?.familyName}"
            driverDTBTextView.text = mDriver?.dateOfBirth
            driverNationalityTextView.text = mDriver?.nationality
            driverPermanentTextView.text = mDriver?.permanentNumber
            println("Driver info: ${mDriver?.givenName} ${mDriver?.familyName}, ${mDriver?.dateOfBirth}, ${mDriver?.nationality}, ${mDriver?.permanentNumber}")

        }

        raceViewModel.fetchRaceDetails(name)
    }
}