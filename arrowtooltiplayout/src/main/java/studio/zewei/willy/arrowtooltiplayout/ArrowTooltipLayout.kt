package studio.zewei.willy.arrowtooltiplayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by Willy on 2020/09/08.
 */
class ArrowTooltipLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // 取得自訂參數
    private val attributes by lazy {
        context.obtainStyledAttributes(attrs, R.styleable.ArrowTooltipLayout)
    }

    // 顏色
    private val tipLayoutColor =
        attributes.getColor(R.styleable.ArrowTooltipLayout_tipLayoutColor, Color.GRAY)

    // 位置 (箭頭鄰邊的 % 值)
    private val positionPercentage =
        attributes.getFloat(R.styleable.ArrowTooltipLayout_positionPercentage, 0.5f)
            .let { if (it < 0) 0.5f else it }

    // 箭頭方向
    private val arrowOrientation =
        ArrowOrientation.values()[attributes.getInt(
            R.styleable.ArrowTooltipLayout_arrowOrientation, 0
        )]

    // 箭頭高
    private val arrowH =
        attributes.getDimensionPixelSize(
            R.styleable.ArrowTooltipLayout_arrowHeight,
            context.dpToPx(10)
        ).let { if (it <= 0) context.dpToPx(10) else it }

    // 箭頭寬
    private val arrowW =
        attributes.getDimensionPixelSize(
            R.styleable.ArrowTooltipLayout_arrowWidth,
            context.dpToPx(20)
        ).let { if (it <= 0) context.dpToPx(20) else it }

    // Layout 導角
    private val tipLayoutRadius =
        attributes.getDimensionPixelSize(
            R.styleable.ArrowTooltipLayout_tipLayoutRadius,
            context.dpToPx(5)
        ).let { if (it < 0) context.dpToPx(5) else it }

    // Paint Style
    private val paintStyle =
        Paint.Style.values()[attributes.getInt(
            R.styleable.ArrowTooltipLayout_tipLayoutStyle, 1
        )]

    // 畫圖的路徑
    private val path = Path()

    // 畫筆
    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = paintStyle
            color = tipLayoutColor
            strokeWidth = context.dpToPx(2).toFloat()
        }
    }

    fun setTooltipColor(color: Int) {
        paint.color = color
    }

    // 由外部指定的起始點
    private var specificX = -1f
    private var specificY = -1f

    /**
     * 設定箭頭的水平定位點
     */
    fun setStartX(x: Float) {
        path.reset()
        if (x >= 0) specificX = x
        invalidate()
    }

    /**
     * 設定箭頭的垂直定位點
     */
    fun setStartY(y: Float) {
        path.reset()
        if (y >= 0) specificY = y
        invalidate()
    }

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minW: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = View.resolveSizeAndState(minW, widthMeasureSpec, 0)

        val minH: Int = paddingBottom + paddingTop + suggestedMinimumHeight
        val h: Int = View.resolveSizeAndState(minH, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.also { c ->
            // 箭頭方向
            when (arrowOrientation) {
                ArrowOrientation.BOTTOM -> drawBottomArrowLayout(c)
                ArrowOrientation.START -> drawLeftArrowLayout(c)
                ArrowOrientation.END -> drawRightArrowLayout(c)
                else -> drawTopArrowLayout(c)
            }
        }
    }

    private fun drawTopArrowLayout(canvas: Canvas) {
        // 起點
        val startX = if (specificX > 0) specificX else measuredWidth * positionPercentage
        val startY = 0F

        // 四個角
        val topRightX = measuredWidth.toFloat()
        val topRightY = 0F + arrowH.toFloat()
        val bottomRightX = measuredWidth.toFloat()
        val bottomRightY = measuredHeight.toFloat()
        val bottomLeftX = 0F
        val bottomLeftY = measuredHeight.toFloat()
        val topLeftX = 0F
        val topLeftY = 0F + arrowH.toFloat()

        // 三角點 1 & 2 (依起點順時針)
        val triangleX1 = startX + arrowW / 2F
        val triangleY1 = startY + arrowH.toFloat()
        val triangleX2 = startX - arrowW / 2F
        val triangleY2 = startY + arrowH.toFloat()

        // 設置 Path
        path.moveTo(startX, startY)
        path.lineTo(triangleX1, triangleY1)
        path.lineTo(topRightX - tipLayoutRadius, topRightY)
        path.cubicTo(
            topRightX - tipLayoutRadius, topRightY,
            topRightX, topRightY,
            topRightX, topRightY + tipLayoutRadius
        )
        path.lineTo(bottomRightX, bottomRightY - tipLayoutRadius)
        path.cubicTo(
            bottomRightX, bottomRightY - tipLayoutRadius,
            bottomRightX, bottomRightY,
            bottomRightX - tipLayoutRadius, bottomRightY
        )
        path.lineTo(bottomLeftX + tipLayoutRadius, bottomLeftY)
        path.cubicTo(
            bottomLeftX + tipLayoutRadius, bottomLeftY,
            bottomLeftX, bottomLeftY,
            bottomLeftX, bottomLeftY - tipLayoutRadius
        )
        path.lineTo(topLeftX, topLeftY + tipLayoutRadius)
        path.cubicTo(
            topLeftX, topLeftY + tipLayoutRadius,
            topLeftX, topLeftY,
            topLeftX + tipLayoutRadius, topLeftY
        )
        path.lineTo(triangleX2, triangleY2)
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun drawBottomArrowLayout(canvas: Canvas) {
        // 起點
        val startX = if (specificX > 0) specificX else measuredWidth * positionPercentage
        val startY = measuredHeight.toFloat()

        // 四個角
        val topRightX = measuredWidth.toFloat()
        val topRightY = 0F
        val bottomRightX = measuredWidth.toFloat()
        val bottomRightY = measuredHeight - arrowH.toFloat()
        val bottomLeftX = 0F
        val bottomLeftY = measuredHeight - arrowH.toFloat()
        val topLeftX = 0F
        val topLeftY = 0F

        // 三角點 1 & 2 (依起點順時針)
        val triangleX1 = startX - arrowW / 2F
        val triangleY1 = startY - arrowH.toFloat()
        val triangleX2 = startX + arrowW / 2F
        val triangleY2 = startY - arrowH.toFloat()

        // 設置 Path
        path.moveTo(startX, startY)
        path.lineTo(triangleX1, triangleY1)
        path.lineTo(bottomLeftX + tipLayoutRadius, bottomLeftY)
        path.cubicTo(
            bottomLeftX + tipLayoutRadius, bottomLeftY,
            bottomLeftX, bottomLeftY,
            bottomLeftX, bottomLeftY - tipLayoutRadius
        )
        path.lineTo(topLeftX, topLeftY + tipLayoutRadius)
        path.cubicTo(
            topLeftX, topLeftY + tipLayoutRadius,
            topLeftX, topLeftY,
            topLeftX + tipLayoutRadius, topLeftY
        )
        path.lineTo(topRightX - tipLayoutRadius, topRightY)
        path.cubicTo(
            topRightX - tipLayoutRadius, topRightY,
            topRightX, topRightY,
            topRightX, topRightY + tipLayoutRadius
        )
        path.lineTo(bottomRightX, bottomRightY - tipLayoutRadius)
        path.cubicTo(
            bottomRightX, bottomRightY - tipLayoutRadius,
            bottomRightX, bottomRightY,
            bottomRightX - tipLayoutRadius, bottomRightY
        )
        path.lineTo(triangleX2, triangleY2)
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun drawLeftArrowLayout(canvas: Canvas) {
        // 起點
        val startX = 0F
        val startY = if (specificY > 0) specificY else measuredHeight * positionPercentage

        // 四個角
        val topRightX = measuredWidth.toFloat()
        val topRightY = 0F
        val bottomRightX = measuredWidth.toFloat()
        val bottomRightY = measuredHeight.toFloat()
        val bottomLeftX = 0F + arrowH.toFloat()
        val bottomLeftY = measuredHeight.toFloat()
        val topLeftX = 0F + arrowH.toFloat()
        val topLeftY = 0F

        // 三角點 1 & 2 (依起點順時針)
        val triangleX1 = startX + arrowH.toFloat()
        val triangleY1 = startY - arrowW / 2F
        val triangleX2 = startX + arrowH.toFloat()
        val triangleY2 = startY + arrowW / 2F

        // 設置 Path
        path.moveTo(startX, startY)
        path.lineTo(triangleX1, triangleY1)
        path.lineTo(topLeftX, topLeftY + tipLayoutRadius)
        path.cubicTo(
            topLeftX, topLeftY + tipLayoutRadius,
            topLeftX, topLeftY,
            topLeftX + tipLayoutRadius, topLeftY
        )
        path.lineTo(topRightX - tipLayoutRadius, topRightY)
        path.cubicTo(
            topRightX - tipLayoutRadius, topRightY,
            topRightX, topRightY,
            topRightX, topRightY + tipLayoutRadius
        )
        path.lineTo(bottomRightX, bottomRightY - tipLayoutRadius)
        path.cubicTo(
            bottomRightX, bottomRightY - tipLayoutRadius,
            bottomRightX, bottomRightY,
            bottomRightX - tipLayoutRadius, bottomRightY
        )
        path.lineTo(bottomLeftX + tipLayoutRadius, bottomLeftY)
        path.cubicTo(
            bottomLeftX + tipLayoutRadius, bottomLeftY,
            bottomLeftX, bottomLeftY,
            bottomLeftX, bottomLeftY - tipLayoutRadius
        )
        path.lineTo(triangleX2, triangleY2)
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun drawRightArrowLayout(canvas: Canvas) {
        // 起點
        val startX = measuredWidth.toFloat()
        val startY = if (specificY > 0) specificY else measuredHeight * positionPercentage

        // 四個角
        val topRightX = measuredWidth.toFloat() - arrowH.toFloat()
        val topRightY = 0F
        val bottomRightX = measuredWidth.toFloat() - arrowH.toFloat()
        val bottomRightY = measuredHeight.toFloat()
        val bottomLeftX = 0F
        val bottomLeftY = measuredHeight.toFloat()
        val topLeftX = 0F
        val topLeftY = 0F

        // 三角點 1 & 2 (依起點順時針)
        val triangleX1 = startX - arrowH.toFloat()
        val triangleY1 = startY + arrowW / 2F
        val triangleX2 = startX - arrowH.toFloat()
        val triangleY2 = startY - arrowW / 2F

        // 設置 Path
        path.moveTo(startX, startY)
        path.lineTo(triangleX1, triangleY1)
        path.lineTo(bottomRightX, bottomRightY - tipLayoutRadius)
        path.cubicTo(
            bottomRightX, bottomRightY - tipLayoutRadius,
            bottomRightX, bottomRightY,
            bottomRightX - tipLayoutRadius, bottomRightY
        )
        path.lineTo(bottomLeftX + tipLayoutRadius, bottomLeftY)
        path.cubicTo(
            bottomLeftX + tipLayoutRadius, bottomLeftY,
            bottomLeftX, bottomLeftY,
            bottomLeftX, bottomLeftY - tipLayoutRadius
        )
        path.lineTo(topLeftX, topLeftY + tipLayoutRadius)
        path.cubicTo(
            topLeftX, topLeftY + tipLayoutRadius,
            topLeftX, topLeftY,
            topLeftX + tipLayoutRadius, topLeftY
        )
        path.lineTo(topRightX - tipLayoutRadius, topRightY)
        path.cubicTo(
            topRightX - tipLayoutRadius, topRightY,
            topRightX, topRightY,
            topRightX, topRightY + tipLayoutRadius
        )
        path.lineTo(triangleX2, triangleY2)
        path.close()

        canvas.drawPath(path, paint)
    }

    enum class ArrowOrientation {
        TOP,
        BOTTOM,
        START,
        END
    }
}