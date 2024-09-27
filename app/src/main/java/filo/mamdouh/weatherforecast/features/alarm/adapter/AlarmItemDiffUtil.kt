package filo.mamdouh.weatherforecast.features.alarm.adapter

import androidx.recyclerview.widget.DiffUtil
import filo.mamdouh.weatherforecast.models.AlarmItem

class AlarmItemDiffUtil : DiffUtil.ItemCallback<AlarmItem>() {
    override fun areItemsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
        return oldItem == newItem
    }
}