package vn.dxg.tikihomedemo.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_banner.view.ivBanner
import vn.dxg.tikihomedemo.R
import vn.dxg.tikihomedemo.entities.banner.Banner

class BannerAdapter() : RecyclerView.Adapter<BannerAdapter.BannerVH>() {
  private val mBanners = arrayListOf<Banner>()

  fun setData(banners: List<Banner>) {
    mBanners.clear()
    mBanners.addAll(banners)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BannerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false))

  override fun getItemCount() = mBanners.size

  override fun onBindViewHolder(holder: BannerVH, position: Int) {
    holder.bindData(mBanners[position])
  }

  inner class BannerVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
      LayoutContainer {
    fun bindData(banner: Banner) {
      Glide.with(containerView.context)
          .load(banner.mobile_url)
          .into(containerView.ivBanner)
    }
  }
}