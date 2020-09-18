package vn.dxg.tikihomedemo.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_tiki_home.contentContainer
import kotlinx.android.synthetic.main.activity_tiki_home.loadingBannerQuickLink
import kotlinx.android.synthetic.main.activity_tiki_home.loadingFlashDeal
import kotlinx.android.synthetic.main.activity_tiki_home.rvFlashDeals
import kotlinx.android.synthetic.main.activity_tiki_home.rvQuickLinks
import kotlinx.android.synthetic.main.activity_tiki_home.vpBanners
import vn.dxg.tikihomedemo.R
import vn.dxg.tikihomedemo.ui.home.adapter.BannerAdapter
import vn.dxg.tikihomedemo.ui.home.adapter.FlashDealAdapter
import vn.dxg.tikihomedemo.ui.home.adapter.QuickLinkAdapter
import vn.dxg.tikihomedemo.entities.banner.Banner
import vn.dxg.tikihomedemo.entities.flashdeal.FlashDeal
import vn.dxg.tikihomedemo.entities.quicklink.QuickLink
import vn.dxg.tikihomedemo.utils.Home.QUICK_LINK_SPAN_COUNT
import vn.dxg.tikihomedemo.utils.Home.SCROLL_NEXT_BANNER_DELAY_TIME
import vn.dxg.tikihomedemo.utils.gone
import vn.dxg.tikihomedemo.utils.invisible
import vn.dxg.tikihomedemo.utils.visible

class TikiHomeActivity : AppCompatActivity(), HomeContract.View {
  private lateinit var bannerAdapter: BannerAdapter
  private lateinit var quickLinkAdapter: QuickLinkAdapter
  private lateinit var flashDealAdapter: FlashDealAdapter
  private var autoScrollNextTask: Runnable? = null
  private var handler = Handler()

  private val presenter by lazy {
    TikiHomePresenter(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tiki_home)
    initView()
    initActions()
    initData()
  }

  private fun initView() {
    setFullScreen()
    bannerAdapter = BannerAdapter()
    vpBanners.adapter = bannerAdapter
    vpBanners.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    vpBanners.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      var oldState = RecyclerView.SCROLL_STATE_IDLE

      override fun onPageScrollStateChanged(state: Int) {
        if (oldState != state) {
          if (state == RecyclerView.SCROLL_STATE_IDLE) {
            autoScrollNextTask?.let {handler.postDelayed(it, SCROLL_NEXT_BANNER_DELAY_TIME)}
            oldState = state
          } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            autoScrollNextTask?.let {handler.removeCallbacks(it)}
            oldState = state
          }
        }
      }

      override fun onPageSelected(position: Int) {
        if ((vpBanners.currentItem + 1) == bannerAdapter.itemCount) {
          vpBanners.currentItem = 0
        }
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

      }
    })

    quickLinkAdapter = QuickLinkAdapter()
    rvQuickLinks.layoutManager = GridLayoutManager(this, QUICK_LINK_SPAN_COUNT, GridLayoutManager.HORIZONTAL, false)
    rvQuickLinks.adapter = quickLinkAdapter

    flashDealAdapter = FlashDealAdapter()
    rvFlashDeals.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    rvFlashDeals.adapter = flashDealAdapter
    val horSpace = resources.getDimension(R.dimen.home_flasdeal_item_margin)
    rvFlashDeals.addItemDecoration(object : RecyclerView.ItemDecoration() {
      override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        outRect.left = horSpace.toInt()
      }
    })
  }

  private fun initActions() {
    contentContainer.setOnRefreshListener {
      presenter.loadData()
      contentContainer.isRefreshing = false
    }
  }

  private fun initData() {
    presenter.loadData()
  }

  private fun setFullScreen() {
    window.apply {
      decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
  }

  override fun showLoadingBannerQuickLink(isLoading: Boolean) {
    if (isLoading) {
      vpBanners.invisible()
      rvQuickLinks.invisible()
      loadingBannerQuickLink.visible()
    } else {
      loadingBannerQuickLink.invisible()
    }

  }

  override fun showLoadingFlashDeal(isLoading: Boolean) {
    if (isLoading) {
      rvFlashDeals.invisible()
      loadingFlashDeal.visible()
    } else {
      loadingFlashDeal.invisible()
    }
  }

  override fun displayBanners(banners: List<Banner>) {
    if (banners.isNotEmpty()) {
      vpBanners.visible()
      val ratio = banners.first().ratio

      val width = DisplayMetrics().let { metric ->
        windowManager.defaultDisplay.getMetrics(metric)
        metric.widthPixels
      }

      val margin = resources.getDimension(R.dimen.home_block_margin)
      val newHeight = (width - margin * 2).div(ratio) + margin * 2
      val layoutParams = ConstraintLayout.LayoutParams(width, newHeight.toInt())
      vpBanners.layoutParams = layoutParams
      bannerAdapter.setData(banners)

      setUpAutoScrollNextBanner(banners.size)
    } else {
      vpBanners.gone()
    }
  }

  private fun setUpAutoScrollNextBanner(size: Int) {
    autoScrollNextTask = Runnable {
      val nextItem = if (vpBanners.currentItem == size - 1) 0 else vpBanners.currentItem + 1
      vpBanners.setCurrentItem(nextItem, true)
      autoScrollNextTask?.let {handler.postDelayed(it, SCROLL_NEXT_BANNER_DELAY_TIME)}
    }
    autoScrollNextTask?.let {handler.postDelayed(it, SCROLL_NEXT_BANNER_DELAY_TIME)}
  }

  override fun displayQuickLinks(quickLinks: List<QuickLink>) {
    if (quickLinks.isNotEmpty()) {
      rvQuickLinks.visible()
      quickLinkAdapter.setData(quickLinks)
    } else {
      rvQuickLinks.gone()
    }
  }

  override fun displayFlashDeals(flashDeals: List<FlashDeal>) {
    if (flashDeals.isNotEmpty()) {
      rvFlashDeals.visible()
      flashDealAdapter.setData(flashDeals)
    } else {
      rvFlashDeals.invisible()
    }
  }

  override fun changeVisibleLists(isVisible: Boolean) {
    if (isVisible) {
      rvFlashDeals.visible()
      rvQuickLinks.visible()
      vpBanners.visible()
    } else {
      rvFlashDeals.invisible()
      rvQuickLinks.invisible()
      vpBanners.invisible()
    }
  }
}