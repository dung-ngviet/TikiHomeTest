package vn.dxg.tikihomedemo.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applyScheduler(): Single<T> {
  return this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.toMainThread(): Single<T> {
  return this.observeOn(AndroidSchedulers.mainThread())
}