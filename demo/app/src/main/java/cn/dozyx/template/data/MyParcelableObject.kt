package cn.dozyx.template.data

import android.os.Parcel
import android.os.Parcelable

class MyParcelableObject : Parcelable {
    var overlayStatus = 0
    var endDuration: Long = 0
    var endCircleAngle = 0f

    constructor() {}

    protected constructor(`in`: Parcel) {
        overlayStatus = `in`.readInt()
        endDuration = `in`.readLong()
        endCircleAngle = `in`.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(overlayStatus)
        dest.writeLong(endDuration)
        dest.writeFloat(endCircleAngle)
    }

    override fun toString(): String {
        return "MyParcelableObject ${super.toString()}(overlayStatus=$overlayStatus, endDuration=$endDuration, endCircleAngle=$endCircleAngle)"
    }


    companion object {
        const val EXTRA_OVERLAY_DATA = "EXTRA_OVERLAY_DATA"
        const val OVERLAY_ERROR = 1
        const val OVERLAY_LOADING_NEXT = 2
        const val OVERLAY_REPLAY = 3

        @JvmField
        val CREATOR: Parcelable.Creator<MyParcelableObject> =
            object : Parcelable.Creator<MyParcelableObject> {
                override fun createFromParcel(source: Parcel): MyParcelableObject {
                    return MyParcelableObject(source)
                }

                override fun newArray(size: Int): Array<MyParcelableObject?> {
                    return arrayOfNulls(size)
                }
            }
    }
}