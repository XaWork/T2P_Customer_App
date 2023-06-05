package me.taste2plate.app.customer.common

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class CompletionGenericLiveData<T> : LiveData<Resource<T>>(), OnCompleteListener<T> {
    init {
        value = Resource(Status.LOADING)
    }

    override fun onComplete(task: Task<T>) {
        if (task.isSuccessful) {
            if (task.result != null) {
                setValue(Resource(task.result!!))
            } else {
                setValue(Resource(Status.SUCCESS))
            }

        } else {
            setValue(Resource(task.exception!!))
        }
    }
}

