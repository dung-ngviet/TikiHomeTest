package vn.dxg.tikihomedemo.entities.banner

import vn.dxg.tikihomedemo.utils.EMPTY

data class Banner(
  val title: String = EMPTY,
  val content: String = EMPTY,
  val url: String = EMPTY,
  val image_url: String = EMPTY,
  val thumbnail_url: String = EMPTY,
  val mobile_url: String = EMPTY,
  val ratio: Float = 0f
)
