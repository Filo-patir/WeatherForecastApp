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
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.AddAlarmListener
import filo.mamdouh.weatherforecast.contracts.AlarmListener
import filo.mamdouh.weatherforecast.databinding.FragmentAlarmBinding
import filo.mamdouh.weatherforecast.features.alarm.adapter.AlarmAdapter
import filo.mamdouh.weatherforecast.features.dialogs.AddAlarmDialog
import filo.mamdouh.weatherforecast.logic.alarm.AlarmSchedulerImpl
import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlarmFragment : Fragment(), AlarmListener , AddAlarmListener {
    lateinit var binding: FragmentAlarmBinding
    private val viewModel by hiltNavGraphViewModels<AlarmViewModel>(R.id.nav_graph)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        AddAlarmDialog.showDialog(activity,activity?.supportFragmentManager,this)
    }

    override fun onRemoveCLicked(alarmItem: AlarmItem) {
        context?.let { AlarmSchedulerImpl(it).cancelAlarm(alarmItem) }
        viewModel.removeAlarm(alarmItem)
    }

    override fun onEditClicked(alarmItem: AlarmItem) {
        AddAlarmDialog.showDialog(activity,activity?.supportFragmentManager,this, alarmItem)
    }

    override fun onAddAlarmClicked(alarmItem: AlarmItem) {
        context?.let { AlarmSchedulerImpl(it).scheduleAlarm(alarmItem) }
        viewModel.saveAlarm(alarmItem)
    }

}