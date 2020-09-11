package studio.zewei.willy.arrowtooltiplayout

import android.app.Activity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Created by Willy on 2020/04/14.
 */

/**
 * 將 DP 轉為 PX
 */
fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()