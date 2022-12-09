package com.project.job_guide.data.model

import com.project.job_guide.data.db.entity.Chat
import com.project.job_guide.data.db.entity.UserInfo

data class ChatWithUserInfo(
    var mChat: Chat,
    var mUserInfo: UserInfo
)
