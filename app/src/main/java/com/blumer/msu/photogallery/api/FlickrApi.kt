package com.blumer.msu.photogallery.api

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.blumer.msu.photogallery.api.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "40b957b8ad84aaa6aec27b75dd726a31"

interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&nojsoncallback=1" +
            "&extras=url_s" +
                "&per_page=$PAGE_SIZE"
    )
    suspend fun fetchPhotos(@Query("page") page: Int): FlickrResponse
}
