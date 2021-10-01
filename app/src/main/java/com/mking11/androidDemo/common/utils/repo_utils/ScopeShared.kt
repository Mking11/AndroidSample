package com.mking11.androidDemo.common.utils.repo_utils

import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


open class ScopeShared(
    private val className: String,
    private val firebaseCrash: FirebaseCrash,
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {
    open val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " $className : ")
    }

    open fun closeRepo() {
        try {
            scope.cancel()
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "${className}.kt close Repository  18: ")
        }
    }

}