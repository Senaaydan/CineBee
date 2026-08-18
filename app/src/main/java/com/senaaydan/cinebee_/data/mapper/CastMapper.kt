package com.senaaydan.cinebee_.data.mapper

import com.senaaydan.cinebee_.data.remote.CastDto
import com.senaaydan.cinebee_.domain.model.Actor


fun CastDto.toActor(): Actor {
    return Actor(
        name = name,
        characterName = characterName,
        imageUrl = if (profilePath != null) {
            "https://image.tmdb.org/t/p/w500$profilePath"
        } else {
            ""
        }

    )

}