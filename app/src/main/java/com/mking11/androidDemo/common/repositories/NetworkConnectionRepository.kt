@file:Suppress("DEPRECATION")

package com.mking11.androidDemo.common.repositories

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.models.ConnectionEnums
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("MissingPermission")
class NetworkConnectionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseCrash: FirebaseCrash,
) :
    LiveData<ConnectionEnums>() {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback


    override fun onInactive() {
        super.onInactive()
        try {
            if (this::networkCallback.isInitialized) {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }

        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "Network Connection")
        }

    }

    fun setConnectionState(state: ConnectionEnums) {
        postValue(state)
    }

    override fun onActive() {
        super.onActive()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallBack())
            }
            Build.VERSION.SDK_INT < Build.VERSION_CODES.N -> {
                lollipopNetworkRequest()
            }
            else -> {
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallBack()
        )

    }

    init {
        postValue(ConnectionEnums.Check)
    }

    // on connectivity changed
    @SuppressLint("SyntheticAccessor")
    private fun connectivityManagerCallBack(): ConnectivityManager.NetworkCallback {
        networkCallback = object : ConnectivityManager.NetworkCallback(),
            ConnectivityManager.OnNetworkActiveListener {
            //Called when a network disconnects or otherwise no longer satisfies this request or callback.
            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(ConnectionEnums.Disconnected)

            }

            //Called if no network is found within the timeout time specified in
            override fun onUnavailable() {
                super.onUnavailable()
                postValue(ConnectionEnums.Disconnected)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                postValue(ConnectionEnums.Check)
            }

            //Called when the framework connects and has declared a new network ready for use.
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(ConnectionEnums.Check)
            }

            override fun onNetworkActive() {
                postValue(ConnectionEnums.Check)
            }


        }
        return networkCallback

    }

    // on network receiver broad caster
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }
    }

    // update status
    internal fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(if (activeNetwork?.isConnected == true) ConnectionEnums.Connected else ConnectionEnums.Check)
    }


}