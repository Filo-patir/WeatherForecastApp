package filo.mamdouh.weatherforecast.features.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentFavouriteDisplayBinding
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.features.favourite.rvadapters.BaseRecyclerViewAdapter
import filo.mamdouh.weatherforecast.logic.NetworkUtils
import filo.mamdouh.weatherforecast.logic.toDate
import filo.mamdouh.weatherforecast.logic.toHourMinute
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.launch

class FavouriteDisplayFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteDisplayBinding
    private lateinit var adapterrv : BaseRecyclerViewAdapter
    override fun onResume() {
        super.onResume()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bottomSheet = BottomSheetBehavior.from(binding.bottomSheet2.standardBottomSheet)
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
        binding = FragmentFavouriteDisplayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by hiltNavGraphViewModels<FavouriteDisplayViewModel>(R.id.nav_graph)
        if (NetworkUtils.isNetworkAvailable(requireContext()))
            if (arguments != null) {
                Log.d("Filo", "onViewCreated: aaaaaa ")
                viewModel.getData(requireArguments().getDouble("lat"), requireArguments().getDouble("lon")
                )
            }
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
                        binding.apply {
                            lowTxt2.text = data.main.temp_min.toString()
                            highTxt2.text = data.main.temp_max.toString()
                            location2.text = data.name
                            mainTemp2.text = data.main.temp.toString()
                            date2.text = data.dt.toDate(data.timezone)
                            time2.text = data.dt.toHourMinute(data.timezone)
                            weatherDesc2.text = data.weather[0].description
                        }
                    }
                    is NetworkResponse.Loading -> {}
                }
            }
        }
        binding.bottomSheet2.bottomSheetRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterrv
        }
    }
}