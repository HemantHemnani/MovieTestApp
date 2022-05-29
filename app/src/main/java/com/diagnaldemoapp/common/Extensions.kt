package com.diagnaldemoapp.common

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.diagnaldemoapp.R
import java.io.IOException
import java.io.InputStream

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun ImageView.loadStrImage(imgName: String, errorResId: Int?) {
    val imageView = this
    if (imgName.isNullOrEmpty()) return
    if (errorResId == null) return

    val res: Resources = resources
    val mDrawableName = imgName; //"financial"
    val resID: Int = res.getIdentifier(mDrawableName.replace(".jpg",""), "mipmap", context.getPackageName())
    if (resID != 0) {
        val drawable: Drawable = ContextCompat.getDrawable(context, resID)!!
        imageView.setImageDrawable(drawable)
    } else {
        imageView.setImageResource(R.mipmap.placeholder_for_missing_posters)
    }
}

fun Context.movieNameSpannable( completeStr:String? , qusStr:String): Spannable
{
    val spannable: Spannable = SpannableString(completeStr)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.yellow)),
            0,
            qusStr.length ,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)),
            qusStr.length,
            completeStr!!.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            qusStr.length,
            completeStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return spannable
}
