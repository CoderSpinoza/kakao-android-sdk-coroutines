package com.kakao.sdk.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 11..
 */
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return viewModels[modelClass]?.get() as T
        } catch (e: Throwable) {
            throw IllegalArgumentException("Wrong ViewModel")
        }
    }

}