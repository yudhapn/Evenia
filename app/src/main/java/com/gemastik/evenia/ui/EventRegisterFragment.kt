package com.gemastik.evenia.ui


import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentEventRegisterBinding
import com.gemastik.evenia.model.Vacancy
import com.gemastik.evenia.viewmodel.RegisterViewModel
import com.pertamina.spbucare.network.NetworkState
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File

class EventRegisterFragment : Fragment(), TextWatcher {
    private lateinit var binding: FragmentEventRegisterBinding
    private val regVM: RegisterViewModel by viewModel { parametersOf("") }
    private var bgImageUri: Uri? = null
    private var identImageUri: Uri? = null
    private var ident2ImageUri: Uri? = null
    private var isBackground = false
    private var isKtp = false
    private var isKtpPerson = false
    private lateinit var imageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.registerVM = regVM
        binding.apply {
            val listType = resources.getStringArray(R.array.category).toList()
            categoryDropdown.setAdapter(configureAdapter(listType))
            categoryDropdown.addTextChangedListener(this@EventRegisterFragment)
            val listIdent = resources.getStringArray(R.array.identity_type).toList()
            picIdentDropdown.setAdapter(configureAdapter(listIdent))
            picIdentDropdown.addTextChangedListener(this@EventRegisterFragment)
            ivBackground.setOnClickListener {
                bgImageUri = null
                cropImage(bgImageUri)
                isBackground = true
            }

            ivPhotoKtp.setOnClickListener {
                identImageUri = null
                cropImage(identImageUri)
                isKtp = true
            }

            ivPhotoKtpPerson.setOnClickListener {
                ident2ImageUri = null
                cropImage(ident2ImageUri)
                isKtpPerson = true
            }
            mcbDisclaimer.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    btnSubmit.isEnabled = true
                } else {
                    btnSubmit.isEnabled = false
                }
            }

            btnSubmit.setOnClickListener {
                regVM.networkState.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        NetworkState.SUCCESS -> {
                            progressbar.visibility = View.GONE
                            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            val action = EventRegisterFragmentDirections.actionVacancy()
                            findNavController().navigate(action)
                        }
                        NetworkState.RUNNING -> {
                            progressbar.visibility = View.VISIBLE
                            requireActivity().window.setFlags(
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            )
                        }
                        else -> {
                            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            View.GONE
                        }
                    }
                })
                val name = etVacName.text.toString()
                val description = etDesc.text.toString()
                val category = categoryDropdown.text.toString().toLowerCase()
                val location = etLocation.text.toString()
                val picName = etPicName.text.toString()
                val picPhone = etPicPhone.text.toString()
                val picIdent = picIdentDropdown.text.toString()
                val picIdentNumber = etPicIdentNum.text.toString()
                val bgImageName = System.currentTimeMillis().toString() + "." + bgImageUri?.let {
                    getFileExtension(it)
                }
                val identImageName =
                    System.currentTimeMillis().toString() + "." + identImageUri?.let {
                        getFileExtension(it)
                    }
                val ident2ImageName =
                    System.currentTimeMillis().toString() + "." + ident2ImageUri?.let {
                        getFileExtension(it)
                    }


                val vacancy = Vacancy(
                    name = name,
                    description = description,
                    category = category,
                    location = location,
                    picName = picName,
                    picPhone = picPhone,
                    picIdentityType = picIdent,
                    picIdentityNumber = picIdentNumber
                )

                regVM.createVacancy(
                    vacancy,
                    bgImageName,
                    bgImageUri,
                    identImageName,
                    identImageUri,
                    ident2ImageName,
                    ident2ImageUri
                )
            }
        }
        return binding.root
    }

    private fun configureAdapter(stringArray: List<String>) =
        ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            stringArray
        )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                when {
                    isBackground -> {
                        bgImageUri = result.uri
                        imageButton = binding.ivBackground
                        isBackground = false
                        setImage(bgImageUri)
                    }
                    isKtp -> {
                        identImageUri = result.uri
                        imageButton = binding.ivPhotoKtp
                        isKtp = false
                        setImage(identImageUri)
                    }
                    else -> {
                        ident2ImageUri = result.uri
                        imageButton = binding.ivPhotoKtpPerson
                        isKtp = false
                        setImage(ident2ImageUri)
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                result.error
            }
        }
    }

    fun cropImage(imageUri: Uri?) {
        CropImage.activity(imageUri)
            .setOutputCompressQuality(70)
            .setAspectRatio(16, 9)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(requireContext(), this)
    }

    fun setImage(imageUri: Uri?) {
        Glide.with(requireContext()).load(imageUri).into(this.imageButton)
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    private fun getFileExtension(uri: Uri): String? {
        var extension: String? = ""
        extension = if (uri.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(requireContext().contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }
}
