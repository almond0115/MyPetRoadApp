package com.mju.capstone.mypetRoad

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

//@HiltAndroidApp
//class PetRoadApplication: Application(), Configuration.Provider {
//
//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory
//
//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//    }
//}
@HiltAndroidApp
class PetRoadApplication : Application(){
}