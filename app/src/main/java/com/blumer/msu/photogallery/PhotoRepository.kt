package com.blumer.msu.photogallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blumer.msu.photogallery.api.FlickrApi
import com.blumer.msu.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
//added to help with jetpack paging
private const val PAGE_SIZE = 100
class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }

    fun fetchPhotosPaging(): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PhotoPagingSource(flickrApi) }
        ).flow
    }
}
