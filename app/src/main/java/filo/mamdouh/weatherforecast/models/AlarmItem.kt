package filo.mamdouh.weatherforecast.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.Gson
import java.time.LocalDateTime

@Entity(primaryKeys = ["id"], tableName = "alarm_table")
data class AlarmItem( val time: LocalDateTime , val message: String, val flag : Boolean , val result: Boolean, val id: Long = System.currentTimeMillis()) : Parcelable {
    constructor(parcel: Parcel) : this(
        Gson().fromJson(parcel.readString().toString(), LocalDateTime::class.java),
        parcel.readString().toString(),
        parcel.readByte() == 1.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Gson().toJson(time))
        parcel.writeString(message)
        parcel.writeByte(if (flag) 1 else 0)
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlarmItem> {
        override fun createFromParcel(parcel: Parcel): AlarmItem {
            return AlarmItem(parcel)
        }

        override fun newArray(size: Int): Array<AlarmItem?> {
            return arrayOfNulls(size)
        }
    }
}
