package com.example.news.data.model

import com.google.gson.annotations.SerializedName

/*@Entity(tableName = "News", primaryKeys = ["_id"])*/
data class ResponseNews(
    /*@ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,*/

    @SerializedName("status")
    val status: String?,

    @SerializedName("totalResults")
    val totalResults: Int?,

    val articles: List<News>

) {
    data class News(
        @SerializedName("source")
        val source: Source,

        @SerializedName("author")
        val author: String?,

        @SerializedName("title")
        val title: String?,

        @SerializedName("description")
        val description: String?,

        @SerializedName("url")
        val url: String?,

        @SerializedName("urlToImage")
        val urlToImage: String?,

        @SerializedName("publishedAt")
        val publishedAt: String?,

        @SerializedName("content")
        val content: String?
    ) {

        data class Source(
            @SerializedName("id")
            val id: Any?,

            @SerializedName("name")
            val name: String?
        )

    }


}
