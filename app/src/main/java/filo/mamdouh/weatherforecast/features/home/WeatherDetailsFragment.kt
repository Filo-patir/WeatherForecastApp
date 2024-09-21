package filo.mamdouh.weatherforecast.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding
import filo.mamdouh.weatherforecast.features.home.rvadapters.BaseRecyclerViewAdapter


class WeatherDetailsFragment : Fragment() {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var adapterrv : BaseRecyclerViewAdapter
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
        val viewModel = ViewModelProvider(this)[WeatherDetailsViewModel::class.java]
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
        binding.locationImg.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_searchFragment)
        }
    }
}