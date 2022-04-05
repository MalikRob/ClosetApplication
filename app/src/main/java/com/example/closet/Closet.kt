package com.example.closet

import android.media.Image
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI
import java.util.*

/**
 * Entitiy List:
 * 1. User
 * 2. Closet
 * 3. Clothing Item
 * 4. Clothing Type
 * 5. Color
 * 6.
 */

//Sprint 2 Class. Only one to be used at the moment.
@Entity
data class ClothingItem(@PrimaryKey val id: UUID = UUID.randomUUID(),
                        var clothingType: String = "",
                        var color: String = "",
                        var description: String = "")
{
    val photoFileName
    get() = "IMG_$id.jpg"
}


@Entity
data class User (@PrimaryKey val User_id: UUID = UUID.randomUUID(),
                 val FirstName: String = "",
                 val LastName: String = "",
                 val Password: String = "",
                 val Email: String = "")
{}
// Should this be a child of ClothingItem? or separate? Style is dependent on ClothingItem
@Entity
data class Style (@PrimaryKey val Style_Id: UUID = UUID.randomUUID(),
                    val Clothing_item_id: UUID,
                    val Style_Name: String = "")
{}

@Entity
data class Closet (@PrimaryKey val Closet_Id: UUID = UUID.randomUUID(),
                    val Clothing_item_id: UUID,
                    val User_id: UUID)
{}


@Entity
data class ClothingType (@PrimaryKey val Clothing_Type_Id: UUID = UUID.randomUUID(),
                        val Clothing_Type_Name: String = "")
{}

@Entity
data class Color (@PrimaryKey val Color_Id: UUID = UUID.randomUUID(),
                    val ColorName: String = "")
{}

