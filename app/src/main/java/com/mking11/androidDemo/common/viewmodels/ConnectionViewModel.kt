package com.mking11.androidDemo.common.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.models.ConnectionEnums
import com.mking11.androidDemo.common.repositories.ConnectionRepository
import com.mking11.androidDemo.common.repositories.NetworkConnectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository,
    private val networkConnection: NetworkConnectionRepository,
    private val firebaseCrash: FirebaseCrash,
) : ViewModel() {

    private var timer: CountDownTimer? = null

    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " ConnectionViewModel.kt  25: ")
    }


    fun observerConnection() = viewModelScope.launch(handler) {
        networkConnection.asFlow().catch { e ->
            firebaseCrash.setErrorToFireBase(e, " ConnectionViewModel.kt  33: ")
        }.collect { connectionEnums ->
            when (connectionEnums) {
                ConnectionEnums.Connected -> {
                    timer?.cancel()
                }
                ConnectionEnums.Check -> {
                    delayAndCheck()
                }
                ConnectionEnums.Disconnected -> {
                    delayAndCheck()
                }
            }
        }
    }


    override fun onCleared() {
        try {
            timer?.cancel()
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "onCleared ConnectionViewModel.kt  59: ")
        }
        super.onCleared()
    }

    // initialize a timer
    private fun initTimer() {
        //initialize timer with delay and interval
        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                try {
                    // on time elapsed check if the network is connected
                    if (connectionRepository.checkIfIsConnected()) {
                        networkConnection.setConnectionState(ConnectionEnums.Connected)
                    } else {
                        networkConnection.setConnectionState(ConnectionEnums.Disconnected)
                    }
                    // cancel the timer
                    timer?.cancel()
                } catch (e: Exception) {
                    firebaseCrash.setErrorToFireBase(e, "onFinish ConnectionViewModel.kt  75: ")
                }
            }
        }
    }

    //delay and check the network state
    private fun delayAndCheck() {
        try {//check if the timer is initialized
            if (timer == null) {
                //if null initialize timer with time allocated
                initTimer()
                //start timer
                timer?.start()
            } else {
                //if timer is initialized cancel timer
                timer?.cancel()
                // start another instance of it
                timer?.start()
            }
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "delayAndCheck ConnectionViewModel.kt  96: ")
        }
    }


}
