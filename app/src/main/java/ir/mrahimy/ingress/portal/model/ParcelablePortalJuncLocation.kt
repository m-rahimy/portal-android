package ir.mrahimy.ingress.portal.model

import android.os.Parcel
import android.os.Parcelable

data class ParcelablePortalJuncLocation(
        var id: String? = "",
        var portal_id: String? = "",
        var lat: Double? = 0.0,
        var lon: Double? = 0.0,
        var uploader: String? = "",
        var address: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor(inp: PortalJuncLocation) : this(
            inp.id,
            inp.portal?.id,
            inp.location?.lat,
            inp.location?.lon,
            inp.location?.uploader?.name,
            inp.location?.address,
            inp.location?.inserted_date,
            inp.location?.updated_date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(portal_id)
        parcel.writeValue(lat)
        parcel.writeValue(lon)
        parcel.writeString(uploader)
        parcel.writeString(address)
        parcel.writeString(inserted_date)
        parcel.writeString(updated_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelablePortalJuncLocation> {
        override fun createFromParcel(parcel: Parcel): ParcelablePortalJuncLocation {
            return ParcelablePortalJuncLocation(parcel)
        }

        override fun newArray(size: Int): Array<ParcelablePortalJuncLocation?> {
            return arrayOfNulls(size)
        }
    }
}