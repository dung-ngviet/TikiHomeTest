package vn.dxg.tikihomedemo.entities.flashdeal

import com.google.gson.annotations.SerializedName
import vn.dxg.tikihomedemo.entities.Product
import vn.dxg.tikihomedemo.utils.EMPTY

data class FlashDeal(
  @SerializedName("discount_percent")
  val discountPercent: Int = 0,
  @SerializedName("special_price")
  val specialPrice: Long = 0,
  val progress: FlashDealProgress,
  val product: Product
)

