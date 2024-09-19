package filo.mamdouh.weatherforecast.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast


class WeatherDetailsFragment : Fragment() {
    lateinit var binding: FragmentWeatherDetailsBinding

    override fun onResume() {
        super.onResume()
        Log.d("Filo", "onResume: ")
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

    fun setBottomSheetData(data: CurrentWeather){
        val bottomSheet = binding.bottomSheet
    }
    fun setBottomSheetData(data: WeatherForecast){

    }
}