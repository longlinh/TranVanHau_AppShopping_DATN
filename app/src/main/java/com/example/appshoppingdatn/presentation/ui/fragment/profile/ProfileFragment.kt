package com.example.appshoppingdatn.presentation.ui.fragment.profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentProfileBinding
import com.example.appshoppingdatn.presentation.ui.activity.home.HomeActivity
import com.example.appshoppingdatn.presentation.ui.activity.login.LoginActivity
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.presentation.ui.fragment.notification.NotificationFragment
import com.example.appshoppingdatn.ultis.ContextUtils
import com.example.appshoppingdatn.ultis.CustomProgressDialog
import com.example.appshoppingdatn.ultis.Utils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var viewModel : ProfileViewModel
    private var dialogCamera : Dialog ?= null
    private var dialogEdit : Dialog ?= null
    private var dialogChangePass : Dialog ?= null
    private var dialogLanguage : Dialog ?= null
    private var check: Int? = null
    private var progrssdialog: CustomProgressDialog? = null
    private val RESULT_LOAD_IMG = 123
    private val PERMISSION_REQUEST_CODE = 200
    private var bitMap: Bitmap? = null
    private var uri : Uri ?= null
    private var storage : FirebaseStorage ?= null
    private var storageRef : StorageReference ?= null
    var tamp = ""

    override fun getLayoutResId(): Int {
        return R.layout.fragment_profile
    }
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                ProfileViewModel.UPDATE_SUCCESS -> onShowMessageUpdateSuccess()
                ProfileViewModel.UPDATE_FAILD -> onShowMessageUpdateFailed()
                ProfileViewModel.CHANGE_SUCCESS -> onShowMessageChangeSuccess()
                ProfileViewModel.CHANGE_FAILD -> onShowMessageChangeFaild()
                ProfileViewModel.DISS_DIALOG_CHANGE -> onDisDialogChangePass()
                ProfileViewModel.SHOW_PROGRESS_DIALOG -> onShowProgressDialog()
                ProfileViewModel.DISS_PROGRESS_DIALOG -> onDisProgressDialog()
            }
        }
        storage = FirebaseStorage.getInstance()
        storageRef = storage!!.reference
        progrssdialog = CustomProgressDialog(requireActivity())
        onClickLogout()
        onSetInformationProfile()
        onCLickVisiblitiPassword()
        onClickAvatar()
        onClickEditProfile()
        onClickChangePassword()
        onClickLanguage()
        OnClickNotification()
    }

    private fun OnClickNotification() {
        binding.layoutNotification.setOnClickListener {
            replaceFragment(NotificationFragment())
        }
    }


    private fun onDisProgressDialog() {
        progrssdialog!!.dismiss()
    }

    private fun onShowProgressDialog() {
        progrssdialog!!.show()
    }

    private fun onDisDialogChangePass() {
        dialogChangePass!!.dismiss()
    }

    private fun onShowMessageChangeFaild() {
        Toast.makeText(context, getString(R.string.changepasswordFaild), Toast.LENGTH_SHORT).show()
    }
    private fun onShowMessageChangeSuccess() {
        Toast.makeText(context, getString(R.string.changepasswordSuccess), Toast.LENGTH_SHORT).show()
    }

    private fun onClickLanguage() {
        binding.layoutLanguage.setOnClickListener {
            OpenDialogChangeLanguage(Gravity.CENTER)
            val btnVietNam = dialogLanguage!!.findViewById<LinearLayout>(R.id.btnVietName)
            val btnEnglish = dialogLanguage!!.findViewById<LinearLayout>(R.id.btnEnglish)

            btnVietNam.setOnClickListener {
                ContextUtils.language = "vi"
                setLocale( ContextUtils.language)
                startActivity(Intent(requireActivity(),HomeActivity::class.java))
                dialogLanguage!!.dismiss()
            }
            btnEnglish.setOnClickListener {
                ContextUtils.language = "en"
                setLocale( ContextUtils.language)
                startActivity(Intent(requireActivity(),HomeActivity::class.java))
                dialogLanguage!!.dismiss()
            }

        }
    }
    private fun setLocale(code: String) {
        val config = Configuration()
        var locale: Locale? = null
        locale = Locale(code)
        Locale.setDefault(locale)
        config.locale = locale
        requireActivity().resources.updateConfiguration(
            config,
            requireActivity().resources.displayMetrics
        )
    }
    override fun onAttach(context: Context) {
        val localeToSwith = Locale(ContextUtils.language)
        val localeUpdateContext = context.let {
            ContextUtils.updateLocale(it,localeToSwith)
        }
        super.onAttach(localeUpdateContext)
    }
    private fun onClickChangePassword() {
        binding.layoutChangePassword.setOnClickListener {
            OpenDialogChangePass(Gravity.CENTER)
            val edtPasswordOld = dialogChangePass!!.findViewById<EditText>(R.id.edtPasswordOld)
            val edtPasswordNew = dialogChangePass!!.findViewById<EditText>(R.id.edtNewPass)
            val edtPasswordConfirm = dialogChangePass!!.findViewById<EditText>(R.id.edtConfirmPass)
            val btnOK = dialogChangePass!!.findViewById<Button>(R.id.btnOKChange)
            val btnCancel = dialogChangePass!!.findViewById<Button>(R.id.btnCancleChange)
            val passwordNoew = binding.txtPassword.text.toString()
            btnCancel.setOnClickListener {
                dialogChangePass!!.dismiss()
            }
            btnOK.setOnClickListener {
                viewModel.changePassword(passwordNoew,edtPasswordOld,edtPasswordNew,edtPasswordConfirm,requireActivity(),date()!!)
                binding.txtPassword.text = Editable.Factory.getInstance().newEditable(edtPasswordNew.text.toString())
            }
        }
    }

    private fun onShowMessageUpdateFailed() {
        showMessage(getString(R.string.updateFailed))
    }

    private fun onShowMessageUpdateSuccess() {
        showMessage(getString(R.string.updateSuccess))
    }

    private fun onClickEditProfile() {
        binding.btnEditProfile.setOnClickListener {
            OpenDialogEdit(Gravity.CENTER)

            val edtName = dialogEdit!!.findViewById<EditText>(R.id.edtNameEdit)
            val edtPhone= dialogEdit!!.findViewById<EditText>(R.id.edtPhoneEdit)
            val btnCancel = dialogEdit!!.findViewById<Button>(R.id.btnCancleEidt)
            val btnOK = dialogEdit!!.findViewById<Button>(R.id.btnOKEdit)


            edtName.text = Editable.Factory.getInstance().newEditable(binding.txtName.text.toString())
            edtPhone.text = Editable.Factory.getInstance().newEditable(binding.txtPhone.text.toString())
            btnCancel.setOnClickListener {
                dialogEdit!!.dismiss()
            }
            btnOK.setOnClickListener {
                viewModel.updateData(requireActivity(),edtName,edtPhone, date()!!)
                binding.txtName.text = edtName.text.toString()
                binding.txtPhone.text = edtPhone.text.toString()
                Utils.name = edtName.text.toString()
                Utils.phone = edtPhone.text.toString()
                dialogEdit!!.dismiss()
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun date(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
        val cal = Calendar.getInstance()
        println(dateFormat.format(cal.time))
        return dateFormat.format(cal.time)
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
                    if (check==0){
                        //Save image to firebase storage
                        val nameIMG = "image/"+UUID.randomUUID().toString()+".jpeg"
                        val ref = storageRef!!.child(nameIMG)
                        ref.putFile(getImageUriFromBitmap(requireActivity(),bitMap!!)).addOnCompleteListener {
                            if (it.isSuccessful){
                                ref.downloadUrl.addOnSuccessListener {
                                    viewModel.updateAvatar(it.toString())
                                }
                            }
                        }
                    }else{
                        //Save image to firebase storage
                        val nameIMG = "image/"+UUID.randomUUID().toString()+".jpeg"
                        val ref = storageRef!!.child(nameIMG)
                        ref.putFile(uri!!).addOnCompleteListener {
                            if (it.isSuccessful){
                                ref.downloadUrl.addOnSuccessListener {
                                    viewModel.updateAvatar(it.toString())
                                }.addOnFailureListener {
                                    Log.d("faild",it.toString())
                                }
                            }
                        }
                    }
                    Toast.makeText(context, getString(R.string.uploadAvatarSuccess), Toast.LENGTH_SHORT).show()
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

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun BitMapToString(bitmap: Bitmap): String {
//        val base = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, base)
//        val b = base.toByteArray()
//        return Base64.getEncoder().encodeToString(b)
//    }
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
            Utils.checkFragment = 0
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
    private fun OpenDialogChangePass(gravity: Int) {
        dialogChangePass = Dialog(requireContext())
        dialogChangePass!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogChangePass!!.setContentView(R.layout.dialog_change_password)
        val window = dialogChangePass!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialogChangePass!!.setCancelable(true)
        } else {
            dialogChangePass!!.setCancelable(false)
        }
        dialogChangePass!!.show()
    }
    private fun OpenDialogChangeLanguage(gravity: Int) {
        dialogLanguage = Dialog(requireContext())
        dialogLanguage!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLanguage!!.setContentView(R.layout.dialog_change_language)
        val window = dialogLanguage!!.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAtributes = window.attributes
        windowAtributes.gravity = gravity
        window.attributes = windowAtributes
        if (Gravity.CENTER == gravity) {
            dialogLanguage!!.setCancelable(true)
        } else {
            dialogLanguage!!.setCancelable(false)
        }
        dialogLanguage!!.show()
    }
}