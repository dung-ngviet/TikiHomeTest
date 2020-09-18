package vn.dxg.tikihomedemo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
  const val BASE_URL = "https://api.tiki.vn"
  const val BANNER_PATH = "v2/home/banners/v2"
  const val QUICKLINK_PATH = "shopping/v2/widgets/quick_link"
  const val FLASHDEAL_PATH = "v2/widget/deals/hot"

  private var mInstance: Retrofit? = null

  val instance: Retrofit?
    get() {
      if (mInstance == null) {
        val interceptor = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        mInstance = Builder()
          .baseUrl(BASE_URL)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
      }

      return mInstance
    }
}