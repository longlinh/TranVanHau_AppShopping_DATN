package com.example.appshoppingdatn.presentation.ui.fragment.profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentProfileBinding
import com.example.appshoppingdatn.presentation.ui.activity.login.LoginActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.ultis.CustomProgressDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.io.ByteArrayOutputStream


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var viewModel : ProfileViewModel
    private var dialogCamera : Dialog ?= null
    private var dialogEdit : Dialog ?= null
    private var check: Int? = null
    private var progrssdialog: CustomProgressDialog? = null
    private val RESULT_LOAD_IMG = 123
    private val PERMISSION_REQUEST_CODE = 200
    private var bitMap: Bitmap? = null
    private var uri : Uri ?= null
    override fun getLayoutResId(): Int {
        return R.layout.fragment_profile
    }
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                ProfileViewModel.UPDATE_SUCCESS -> onShowMessageUpdateSuccess()
                ProfileViewModel.UPDATE_FAILD -> onShowMessageUpdateFailed()
            }
        }
        progrssdialog = CustomProgressDialog(requireActivity())
        onClickLogout()
        onSetInformationProfile()
        onCLickVisiblitiPassword()
        onClickAvatar()
        onClickEditProfile()
    }

    private fun onShowMessageUpdateFailed() {
        showMessage("Updated successfully !")
    }

    private fun onShowMessageUpdateSuccess() {
        showMessage("Failed to update !")
    }

    private fun onClickEditProfile() {
        binding.btnEditProfile.setOnClickListener {
            OpenDialogEdit(Gravity.CENTER)
            val edtEmail = dialogEdit!!.findViewById<EditText>(R.id.edtEmailEdit)
            val edtName = dialogEdit!!.findViewById<EditText>(R.id.edtNameEdit)
            val edtPhone= dialogEdit!!.findViewById<EditText>(R.id.edtPhoneEdit)
            val btnCancel = dialogEdit!!.findViewById<Button>(R.id.btnCancleEidt)
            val btnOK = dialogEdit!!.findViewById<Button>(R.id.btnOKEdit)

            edtEmail.text = Editable.Factory.getInstance().newEditable(binding.txtemail.text.toString())
            edtName.text = Editable.Factory.getInstance().newEditable(binding.txtName.text.toString())
            edtPhone.text = Editable.Factory.getInstance().newEditable(binding.txtPhone.text.toString())
            btnCancel.setOnClickListener {
                dialogEdit!!.dismiss()
            }
            btnOK.setOnClickListener {
                val email = binding.txtemail.text.toString()
                val name = binding.txtName.text.toString()
                val phone = binding.txtPhone.text.toString()
                viewModel.updateData(email,name,phone,edtEmail,edtName,edtPhone)
                dialogEdit!!.dismiss()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun onClickAvatar() {
        binding.imgAvatar.setOnClickListener {
            if (checkPermission()){
                OpenDialogCamera(Gravity.CENTER)
                val camera = dialogCamera!!.findViewById<LinearLayout>(R.id.dialogCamera)
                val dialogGallery = dialogCamera!!.findViewById<LinearLayout>(R.id.dialogGallery)
                val dialogCancle = dialogCamera!!.findViewById<TextView>(R.id.dialogCancle)
                dialogCancle.setOnClickListener {
                    dialogCamera!!.dismiss()
                }
                camera.setOnClickListener {
                    check = 0
                    getCameraTakePhoto()
                }
                dialogGallery.setOnClickListener {
                    check = 1
                    getImageFromGallery()
                }
            }else{
                requestPermission()
            }

        }
        binding.imgSave.setOnClickListener {
            onUpdateInfor()
        }
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),PERMISSION_REQUEST_CODE)
    }
    fun setmUri(mUri: Uri) {
        uri = mUri
    }
    private fun onUpdateInfor() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return
        progrssdialog!!.show()
        val profileChangeRequest = if (check==0){
            UserProfileChangeRequest.Builder()
                .setPhotoUri(getImageUriFromBitmap(requireActivity(),bitMap!!))
                .build()
        }else{
            UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
        }
        firebaseUser.updateProfile(profileChangeRequest)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    progrssdialog!!.dismiss()
                    Glide.with(requireActivity()).load(firebaseUser.photoUrl).error(R.drawable.avatar).into(binding.imgAvatar)
                    binding.imgSave.visibility = View.GONE
                    Toast.makeText(context, "Upload avatar success", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun getImageFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        dialogCamera!!.dismiss()
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun getCameraTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, RESULT_LOAD_IMG)
        }
        dialogCamera!!.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            if (check == 0){
                val takentImage = data!!.extras!!.get("data") as Bitmap
                setBitmapImageView(takentImage)
                bitMap = takentImage
                binding.imgSave.visibility = View.VISIBLE
            }else if (check == 1){
                val selectedImage = data!!.data
                setmUri(selectedImage!!)
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                setBitmapImageView(bitmap)
                bitMap = bitmap
                binding.imgSave.visibility = View.VISIBLE
            }
        }
    }
    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "", null)
        return Uri.parse(path.toString())
    }
    private fun onCLickVisiblitiPassword() {
        binding.imgShowPass.setOnClickListener {
            viewModel.onCLickVisiblePassword(binding.imgShowPass,binding.txtPassword)
        }
    }
    fun setBitmapImageView(bitmapImageView: Bitmap?) {
        Glide.with(binding.imgAvatar).load(bitmapImageView).into(binding.imgAvatar)
    }

    private fun onSetInformationProfile() {
        viewModel.updateInfor(binding.txtemail,binding.txtName,binding.txtPassword,binding.txtPhone,requireActivity(),binding.imgAvatar)
    }

    private fun onClickLogout() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }
    private fun OpenDialogCamera(gravity: Int) {
        dialogCamera = Dialog(requireContext())
        dialogCamera!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCamera!!.setContentView(R.layout.custom_dialog_choose_image)
        val window = dialogCamera!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialogCamera!!.setCancelable(true)
        } else {
            dialogCamera!!.setCancelable(false)
        }
        dialogCamera!!.show()
    }
    private fun OpenDialogEdit(gravity: Int) {
        dialogEdit = Dialog(requireContext())
        dialogEdit!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogEdit!!.setContentView(R.layout.dialog_update_profile)
        val window = dialogEdit!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialogEdit!!.setCancelable(true)
        } else {
            dialogEdit!!.setCancelable(false)
        }
        dialogEdit!!.show()
    }
}