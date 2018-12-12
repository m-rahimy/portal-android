package ir.mrahimy.ingress.portal.util

import android.os.Parcel
import android.os.Parcelable
import ir.mrahimy.ingress.portal.model.PortalImage

data class ParcelablePortalImage(
        var id: String? = "",
        var portal_id: String? = "",
        var portal_uploader: String? = "",
        var image_url: String? = "",
        var image_uploader: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    constructor(pi: PortalImage) : this(
            pi.id,
            pi.portal?.id,
            pi.portal?.uploader?.name,
            pi.image?.url,
            pi.image?.uploader?.name,
            pi.inserted_date,
            pi.updated_date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(portal_id)
        parcel.writeString(portal_uploader)
        parcel.writeString(image_url)
        parcel.writeString(image_uploader)
        parcel.writeString(inserted_date)
        parcel.writeString(updated_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelablePortalImage> {
        override fun createFromParcel(parcel: Parcel): ParcelablePortalImage {
            return ParcelablePortalImage(parcel)
        }

        override fun newArray(size: Int): Array<ParcelablePortalImage?> {
            return arrayOfNulls(size)
        }
    }

}
