package com.example.firstinubuntu.fragments

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firstinubuntu.R
import com.example.firstinubuntu.data.LikeObject
import com.example.firstinubuntu.data.allList
import com.example.firstinubuntu.data.natureList
import com.example.firstinubuntu.data.popularList
import com.example.firstinubuntu.databinding.FragmentShowBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ShowFragment : Fragment() {

    lateinit var binding: FragmentShowBinding
    private var imageIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(layoutInflater)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView).visibility =
            View.GONE

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvImg.setOnClickListener { return@setOnClickListener }

        val listkey = arguments?.getInt("keylist")
        imageIndex = arguments?.getInt("image_url")
        var imageResId = 0

        when (listkey) {
            1 -> imageResId = allList[imageIndex!!].img
            2 -> imageResId = natureList[imageIndex!!].img
            3 -> imageResId = popularList[imageIndex!!].img
            4 -> imageResId = LikeObject.likeList[imageIndex!!].img
            else -> Toast.makeText(requireContext(), "Xatolik yuz berdi", Toast.LENGTH_SHORT).show()
        }
        binding.tvImg.setImageResource(imageResId)


        binding.shareBtn.setOnClickListener {
            shareImage()
        }

        var isLiked = false

        binding.likeBtn.setOnClickListener {
            isLiked = !isLiked

            val newColor =
                if (isLiked) Color.parseColor("#FF0000") else Color.parseColor("#9E020202")
            binding.likeBtn.setColorFilter(newColor)
        }



        binding.installBtn.setOnClickListener {
            saveImageToGallery()
        }

        binding.infoBtn.setOnClickListener {

            if (binding.bottomSheet.visibility == View.VISIBLE) {
                binding.bottomPanel.visibility = View.VISIBLE
                binding.bottomSheet.visibility = View.GONE
            } else {
                binding.bottomSheet.visibility = View.VISIBLE
                binding.bottomPanel.visibility = View.GONE
                val drawable = ContextCompat.getDrawable(requireContext(), imageResId)
                if (drawable is BitmapDrawable) {
                    val bitmap = drawable.bitmap
                    val byteCount = bitmap.byteCount.toDouble() / 1024.0
                    val formattedSize = if (byteCount >= 1024) {
                        String.format("%.2f MB", byteCount / 1024)
                    } else {
                        String.format("%.2f KB", byteCount)
                    }
                    binding.imgSize.text = "$formattedSize"
                } else {
                    binding.imgSize.text = "N/A"
                }

                binding.tvImg.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        binding.tvImg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        val width = binding.tvImg.width
                        val height = binding.tvImg.height
                        binding.imgResolution.text = "$width x $height px"
                    }
                })
            }
        }


        return binding.root
    }

    private fun shareImage() {
        val drawable = binding.tvImg.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val cachePath = File(requireContext().cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()

        val fileUri = FileProvider.getUriForFile(
            requireContext(), "${requireContext().packageName}.provider", file
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, fileUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    private fun saveImageToGallery() {
        val drawable = binding.tvImg.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "wallpaper_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Wallpapers")
        }

        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )

        uri?.let {
            val outputStream: OutputStream? = requireContext().contentResolver.openOutputStream(it)
            outputStream?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                Toast.makeText(requireContext(), "Rasm gallereyaga yuklandi!", Toast.LENGTH_SHORT)
                    .show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Xatolik yuz berdi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView).visibility =
            View.VISIBLE
    }
}