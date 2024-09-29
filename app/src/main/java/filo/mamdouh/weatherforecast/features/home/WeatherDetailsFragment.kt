package filo.mamdouh.weatherforecast.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentWeatherDetailsBinding
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.features.favourite.rvadapters.HomeBaseRecyclerViewAdapter
import filo.mamdouh.weatherforecast.logic.NetworkUtils
import filo.mamdouh.weatherforecast.models.CachedData
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var adapterrv : HomeBaseRecyclerViewAdapter
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
        adapterrv = HomeBaseRecyclerViewAdapter()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.currentWeather.collect{
                    when(it) {
                        is NetworkResponse.Failure -> Toast.makeText(context, "Failure: ${it.errorMessage}", Toast.LENGTH_SHORT)
                        is NetworkResponse.Success -> {
                            Log.d("Filo", "onViewCreated: ${it.data}")
                            val data = it.data as List<CachedData>
                            adapterrv.setForecastItems(data)
                            binding.apply {
                                date.text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                time.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
                                mainTemp.text = data[0].main.temp.toString()
                                weatherDesc.text = data[0].weather[0].description
                                location.text = data[0].key.city.name
                                lowTxt.text = data[0].main.temp_min.toString()
                                highTxt.text = data[0].main.temp_max.toString()
                            }
                        }
                        is NetworkResponse.Loading -> {}
                    }
                }
            }
        }
        binding.bottomSheet.bottomSheetRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterrv
        }
        binding.locationImg.setOnClickListener {
            if(NetworkUtils.isNetworkAvailable(requireContext()))
                Navigation.findNavController(it).navigate(R.id.action_weatherDetailsFragment_to_searchFragment)
            else Toast.makeText(context, "This Feature Requires Internet", Toast.LENGTH_SHORT).show()
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