@file:Suppress("DEPRECATION")

package com.example.closet


//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.io.IOException
import java.util.*


private const val ARG_CLOTHING_ITEM_ID = "clothing_item_id"

class EditClothingPageFragment: Fragment() {

    private lateinit var clothingItem: ClothingItem
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri
    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE = 2
    private lateinit var clothingTypeField: EditText
    private lateinit var colorField: EditText
    private lateinit var clothingDescriptionField: EditText
    private lateinit var photoView: ImageView
    private lateinit var imageGallery: ImageButton
    private lateinit var captureImage: ImageButton


    private val clothingItemDetailViewModel: ClothingItemDetailViewModel by lazy {
        ViewModelProvider(this).get(ClothingItemDetailViewModel::class.java)
    }

    //Create an instance of Clothing Item on Edit Layout Page
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clothingItem = ClothingItem()
        val clothingItemId: UUID = arguments?.getSerializable(ARG_CLOTHING_ITEM_ID) as UUID
        // Eventually load clothes from Database
        clothingItemDetailViewModel.loadClothingItem(clothingItemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_clothing_details_page,container, false)
        val previewImage: ImageView by lazy { view.findViewById<ImageView>(R.id.clothing_item_photo)  }
        //Any buttons or user interactable objects that use or receive data.
        clothingTypeField = view.findViewById(R.id.edit_clothing_type) as EditText
        colorField = view.findViewById(R.id.edit_clothing_color) as EditText
        clothingDescriptionField = view.findViewById(R.id.edit_clothing_description) as EditText
        photoView = view.findViewById(R.id.clothing_item_photo) as ImageView
        imageGallery = view.findViewById(R.id.image_gallery_button) as ImageButton
        captureImage = view.findViewById(R.id.camera_button) as ImageButton
        return view

      /*  val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { photoView.setImageURI(uri) }
        }*/

       /* fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")
*/
        imageGallery.setOnClickListener {
                    selectImageFromGallery()
                }*/

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clothingItemDetailViewModel.clothingItemLiveData.observe(
            viewLifecycleOwner,
            Observer { clothingItems -> clothingItems?.let {
                    if (clothingItems != null) {
                        this.clothingItem = clothingItems
                        photoFile = clothingItemDetailViewModel.getPhotoFile(clothingItems)
                        photoUri = FileProvider.getUriForFile(requireActivity(),"com.example.closet.provider", photoFile)
                        updateUI()
                    }
                }
            }
        )
    }
    //Try adding the Spinner Listener to onStart
    override fun onStart() {
            super.onStart()
            /**
             * Edit Text widget watcher for Clothing TYPE on Edit screen.
             */
            val clothingTypeWatcher = object : TextWatcher {
                override fun beforeTextChanged( sequence: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    //left blank intentionally
                }   override fun onTextChanged( sequence: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    clothingItem.clothingType = sequence.toString()
                }
                //ClothingColor edit Text widg
                override fun afterTextChanged(sequence: Editable?) {
                   //Left blank too.
                }
            }

            /**
             * Edit Text widget watcher for Clothing COLOR on Edit screen.
             */
            val clothingColorWatcher = object : TextWatcher {
                override fun beforeTextChanged( sequence: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    //left blank intentionally
                }   override fun onTextChanged( sequence: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    clothingItem.color = sequence.toString()
                }
                //ClothingColor edit Text widget
                override fun afterTextChanged(sequence: Editable?) {
                    //Left blank too.
                }
            }

            /**
             * Edit Text widget watcher for Clothing DESCRIPTION on Edit screen.
             */
            val clothingDescriptionWatcher = object : TextWatcher {
                override fun beforeTextChanged( sequence: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    //left blank intentionally
                }   override fun onTextChanged( sequence: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    clothingItem.description = sequence.toString()
                }
                //ClothingColor edit Text widget
                override fun afterTextChanged(sequence: Editable?) {
                    //Left blank too.
                }
            }

            clothingTypeField.addTextChangedListener(clothingTypeWatcher)
            colorField.addTextChangedListener(clothingColorWatcher)
            clothingDescriptionField.addTextChangedListener(clothingDescriptionWatcher)


        captureImage.apply {
            val packageManager: PackageManager = requireActivity().packageManager

            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(takePicture,PackageManager.MATCH_DEFAULT_ONLY)

            if (resolvedActivity == null){
                isEnabled = false
             }
            setOnClickListener {
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(takePicture,PackageManager.MATCH_DEFAULT_ONLY)

                for (cameraActivity in cameraActivities){
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }

                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
            }
        }
        imageGallery.apply {
            // val packageManager: PackageManager = requireActivity().packageManager
            // val pickImage = Intent (Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // val resolvedActivity: ResolveInfo? =
            //  packageManager.resolveActivity(pickImage,PackageManager.MATCH_DEFAULT_ONLY)
            //if (resolvedActivity == null){
            //           isEnabled = false
            //   }
            //   startActivityForResult(pickImage, PICK_IMAGE)

        }
}

private fun updatePhotoView(){
 if(photoFile.exists()){
     val bitmap = getScaledBitmap(photoFile.path, requireActivity())
     photoView.setImageBitmap(bitmap)
 }else{
     photoView.setImageDrawable(null)
 }
}

override fun onStop() {
 super.onStop()
 clothingItemDetailViewModel.saveClothingItem(clothingItem)
}

private fun updateUI() {
 clothingTypeField.setText(clothingItem.clothingType)
 colorField.setText(clothingItem.color)
 clothingDescriptionField.setText(clothingItem.description)

 updatePhotoView()

}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
 when (requestCode) {
     REQUEST_IMAGE_CAPTURE -> {
         updatePhotoView()
     } PICK_IMAGE ->{
         updatePhotoView()
     }}
}

companion object {
 fun newInstance(clothingItemId: UUID): EditClothingPageFragment {
     val args = Bundle().apply {
         putSerializable(ARG_CLOTHING_ITEM_ID, clothingItemId)
     }
     return EditClothingPageFragment().apply {
         arguments = args
     }
 }

}
}


