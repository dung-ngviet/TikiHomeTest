package vn.dxg.tikihomedemo.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import vn.dxg.tikihomedemo.entities.banner.BannerResponse
import vn.dxg.tikihomedemo.entities.flashdeal.FlashDealResponse
import vn.dxg.tikihomedemo.entities.quicklink.QuickLinkResponse

interface TikiAPIs {
  @GET("{api}")
  fun getBanners(@Path("api", encoded = true) path: String = RetrofitClient.BANNER_PATH): Single<BannerResponse>

  @GET("{api}")
  fun getQuickLinks(@Path("api", encoded = true) path: String = RetrofitClient.QUICKLINK_PATH): Single<QuickLinkResponse>

  @GET("{api}")
  fun getFlashDeals(@Path("api", encoded = true) path: String = RetrofitClient.FLASHDEAL_PATH): Single<FlashDealResponse>
}