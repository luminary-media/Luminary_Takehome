package cvdevelopers.takehome.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposable : LifecycleObserver {
    private lateinit var compositeDisposable: CompositeDisposable

    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        if (!::compositeDisposable.isInitialized) {
            throw NotImplementedError("Must be bound to lifecycle first")
        }

        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.dispose()
    }
}

fun Disposable.autoDispose(autoDisposable: AutoDisposable): Disposable {
    autoDisposable.add(this)
    return this
}
