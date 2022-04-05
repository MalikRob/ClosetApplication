package com.example.closet

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.closet.database.ClosetDatabase
import com.example.closet.ClothingItem
import java.io.File
import java.util.*
import java.util.concurrent.Executors

/**
 * This class encapsulates the logic for accessing data from a single sourcec or a set of sources.
 * For our application in a further state the data will be accessed from a local
 * database within the application.
 * This class on March 23, 2022 will be able to handle fetching data from the database.
 *
 * THIS IS NOT OPTIMAL FOR LONG TERM DATA STORAGE. TEMP SOLUTION ATM.
 */

private const val DATABASE_NAME = "closet-database"

class ClosetRepository private constructor(context: Context) {

    /**
     * Creates an Implementation of the abstract ClosetDatabase.kt with 3 parameters
     * 1. Passing in the application context so it can live longer than the activity classes (Pages)
     * 2. Database class that we want the Room database to use and create for us.
     * 3. Constant STRING to reference the name of the database
     *
     * Followed by a chain function to actually build the application's database to use.
     */
    private val database : ClosetDatabase = Room.databaseBuilder(
        context.applicationContext,
        ClosetDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val closetDAO = database.closetDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir

    fun getPhotoFile(clothingItem: ClothingItem): File = File(filesDir, clothingItem.photoFileName)

    fun getClothingItems(): LiveData<List<ClothingItem>> = closetDAO.getClothingItems()

    fun getClothingItem(id: UUID): LiveData<ClothingItem?> = closetDAO.getClothingItem(id)

    fun updateClothingItem(clothingItem: ClothingItem) {
        executor.execute {
            closetDAO.updateClothingItem(clothingItem)
        }
    }

    //Add Clothing Item
    fun addClothingItem(clothingItem: ClothingItem) {
        executor.execute {
            closetDAO.addClothingItem(clothingItem)
        }
    }

    companion object {
        private var INSTANCE: ClosetRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ClosetRepository(context)
            }
        }

        fun get(): ClosetRepository {
            return INSTANCE ?:
            throw IllegalStateException("ClosetRepository must be initialized")
        }
    }
}