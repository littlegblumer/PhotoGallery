package com.blumer.msu.photogallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blumer.msu.photogallery.api.GalleryItem
import com.blumer.msu.photogallery.PhotoRepository
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 100

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()

    val photoList: Flow<PagingData<GalleryItem>> =
        photoRepository.fetchPhotosPaging()
            .cachedIn(viewModelScope)
}
