package filo.mamdouh.weatherforecast.features.alarm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.AlarmListener
import filo.mamdouh.weatherforecast.databinding.AlarmItemBinding
import filo.mamdouh.weatherforecast.logic.showTime
import filo.mamdouh.weatherforecast.models.AlarmItem

class AlarmAdapter (private val listener : AlarmListener): ListAdapter<AlarmItem, AlarmAdapter.ViewHolder>(AlarmItemDiffUtil())  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AlarmItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            alarmTitle.text = item.message
            alarmDate.text = item.time.showTime()
            deleteBtn.setOnClickListener {
                listener.onRemoveCLicked(item)
            }
            editBtn.setOnClickListener { listener.onEditClicked(item) }
        }
    }

}