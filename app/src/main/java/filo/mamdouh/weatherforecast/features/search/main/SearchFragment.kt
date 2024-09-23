package filo.mamdouh.weatherforecast.features.search.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by hiltNavGraphViewModels<SearchViewModel>(R.id.nav_graph)
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchFragment_to_mapFragment)
        }
    }
}