package com.m7.nyarticles.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Long,
    val title: String,
    val abstract: String,
    val byline: String,
    val published_date: String,
    val media: List<Media>?
) : Parcelable {

    val thumbnailUrl: String?
        get() = media?.firstOrNull()?.media_metadata?.firstOrNull()?.url

    val imgUrl: String?
        get() = media?.firstOrNull()?.media_metadata?.lastOrNull()?.url

    @Parcelize
    data class Media(
        @SerializedName("media-metadata")
        val media_metadata: List<MetaData>
    ) : Parcelable {

        @Parcelize
        data class MetaData(
            val url: String
        ) : Parcelable
    }
}
