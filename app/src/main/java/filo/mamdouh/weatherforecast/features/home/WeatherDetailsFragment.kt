package filo.mamdouh.weatherforecast.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding
import filo.mamdouh.weatherforecast.features.home.rvadapters.BaseRecyclerViewAdapter


class WeatherDetailsFragment : Fragment() {
    lateinit var binding: FragmentWeatherDetailsBinding
    lateinit var viewModel: WeatherDetailsViewModel
    lateinit var adapterrv : BaseRecyclerViewAdapter
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
        val viewModel = ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)
        adapterrv = BaseRecyclerViewAdapter()
        viewModel.weatherForecast.observe(viewLifecycleOwner){
            adapterrv.setForecastItems(it.list, it.city.timezone)
        }
        viewModel.currentWeather.observe(viewLifecycleOwner){
            adapterrv.setCurrentWeather(it)
        }
        binding.bottomSheet.bottomSheetRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterrv
        }
    }
}