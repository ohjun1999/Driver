package com.kingbus.driver.dataclass

data class UserDataClass(
    var name: String? = null,
    //전세버스, 일반회원, 제휴업체
    var loginCheck: String? = null,
    //전세버스, 일반회원, 제휴업체
    var id: String? = null,
    //전세버스, 일반회원, 제휴업체
    var password: String? = null,
    //전세버스, 일반회원, 제휴업체
    var type: String? = null,
    //전세버스, 일반회원, 제휴업체
    var company: String? = null,
    //전세버스, 제휴업체
    var province: String? = null,
    //전세버스, 일반회원, 제휴업체
    var city: String? = null,
    //전세버스, 일반회원, 제휴업체?
    var phoneNum: String? = null,
    //전세버스, 일반회원, 제휴업체
    var profileImg: String? = null,
    //전세버스, 일반회원, 제휴업체
    var companyImg: String? = null,
    //전세버스, 일반회원, 제휴업체
    var birth: String? = null,
    //일반회원
    var gender: String? = null,
    //일반회원
    var sector: String? = null,
    //제휴업체
    var companyAdr: String? = null,
    //제휴업체
    var uid: String? = null,
    //전세버스, 일반회원, 제휴업체
    var writeCount: Int? = null,

    var imageUri: String? = null,

    var submit: ArrayList<Any>? = ArrayList(),

    )
