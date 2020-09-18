package vn.dxg.tikihomedemo.entities.flashdeal

import vn.dxg.tikihomedemo.utils.EMPTY

data class FlashDealProgress(
  val qty: Int = 0,
  val qtyOrdered: Int = 0,
  val qtyRemain: Int = 0,
  val percent: Float = 0f,
  val status: String = EMPTY
)
