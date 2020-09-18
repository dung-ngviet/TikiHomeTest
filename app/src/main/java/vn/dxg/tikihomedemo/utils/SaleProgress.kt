package vn.dxg.tikihomedemo.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SaleProgress : View {
  var progress: Float = 1f
  set(value) {
    if (value != field) {
      field = value
      invalidate()
    }
  }

  private var w: Int = 0
  private var h: Int = 0
  private val paint = Paint().apply {
    isAntiAlias = true
    color = Color.RED
  }
  private var stopXPos: Float = 0f

  constructor(context: Context?) : super(context)

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context, attrs,
    defStyleAttr
  )

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    this.w = w
    this.h = h
  }

  override fun onDraw(canvas: Canvas?) {
    stopXPos = w * progress
    canvas?.drawRoundRect(0f, 0f, stopXPos, h.toFloat(), w / 2f, w / 2f, paint)
  }
}