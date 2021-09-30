package com.mking11.androidDemo.common.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.mking11.androidDemo.common.firebaseutils.FirebaseRealDb
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("SyntheticAccessor")
@Suppress("DEPRECATION")

class ConnectionRepository @Inject constructor(
    @ApplicationContext val context: Context,
    realDb: FirebaseRealDb
) {

    private val docRef = realDb.connectionState()
    private var _connectionState = MutableLiveData<Boolean>()
    private val connectionState: LiveData<Boolean>
        get() = _connectionState


    private val listener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            _connectionState.value = false
        }


        override fun onDataChange(p0: DataSnapshot) {
            val connected = p0.getValue<Boolean>()
            _connectionState.value = connected ?: false
        }

    }

    init {
        _connectionState.value = true
        observeConnection()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


    fun checkIfIsConnected(): Boolean {
        val connectionState = connectionState.value
        val isAvailable = isInternetAvailable(context)
        return connectionState == true && isAvailable
    }


    private fun observeConnection() {
        docRef.addValueEventListener(listener)
    }

    fun clearOnExit() {
        docRef.removeEventListener(listener)
    }

}