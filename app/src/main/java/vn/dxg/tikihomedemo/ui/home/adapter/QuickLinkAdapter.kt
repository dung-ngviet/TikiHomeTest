package vn.dxg.tikihomedemo.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_quick_link.view.quickLinkDescription
import kotlinx.android.synthetic.main.item_quick_link.view.quickLinkImage
import vn.dxg.tikihomedemo.R
import vn.dxg.tikihomedemo.entities.quicklink.QuickLink

class QuickLinkAdapter() : RecyclerView.Adapter<QuickLinkAdapter.QuickLinkVH>() {
  private val mQuickLinks = arrayListOf<QuickLink>()

  fun setData(banners: List<QuickLink>) {
    mQuickLinks.clear()
    mQuickLinks.addAll(banners)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    QuickLinkVH(LayoutInflater.from(parent.context).inflate(R.layout.item_quick_link, parent, false))

  override fun getItemCount() = mQuickLinks.size

  override fun onBindViewHolder(holder: QuickLinkVH, position: Int) {
    holder.bindData(mQuickLinks[position])
  }

  inner class QuickLinkVH(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {
      fun bindData(quickLink: QuickLink) {
        Glide.with(containerView.context)
          .load(quickLink.imageUrl)
          .into(containerView.quickLinkImage)

        containerView.quickLinkDescription.text = quickLink.title
      }
  }
}