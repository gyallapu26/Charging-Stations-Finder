package com.example.chargingstationsfinder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chargingstationsfinder.databinding.ActivityChargingStationsFinderBinding
import com.example.chargingstationsfinder.domain.Station
import com.example.chargingstationsfinder.domain.StationImpl
import com.example.chargingstationsfinder.helper.isNetworkAvailable
import com.example.chargingstationsfinder.ui.StationDetailsDialog
import com.example.chargingstationsfinder.ui.StationsFinderViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChargingStationsFinderActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityChargingStationsFinderBinding
    private val viewModel: StationsFinderViewModel by viewModels()
    private var stations: MutableList<Station> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChargingStationsFinderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.fetchStations()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener { marker ->
            stations.find { it.id == marker.tag }?.let {
                goToDetailsScreen(it)
            }
            false
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    stations = uiState.stations.toMutableList()
                    uiState.stations.forEach {
                        mMap.addMarker(it.toMarkerOptions())?.apply { showInfoWindow() }
                            ?.apply { tag = it.id }
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLng(
                                LatLng(
                                    it.addressInfo.latitude,
                                    it.addressInfo.longitude
                                )
                            )
                        )
                    }
                    if (uiState.errorMessage.isNotEmpty()) {
                        Snackbar.make(
                            binding.root,
                            uiState.errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun goToDetailsScreen(station: Station) {
        supportFragmentManager.let {
            val dialog =
                (it.findFragmentByTag(StationDetailsDialog.TAG) as? StationDetailsDialog)
                    ?: StationDetailsDialog.getInstance(station as StationImpl)
            if (dialog.isAdded.not()) {
                dialog.show(it, StationDetailsDialog.TAG)
            }
        }
    }
}

internal fun Station.toMarkerOptions(): MarkerOptions {
    return MarkerOptions().position(LatLng(this.addressInfo.latitude, this.addressInfo.longitude))
        .title(this.addressInfo.title)
}