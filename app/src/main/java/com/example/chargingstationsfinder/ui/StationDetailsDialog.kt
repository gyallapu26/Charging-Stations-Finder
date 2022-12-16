package com.example.chargingstationsfinder.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.chargingstationsfinder.R
import com.example.chargingstationsfinder.databinding.FragmentStationDetailsBinding
import com.example.chargingstationsfinder.domain.Station
import com.example.chargingstationsfinder.domain.StationImpl

class StationDetailsDialog : DialogFragment() {

    private var _binding: FragmentStationDetailsBinding? = null
    private val binding get() = _binding!!
    private var station: Station? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentStationDetailsBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(false)
            .create()
        with(dialog.window) {
            this?.let {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }
        station = arguments?.getParcelable<StationImpl>(STATION_TAG)
        initClickListeners()
        renderUi()
        return dialog

    }

    private fun renderUi() {
        station?.let {
            with(binding) {
                titleTv.text = it.addressInfo.title
                addressTv.text = it.addressInfo.addressLine
                stationPointsCountTv.text = String.format(
                    getString(
                        R.string.available_charging_points,
                        it.chargingPointsCount
                    )
                )
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }

    private fun initClickListeners() {
        binding.okButton.setOnClickListener {
            dismiss()
        }
    }


    companion object {
        val TAG = StationDetailsDialog::class.java.canonicalName
        private val STATION_TAG = Station::class.java.canonicalName
        fun getInstance(station: StationImpl): StationDetailsDialog = StationDetailsDialog().apply {
            arguments = Bundle().apply {
                putParcelable(STATION_TAG, station)
            }
        }
    }

}