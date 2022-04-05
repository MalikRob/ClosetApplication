package com.example.closet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class ClothingItemDetailViewModel() : ViewModel() {

    private val clothingItemRepository = ClosetRepository.get()
    private val clothingItemIdLiveData = MutableLiveData<UUID>()

    var clothingItemLiveData: LiveData<ClothingItem?> =
        Transformations.switchMap(clothingItemIdLiveData) {
                id -> clothingItemRepository.getClothingItem(id)
        }

    fun loadClothingItem(clothingItemId: UUID) {
        clothingItemIdLiveData.value = clothingItemId
    }

    fun saveClothingItem(clothingItem: ClothingItem){
        clothingItemRepository.updateClothingItem(clothingItem)
    }
    fun getPhotoFile(clothingItem: ClothingItem): File {
        return clothingItemRepository.getPhotoFile(clothingItem)
    }
}