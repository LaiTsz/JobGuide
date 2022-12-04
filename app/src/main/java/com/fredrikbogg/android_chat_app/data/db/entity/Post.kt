package com.fredrikbogg.android_chat_app.data.db.entity

import com.google.firebase.database.PropertyName


data class Post(
    @get:PropertyName("lastMessage") @set:PropertyName("lastMessage") var lastMessage: String = "",
    @get:PropertyName("topic") @set:PropertyName("topic") var topic: String = "",
    @get:PropertyName("id") @set:PropertyName("id") var id: String = ""

)
