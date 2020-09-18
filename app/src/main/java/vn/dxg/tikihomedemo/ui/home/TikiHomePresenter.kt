package vn.dxg.tikihomedemo.ui.home

import android.util.Log
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import vn.dxg.tikihomedemo.entities.banner.Banner
import vn.dxg.tikihomedemo.entities.banner.BannerResponse
import vn.dxg.tikihomedemo.entities.quicklink.QuickLink
import vn.dxg.tikihomedemo.entities.quicklink.QuickLinkResponse
import vn.dxg.tikihomedemo.network.RetrofitClient
import vn.dxg.tikihomedemo.network.TikiAPIs
import vn.dxg.tikihomedemo.ui.home.HomeContract.Presenter
import vn.dxg.tikihomedemo.ui.home.HomeContract.View
import vn.dxg.tikihomedemo.utils.Home.REQUEST_TIMEOUT
import vn.dxg.tikihomedemo.utils.applyScheduler
import vn.dxg.tikihomedemo.utils.toMainThread
import java.util.concurrent.TimeUnit

class TikiHomePresenter(val view: View): Presenter {
  private val tikiAPIs by lazy {
    RetrofitClient.instance?.create(TikiAPIs::class.java)
  }

  override fun loadData() {
    view.changeVisibleLists(false)

    // Load banners and quick-links in parallel by using Single.zip, each task run in different thread by .subscribeOn(Schedulers.io())
    tikiAPIs?.let {api ->
      Single.zip(api.getBanners().timeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS).onErrorReturn { BannerResponse(emptyList()) }.subscribeOn(Schedulers.io()),
        api.getQuickLinks().timeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS).onErrorReturn { QuickLinkResponse(emptyList()) }.subscribeOn(Schedulers.io()),
        BiFunction<BannerResponse, QuickLinkResponse, Pair<List<Banner>, List<QuickLink>>> { bannerRes, quickLinkRes ->
          return@BiFunction bannerRes.data to quickLinkRes.data.first()
        })
        .toMainThread()
        .doOnSubscribe { view.showLoadingBannerQuickLink(true) }
        .subscribe({ result ->
          view.showLoadingBannerQuickLink(false)
          view.displayBanners(banners = result.first)
          view.displayQuickLinks(quickLinks = result.second)
          loadFlashDeal()
        }, {
          view.showLoadingBannerQuickLink(false)
          view.displayBanners(emptyList())
          view.displayQuickLinks(emptyList())
          loadFlashDeal()
          Log.e("Home", "Load banner and quick link fail")
        })
    }
  }

  private fun loadFlashDeal() {
    tikiAPIs?.let {api ->
      api.getFlashDeals()
        .applyScheduler()
        .doOnSubscribe { view.showLoadingFlashDeal(true) }
        .subscribe({flashDealsRes ->
          view.showLoadingFlashDeal(false)
          view.displayFlashDeals(flashDeals = flashDealsRes.data)
        }, {
          view.showLoadingFlashDeal(false)
          view.displayFlashDeals(emptyList())
          Log.e("Home", "Load flash deal fail")
        })
    }
  }
}