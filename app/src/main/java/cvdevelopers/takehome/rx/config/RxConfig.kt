package cvdevelopers.takehome.rx.config

import android.util.Log
import cvdevelopers.takehome.common.Constants
import io.reactivex.plugins.RxJavaPlugins

fun initRx() {
    RxJavaPlugins.setErrorHandler { throwable ->
        Log.d(Constants.LOG_TAG, throwable.message ?: "Unhandled Rx exception")
    }
}
