package com.mju.capstone.mypetRoad.domain.model

import java.util.Date

data class GpsModel(
    override val id: Long,
    override val createTime: Date,
    var latitude: Double,
    var longitude: Double,
    var altitude: Double
): Model(id, createTime){
}