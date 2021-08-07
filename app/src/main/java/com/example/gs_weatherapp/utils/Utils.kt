package com.example.gs_weatherapp.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun readJSONFromAsset(context: Context): String? {
        val json: String
        try {
            val inputStream: InputStream = context.assets.open("city.list.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDate(dateInSeconds: Long, dateFormat: String): String {
        return try {
            val formatter = SimpleDateFormat(dateFormat)
            val cal = Calendar.getInstance()
            cal.timeInMillis = dateInSeconds * 1000 // get date to millis
            formatter.format(cal.time)
        } catch (ex: Exception) {
            print(ex.message)
            return dateInSeconds.toString()
        }
    }

    fun formatDateString(date: String, inputFormat: String, desiredFormat: String): String {
        try {
            val oneWayTripDate: Date?
            val input = SimpleDateFormat(inputFormat, Locale.US)
            val output = SimpleDateFormat(desiredFormat, Locale.US)
            oneWayTripDate = input.parse(date)
            return output.format(oneWayTripDate!!)
        } catch (e: Exception) {
            print(e.message)
        }
        return date
    }

    fun checkNetwork(context: Context?): Boolean {
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    // region "alert dialogs"
    fun showDialogActions(
        context: Context,
        msg: String,
        posAction: String,
        negAction: String,
        posRunnable: () -> Unit,
        negRunnable: (() -> Unit)?
    ): AlertDialog? {
        val diag = AlertDialog.Builder(context)
        diag.setMessage(msg)
        diag.setCancelable(false)
        diag.setPositiveButton(posAction) { dialog, _ ->
            dialog.dismiss()
            Handler().post(posRunnable)
        }
        diag.setNegativeButton(negAction) { dialog, _ ->
            dialog.dismiss()
            if(negRunnable != null) {
                Handler().post(negRunnable)
            }
        }
        return diag.show()
    }

    fun showAlertDialog(activity: Context, msg: String, action: String, finishActivity: Boolean) {
        AlertDialog.Builder(activity).apply {
            setMessage(msg)
            setCancelable(false)
            setPositiveButton(action) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }

    }
    // endregion
}
