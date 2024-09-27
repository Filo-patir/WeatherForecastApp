package filo.mamdouh.weatherforecast.features.alarm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.AlarmListener
import filo.mamdouh.weatherforecast.databinding.FragmentAlarmBinding
import filo.mamdouh.weatherforecast.features.alarm.adapter.AlarmAdapter
import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.launch

class AlarmFragment : Fragment(), AlarmListener{
    lateinit var binding: FragmentAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by hiltNavGraphViewModels<AlarmViewModel>(R.id.nav_graph)
        val adapter = AlarmAdapter(this)
        binding.apply {
            RV.adapter = adapter
            floatingActionButton.setOnClickListener { addAlarm() }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.alarmList.collect{
                    Log.d("Filo", "onViewCreated: $it")
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun addAlarm() {
        TODO("Not yet implemented")
    }

    override fun onRemoveCLicked(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }

    override fun onEditClicked(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }
}