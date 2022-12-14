package com.kingbus.driver.dataclass

data class PostDataClass(
    var title: String? = null,
    var name: String? = null,
    var pubDate: String? = null,
    var comment: Int? = null,
    var uid: String? = null,
    var context: String? = null,
    var img: String? = null,
    var view: Int? = null,
    var type: String? = null,
    var postUid: String? = null,
    var commentList: ArrayList<Any>? = ArrayList(),
    var imgLink: ArrayList<Any>? = ArrayList(),
    var block: ArrayList<Any>? = ArrayList(),
    var declaration: ArrayList<Any>? = ArrayList(),
)
