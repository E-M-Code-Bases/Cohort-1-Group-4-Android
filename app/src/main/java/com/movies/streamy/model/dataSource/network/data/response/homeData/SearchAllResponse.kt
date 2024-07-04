package com.movies.streamy.model.dataSource.network.data.response.homeData

import com.google.gson.annotations.SerializedName

data class SearchAllResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchAllResult>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)