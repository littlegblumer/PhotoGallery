package com.blumer.msu.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blumer.msu.photogallery.api.FlickrApi
import com.blumer.msu.photogallery.api.GalleryItem
import retrofit2.HttpException
import java.io.IOException
//read this to learn about how this works: developer.android.com/topic/libraries/architecture/paging
//added this, main changes from chatgpt. i had a lot of errors when trying to make this change without its help


private const val INITIAL_PAGE = 1
private const val PAGE_SIZE = 100

class PhotoPagingSource(private val flickrApi: FlickrApi) : PagingSource<Int, GalleryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        try {
            val nextPage = params.key ?: INITIAL_PAGE
            val response = flickrApi.fetchPhotos(nextPage)
            val photos = response.photos.galleryItems

            return LoadResult.Page(
                data = photos,
                prevKey = if (nextPage == INITIAL_PAGE) null else nextPage - 1,
                nextKey = if (photos.isEmpty()) null else nextPage + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
