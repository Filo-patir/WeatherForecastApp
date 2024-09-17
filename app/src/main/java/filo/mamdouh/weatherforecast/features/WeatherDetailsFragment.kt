package filo.mamdouh.weatherforecast.features

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding


class WeatherDetailsFragment : Fragment() {
    lateinit var binding: FragmentWeatherDetailsBinding
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.standardBottomSheet)
            if (BottomSheetBehavior.STATE_EXPANDED == bottomSheet.state)
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            else {
                this.remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}