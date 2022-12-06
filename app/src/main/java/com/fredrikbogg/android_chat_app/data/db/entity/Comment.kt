package com.fredrikbogg.android_chat_app.data.db.entity

import com.google.firebase.database.PropertyName
import java.util.*


data class Comment(
    @get:PropertyName("displayName") @set:PropertyName("displayName") var displayName: String = "",
    @get:PropertyName("context") @set:PropertyName("context") var context: String = "",
    @get:PropertyName("epochTimeMs") @set:PropertyName("epochTimeMs") var epochTimeMs: Long = Date().time

    )
