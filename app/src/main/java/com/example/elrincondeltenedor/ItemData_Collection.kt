package com.example.elrincondeltenedor

import android.os.Parcel
import android.os.Parcelable

data class ItemData_Collection(
    val text: String,
    val description: String,
    val imageResId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(description)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ItemData_Collection> {
        override fun createFromParcel(parcel: Parcel): ItemData_Collection {
            return ItemData_Collection(parcel)
        }

        override fun newArray(size: Int): Array<ItemData_Collection?> {
            return arrayOfNulls(size)
        }
    }
}