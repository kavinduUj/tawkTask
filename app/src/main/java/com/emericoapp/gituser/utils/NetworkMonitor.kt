package com.emericoapp.gituser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Handler
import android.os.Looper
import android.widget.Toast

/**
 * this is the real time network monitoring class i have user to idintify network status
 */
class NetworkMonitor(context: Context, lifecycle: Lifecycle) : DefaultLifecycleObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isConnected by mutableStateOf(false)

    private val mainHandler = Handler(Looper.getMainLooper())

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            mainHandler.post {
                isConnected = true      // from here i will be able to indicate network back
            }
        }

        override fun onLost(network: Network) {
            mainHandler.post {
                isConnected = false
                Toast.makeText(context, "Connection lost!", Toast.LENGTH_SHORT).show()      // from here i will be able to indicate network lost
            }
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            mainHandler.post {
                isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
    }

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
