package com.smartphoneprogamming.afinal.List

import java.util.*

data class Writing(var question : String ?= null, var text : String ?= null, var nick : String?=null, var date : Date?= null, var lock : Boolean ?= null){
    companion object {
        const val MINE = 0
        const val YOUR = 1
    }
}

