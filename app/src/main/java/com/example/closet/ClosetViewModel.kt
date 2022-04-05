package com.example.closet

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

import com.example.closet.ClosetRepository
import com.example.closet.Closet
import com.example.closet.ClothingItem
import java.io.File
import java.util.*

class ClosetViewModel: ViewModel() {

    private val closetRepository = ClosetRepository.get()
    val clothingItemListLiveData = closetRepository.getClothingItems()

    fun addClothingItem(clothingItem: ClothingItem) {
        closetRepository.addClothingItem(clothingItem)
    }

}