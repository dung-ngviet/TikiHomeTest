package vn.dxg.tikihomedemo.entities

import com.google.gson.annotations.SerializedName
import vn.dxg.tikihomedemo.utils.EMPTY

data class Product(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String = EMPTY,
    val price: String = EMPTY,
    val discount: String = EMPTY
)