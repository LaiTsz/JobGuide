package com.project.job_guide.data.model

data class CreateUser(
    var displayName: String = "",
    var email: String = "",
    var password: String = "",
    var major:String="",
    var career: String=""
)