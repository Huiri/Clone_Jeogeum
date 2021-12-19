package com.smartphoneprogamming.afinal.List

data class Writing(val type : Int, var question : String ?= null, var text : String ?= null, var nick : String?=null){
    companion object {
        const val MINE = 0
        const val YOUR = 1
    }
}

