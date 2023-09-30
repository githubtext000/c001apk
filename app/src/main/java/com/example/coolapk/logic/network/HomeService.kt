package com.example.coolapk.logic.network

import com.example.coolapk.logic.model.FeedContentResponse
import com.example.coolapk.logic.model.HomeFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HomeService {

    @GET("/v6/main/indexV8?ids=")
    @Headers(
        "X-Requested-With: XMLHttpRequest",
        "X-App-Id: com.coolapk.market",
        "X-App-Device: wMxASdvl1ciJGbv92QgsDM2gTOH1STTByOn5Wdz1WYzByOn5Wdz1WYzByO3AjO4UjOxkjOCNkOBZkO2kDI7AyOgsjYkRmZ4MmNxADN0YWYllDZ",
        "X-App-Token: v2JDJhJDEwJE1TNDJPVFl3TXpRNE1rVTUvN2M4MXVDTHMua2NyTWFEV09RbXJVUFZWSm5FTzlCU0ZVOS5T"
    )
    fun getHomeFeed(
        @Query("page") page: Int,
        @Query("firstLaunch") firstLaunch: Int,
    ): Call<HomeFeedResponse>

    @GET("/v6/feed/detail?tmp=1")
    @Headers(
        "X-Requested-With: XMLHttpRequest",
        "X-App-Id: com.coolapk.market",
        "X-App-Device: wMxASdvl1ciJGbv92QgsDM2gTOH1STTByOn5Wdz1WYzByOn5Wdz1WYzByO3AjO4UjOxkjOCNkOBZkO2kDI7AyOgsjYkRmZ4MmNxADN0YWYllDZ",
        "X-App-Token: v2JDJhJDEwJE1TNDJPVFl3TXpRNE1rVTUvN2M4MXVDTHMua2NyTWFEV09RbXJVUFZWSm5FTzlCU0ZVOS5T"
    )
    fun getFeedContent(
        @Query("id") id: String
    ): Call<FeedContentResponse>

    @GET("/v6/feed/replyList?tmp=1")
    @Headers(
        "X-Requested-With: XMLHttpRequest",
        "X-App-Id: com.coolapk.market",
        "X-App-Device: wMxASdvl1ciJGbv92QgsDM2gTOH1STTByOn5Wdz1WYzByOn5Wdz1WYzByO3AjO4UjOxkjOCNkOBZkO2kDI7AyOgsjYkRmZ4MmNxADN0YWYllDZ",
        "X-App-Token: v2JDJhJDEwJE1TNDJPVFl3TXpRNE1rVTUvN2M4MXVDTHMua2NyTWFEV09RbXJVUFZWSm5FTzlCU0ZVOS5T"
    )
    fun getFeedContentReply(
        @Query("id") id: String,
        @Query("discussMode") discussMode: Int,
        @Query("listType") listType: String,
        @Query("page") page: Int
    ): Call<HomeFeedResponse>

}