package com.emericoapp.gituser.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val isNote: Boolean,
    val note: String
)
