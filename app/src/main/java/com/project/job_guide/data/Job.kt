package com.project.job_guide.data

data class Job(
    var title: String?="",
    var profileImageUrl: String="",
    var expandable :Boolean = false,
    var deadline : String="",
    var salary : String="",
    var link : String="",
    var career : String=""
)
