package com.project.job_guide.data.db.entity

import com.google.firebase.database.PropertyName


data class Post(
    @get:PropertyName("lastComment") @set:PropertyName("lastComment") var lastComment: String = "",
    @get:PropertyName("topic") @set:PropertyName("topic") var topic: String = "",
    @get:PropertyName("id") @set:PropertyName("id") var id: String = ""

)
