package filo.mamdouh.weatherforecast.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.features.home.rvadapters.BaseRecyclerViewAdapter
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var adapterrv : BaseRecyclerViewAdapter
    override fun onResume() {
        super.onResume()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.standardBottomSheet)
                if (BottomSheetBehavior.STATE_EXPANDED == bottomSheet.state)
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                else if (TopSheetBehavior.STATE_EXPANDED == TopSheetBehavior.from(binding.topSheetView).state)
                    TopSheetBehavior.from(binding.topSheetView).state = TopSheetBehavior.STATE_COLLAPSED
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
        val viewModel by hiltNavGraphViewModels<WeatherDetailsViewModel>(R.id.nav_graph)
        adapterrv = BaseRecyclerViewAdapter()
        lifecycleScope.launch {
            viewModel.weatherForecast.collect{
                when(it) {
                    is NetworkResponse.Failure -> Toast.makeText(context, "Failure: ${it.errorMessage}", Toast.LENGTH_SHORT)
                    is NetworkResponse.Success -> {
                        val data = it.data as WeatherForecast
                        adapterrv.setForecastItems(data.list, data.city.timezone)
                    }
                    is NetworkResponse.Loading -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.currentWeather.collect{
                when(it) {
                    is NetworkResponse.Failure -> Toast.makeText(context, "Failure: ${it.errorMessage}", Toast.LENGTH_SHORT)
                    is NetworkResponse.Success -> {
                        val data = it.data as CurrentWeather
                        adapterrv.setCurrentWeather(data)
                    }
                    is NetworkResponse.Loading -> {}
                }
            }
        }
        binding.bottomSheet.bottomSheetRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterrv
        }
        binding.locationImg.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_searchFragment)
        }
        binding.menuImg.setOnClickListener {
            TopSheetBehavior.from(binding.topSheetView).state = TopSheetBehavior.STATE_EXPANDED
        }
        binding.topSheetItem.settingsBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_settingsFragment)
        }
        binding.topSheetItem.alarmsBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_alarmFragment)
        }
        binding.topSheetItem.notificationBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_notificationFragment)
        }
    }
}