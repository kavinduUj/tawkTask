package com.emericoapp.gituser.utils

import com.emericoapp.gituser.domain.model.User
import com.emericoapp.gituser.data.model.UserDto
import com.emericoapp.gituser.data.model.UserEntity


fun UserDto.toEntityModel() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatar_url,
    isNote = false,
    note = ""
)

fun UserEntity.toDomainModel() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    isNote = isNote,
    note = note
)


