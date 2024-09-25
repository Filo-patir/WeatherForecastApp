package filo.mamdouh.weatherforecast.models

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class AlarmItem( val time: LocalDateTime , val message: String , val result: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong().let { LocalDateTime.ofEpochSecond(it, 0,
            ZoneId.systemDefault() as ZoneOffset?
        ) },
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(time.atZone(ZoneId.systemDefault()).toEpochSecond())
        parcel.writeString(message)
        parcel.writeByte(if (result) 1 else 0)
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
