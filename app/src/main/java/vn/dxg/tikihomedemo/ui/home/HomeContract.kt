package vn.dxg.tikihomedemo.ui.home

import vn.dxg.tikihomedemo.entities.banner.Banner
import vn.dxg.tikihomedemo.entities.flashdeal.FlashDeal
import vn.dxg.tikihomedemo.entities.quicklink.QuickLink

interface HomeContract {
  interface View {
    fun showLoadingBannerQuickLink(isLoading: Boolean)

    fun showLoadingFlashDeal(isLoading: Boolean)

    fun displayBanners(banners: List<Banner>)

    fun displayQuickLinks(quickLinks: List<QuickLink>)

    fun displayFlashDeals(flashDeals: List<FlashDeal>)

    fun changeVisibleLists(isVisible: Boolean)
  }

  interface Presenter {
    fun loadData()
  }
}