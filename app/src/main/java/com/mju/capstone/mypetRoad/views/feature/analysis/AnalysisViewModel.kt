package com.mju.capstone.mypetRoad.views.feature.analysis

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.databinding.ObservableField
import com.mju.capstone.mypetRoad.util.Config
import com.mju.capstone.mypetRoad.util.DateFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor

@HiltViewModel
class AnalysisViewModel @Inject constructor(

): ViewModel(){
    //entire log 변수
    val startDate = ObservableField<String>()
    val endDate = ObservableField<String>()
    val WalkingCardKm = ObservableField<String>()
    val WalkingCardCnt = ObservableField<String>()
    val WalkingCardMin = ObservableField<String>()
    val WalkingCardKcal = ObservableField<String>()

    //weekly_log_card 변수


    @RequiresApi(Build.VERSION_CODES.O)
    fun entireUpdateText() { //entire log 업뎃
        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0

        for(i in Config.walkList){
            totalM += i.activity.travelDistance
            cnt += 1
            totalSec += i.activity.walkedTime.toLong()
            totalKcal += i.activity.burnedCalories
        }
        startDate.set(DateFormatter.dateToString(Config.walkList[0].walkDate)?.take(4))
        endDate.set(LocalDate.now().year.toString())
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthlyUpdateText() { //entire log 업뎃
        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0

        for(i in Config.walkList){
            totalM += i.activity.travelDistance
            cnt += 1
            totalSec += i.activity.walkedTime.toLong()
            totalKcal += i.activity.burnedCalories
        }
        startDate.set(DateFormatter.dateToString(Config.walkList[0].walkDate)?.take(4))
        endDate.set(LocalDate.now().year.toString())
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun weeklyUpdateText() { //entire log 업뎃
        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0

        for(i in Config.walkList){
            totalM += i.activity.travelDistance
            cnt += 1
            totalSec += i.activity.walkedTime.toLong()
            totalKcal += i.activity.burnedCalories
        }
        startDate.set(DateFormatter.dateToString(Config.walkList[0].walkDate)?.take(4))
        endDate.set(LocalDate.now().year.toString())
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }
}