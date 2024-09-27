package filo.mamdouh.weatherforecast.features.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.NotificationListener
import filo.mamdouh.weatherforecast.databinding.NotificationItemBinding
import filo.mamdouh.weatherforecast.features.alarm.adapter.AlarmItemDiffUtil
import filo.mamdouh.weatherforecast.logic.showTime
import filo.mamdouh.weatherforecast.models.AlarmItem

class NotificationAdapter (private val listener: NotificationListener) : ListAdapter<AlarmItem, NotificationAdapter.ViewHolder>(AlarmItemDiffUtil())  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NotificationItemBinding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            notificationTitle.text = item.message
            notificationDate.text = item.time.showTime()
            imageView9.setOnClickListener { listener.removeItem(item) }
        }
    }
}