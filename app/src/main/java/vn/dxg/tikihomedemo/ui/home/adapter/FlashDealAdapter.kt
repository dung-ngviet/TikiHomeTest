package vn.dxg.tikihomedemo.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_flash_deal.view.ivProduct
import kotlinx.android.synthetic.main.item_flash_deal.view.saleProgress
import kotlinx.android.synthetic.main.item_flash_deal.view.tvDiscount
import kotlinx.android.synthetic.main.item_flash_deal.view.tvPrice
import kotlinx.android.synthetic.main.item_flash_deal.view.tvSaleStatus
import vn.dxg.tikihomedemo.R
import vn.dxg.tikihomedemo.entities.flashdeal.FlashDeal
import java.text.DecimalFormat

class FlashDealAdapter() : RecyclerView.Adapter<FlashDealAdapter.FlashDealVH>() {
  private val mFlashDeals = arrayListOf<FlashDeal>()

  fun setData(flashDeals: List<FlashDeal>) {
    mFlashDeals.clear()
    mFlashDeals.addAll(flashDeals)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    FlashDealVH(LayoutInflater.from(parent.context).inflate(R.layout.item_flash_deal, parent, false))

  override fun getItemCount() = mFlashDeals.size

  override fun onBindViewHolder(holder: FlashDealVH, position: Int) {
    holder.bindData(mFlashDeals[position])
  }

  class FlashDealVH(override val containerView: View) : RecyclerView.ViewHolder(
      containerView
  ), LayoutContainer {
    fun bindData(flashDeal: FlashDeal) {
      Glide.with(containerView.context)
        .load(flashDeal.product.thumbnailUrl)
        .into(containerView.ivProduct)

      val formatter = DecimalFormat("###,###")
      val specialPrice = formatter.format(flashDeal.specialPrice / 1000f) + " ₫"
      containerView.tvPrice.text = specialPrice

      containerView.tvDiscount.text = String.format("-%s%%", flashDeal.discountPercent)

      containerView.saleProgress.progress = flashDeal.progress.percent.div(100)

      val soldItems = flashDeal.progress.let {prg -> prg.percent * prg.qty / 100}.toInt()

      val textSoldItems = if (soldItems < 1) "Vừa mở bán" else "Đã bán $soldItems"
      containerView.tvSaleStatus.text = textSoldItems

    }
  }
}