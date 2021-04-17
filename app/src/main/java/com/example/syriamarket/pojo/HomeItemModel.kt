package com.example.syriamarket.pojo

import com.example.syriamarket.pojo.cats.DataX
import com.example.syriamarket.pojo.products.DataXP

data class HomeItemModel(val homeCatsID:String,val homeCatsName:String, val homeProducts:  ArrayList<DataXP>)
