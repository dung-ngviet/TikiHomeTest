package vn.dxg.tikihomedemo.entities.quicklink

import com.google.gson.annotations.SerializedName
import vn.dxg.tikihomedemo.utils.EMPTY

data class QuickLink(
  val title: String = EMPTY,
  val content: String = EMPTY,
  val url: String = EMPTY,
  val authentication: Boolean = false,
  val webUrl: String = EMPTY,
  @SerializedName("image_url")
  val imageUrl: String = EMPTY
)