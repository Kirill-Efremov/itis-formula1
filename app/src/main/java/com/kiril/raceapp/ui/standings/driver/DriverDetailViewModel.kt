package com.kiril.raceapp.ui.standings.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiril.raceapp.data.driver.model.DriverDetail
import com.kiril.raceapp.data.driver.repository.DriverRepository
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverDetailViewModel @Inject constructor(
    private val raceRepository: DriverRepository
) : ViewModel() {

    private val _raceInfo = MutableLiveData<DriverDetail>()
    val raceInfo: LiveData<DriverDetail> get() = _raceInfo

    fun fetchRaceDetails(name: String) {
        viewModelScope.launch {
            val response = raceRepository.getDriverByName(name)
            response.onSuccess {
                _raceInfo.value = data
            }
        }
    }
}
