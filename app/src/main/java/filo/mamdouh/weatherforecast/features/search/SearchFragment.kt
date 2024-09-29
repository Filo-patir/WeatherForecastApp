package filo.mamdouh.weatherforecast.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.SearchLocationContract
import filo.mamdouh.weatherforecast.databinding.FragmentSearchBinding
import filo.mamdouh.weatherforecast.features.search.adapter.RVAdapter
import filo.mamdouh.weatherforecast.logic.NetworkUtils
import filo.mamdouh.weatherforecast.models.CurrentWeather
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() , SearchLocationContract.View{
    lateinit var binding: FragmentSearchBinding
    private val viewModel by hiltNavGraphViewModels<SearchViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            if (NetworkUtils.isNetworkAvailable(requireContext()))
                Navigation.findNavController(it).navigate(R.id.action_searchFragment_to_mapFragment)
            else Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
        val adapter = RVAdapter(this)
        binding.searchRV.apply {
            setAdapter(adapter)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.list.collect{
                    Log.d("Filo", "onViewCreated: $it")
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClickListener(currentWeather: CurrentWeather) {
        val bundle = Bundle().apply {
            putDouble("lon",currentWeather.coord.lon)
            putDouble("lat", currentWeather.coord.lat)
        }
        Navigation.findNavController(binding.root).navigate(R.id.action_searchFragment_to_favouriteDisplayFragment, bundle)
    }

    override fun onDeleteClicked(position: Int) {
        viewModel.deleteLocation(position)
    }
}