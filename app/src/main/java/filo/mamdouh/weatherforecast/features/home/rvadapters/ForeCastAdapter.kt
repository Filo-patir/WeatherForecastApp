package filo.mamdouh.weatherforecast.features.home.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.WeatherForecastItemBinding
import filo.mamdouh.weatherforecast.logic.toDrawable
import filo.mamdouh.weatherforecast.logic.toHour
import filo.mamdouh.weatherforecast.logic.toTime
import filo.mamdouh.weatherforecast.models.ForecastItems

class ForeCastAdapter(private var list: List<ForecastItems>,private val timeZone:Int , private var flag: Boolean) :
    RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder>() {

        fun updateList(list: List<ForecastItems>, flag: Boolean){
            this.list = list
            this.flag = flag
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.weather_forecast_item,parent,false)
        return ForeCastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ForeCastViewHolder, position: Int) {
            if (flag){
                list[position].apply {
                    holder.binding.apply {
                        dayTxt.text = dt.toHour(timeZone)
                        imageView.setImageResource(weather[0].icon.toDrawable())
                        weatherTxt.text = buildString {
                            append(main.temp.toString() + " ")
                            append(R.string.degree)
                        }
                    }
                }
            }else{
                list[position].apply {
                    holder.binding.apply {
                        dayTxt.animate().translationY(-30f)
                        imageView.animate().translationY(30f)
                        dayTxt.text = dt.toTime(timeZone)
                        imageView.setImageResource(weather[0].icon.toDrawable())
                        weatherTxt.text = buildString {
                            append(main.temp.toString() + " ")
                            append(R.string.degree)
                        }
                    }
                }
            }
        }
    class ForeCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = WeatherForecastItemBinding.bind(itemView)
    }
}