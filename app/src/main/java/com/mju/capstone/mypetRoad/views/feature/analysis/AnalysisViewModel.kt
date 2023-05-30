package com.mju.capstone.mypetRoad.views.feature.analysis

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.databinding.ObservableField
import com.mju.capstone.mypetRoad.util.Config
import com.mju.capstone.mypetRoad.util.DateFormatter
import java.time.LocalDate
import java.util.*
import kotlin.math.floor

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class AnalysisViewModel @Inject constructor(

): ViewModel(){
    //entire log 변수
    val startDate = ObservableField<String>()
    val endDate = ObservableField<String>()
    val WalkingCardKm = ObservableField<String>()
    val WalkingCardCnt = ObservableField<String>()
    val WalkingCardMin = ObservableField<String>()
    val WalkingCardKcal = ObservableField<String>()

    //detail 변수
    var distance = ObservableField<String>()
    var minTime = ObservableField<String>()
    var calories = ObservableField<String>()

    var walkedDate = ObservableField<String>()
    var endTime = ObservableField<String>()
    var startTime = ObservableField<String>()

    fun entireUpdateText() { //entire log 업뎃
        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0
        val startDateStr = String.format("%tY.%tm.%td",
            Config.walkList[0].walkDate,
            Config.walkList[0].walkDate,
            Config.walkList[0].walkDate
        )
        val endDateStr = String.format("%tY.%tm.%td",
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.now()
        )

        for(i in Config.walkList){
            totalM += i.activity.travelDistance
            cnt += 1
            totalSec += i.activity.walkedTime.toLong()
            totalKcal += i.activity.burnedCalories
        }
        startDate.set(startDateStr)
        endDate.set(endDateStr)
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    fun monthlyFirstUpdateText() { //monthly log 첫 업뎃
        val firstDayOfMonth = DateFormatter.getFirstDayOfMonth(Date()) // 이번 달의 첫 날

        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0
        val startDateStr = String.format("%tY.%tm.%td",
            firstDayOfMonth,
            firstDayOfMonth,
            firstDayOfMonth
        )
        val endDateStr = String.format("%tY.%tm.%td",
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.now()
        )

        for(i in Config.walkList){
            if (i.walkDate >= firstDayOfMonth) {
                totalM += i.activity.travelDistance
                cnt += 1
                totalSec += i.activity.walkedTime.toLong()
                totalKcal += i.activity.burnedCalories
            }
        }
        startDate.set(startDateStr)
        endDate.set(endDateStr)
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    fun monthlyNextUpdateText(date: Date) { //monthly log 변경 업뎃
        val firstDayOfMonth = DateFormatter.getFirstDayOfMonth(date) // 해당 달의 첫 날
        val lastDayOfMonth = DateFormatter.getLastDayOfMonth(date) // 해당 달의 마지막 날

        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0
        val startDateStr = String.format("%tY.%tm.%td",
            firstDayOfMonth,
            firstDayOfMonth,
            firstDayOfMonth
        )
        val endDateStr = String.format("%tY.%tm.%td",
            lastDayOfMonth,
            lastDayOfMonth,
            lastDayOfMonth
        )

        for(i in Config.walkList){
            if (i.walkDate in firstDayOfMonth..lastDayOfMonth) {
                totalM += i.activity.travelDistance
                cnt += 1
                totalSec += i.activity.walkedTime.toLong()
                totalKcal += i.activity.burnedCalories
            }
        }
        startDate.set(startDateStr)
        endDate.set(endDateStr)
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    fun weeklyUpdateText() { //weekly log 업뎃
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7) // 현재 날짜로부터 7일 전 날짜로 설정
        val oneWeekAgo = calendar.time // 최근 일주일 전 날짜

        // 텍스트 업데이트 로직
        var totalM = 0F
        var cnt = 0
        var totalSec : Long = 0
        var totalKcal = 0
        val startDateStr = String.format("%tY.%tm.%td",
            oneWeekAgo,
            oneWeekAgo,
            oneWeekAgo
        )
        val endDateStr = String.format("%tY.%tm.%td",
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.now()
        )

        for(i in Config.walkList){
            if (i.walkDate >= oneWeekAgo) {
                totalM += i.activity.travelDistance
                cnt += 1
                totalSec += i.activity.walkedTime.toLong()
                totalKcal += i.activity.burnedCalories
            }
        }
        startDate.set(startDateStr)
        endDate.set(endDateStr)
        WalkingCardKm.set((floor((totalM*100).toDouble()/1000) /100.0).toString())
        WalkingCardCnt.set(cnt.toString())
        WalkingCardMin.set((totalSec / 60).toString())
        WalkingCardKcal.set(totalKcal.toString())
    }

    fun detailUpdateText() {
        // 텍스트 업데이트 로직
        val dateStr = String.format("%tY. %tm. %td (%ta)",
            Config.startDate,
            Config.startDate,
            Config.startDate,
            Config.startDate
        )
        val startTimeStr = String.format("%tH시 %tM분", Config.startDate, Config.startDate)
        val endTimeStr = String.format("%tH시 %tM분", Config.endDate, Config.endDate)
        val durationTime = (Config.endTime - Config.startTime) / 1000
        val timeStr = String.format("%d", durationTime/60)

        Log.d("시작시간", Config.startDate.toString())
        Log.d("종료시간", Config.endDate.toString())
        walkedDate.set(dateStr)
//        endTime.set(endTimeStr)
//        startTime.set(startTimeStr)
//        distance.set(String.format("%.2f", Distance.totalDistance/1000))
//        minTime.set(timeStr)
//        calories.set(Calories.totalCalories.toString()
    }
}