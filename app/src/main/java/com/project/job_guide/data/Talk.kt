package com.project.job_guide.data

data class Talk(
    var title: String?="",
    var profileImageUrl: String?="",
    var expandable :Boolean = false,
    var date : String?="",
    var time : String?="",
    var venue : String?=""
)
