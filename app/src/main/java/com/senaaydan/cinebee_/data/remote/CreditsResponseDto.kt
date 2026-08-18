package com.senaaydan.cinebee_.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsResponseDto (
    val id: Int,
    val cast: List<CastDto>

)
@JsonClass(generateAdapter = true)
data class CastDto(
    val id: Int,
    val name: String,

    @Json(name = "character")
    val characterName: String,

    @Json(name = "profile_path")
    val profilePath: String?


)
