package com.kingbus.driver.dataclass

data class NoticeDataClass(
    var title: String? = null,
    var pubDate: String? = null,
    var comment: Int? = null,
    var context: String? = null,
    var type: String? = null,
    var noticeUid: String? = null,
    var commentList: ArrayList<Any>? = ArrayList(),
)
