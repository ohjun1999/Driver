package com.kingbus.driver.dataclass

data class TravelDataClass(
    var title: String? = null,
    var titleImg: String? = null,
    var contextImg: ArrayList<Any>? = ArrayList(),
    var pubDate: String? = null,
    var travelStart: String? = null,
    var purchaseStart: String? = null,
    var purchaseEnd: String? = null,
    var uid: String? = null,
    var tag1: String? = null,
    var tag2: String? = null,
    var tag3: String? = null,
)