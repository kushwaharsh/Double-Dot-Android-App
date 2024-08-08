package com.example.doubledotproject.activity.drawer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.auth.SignInActivity
import com.example.doubledotproject.activity.reusableClasses.BlurDialog
import com.example.doubledotproject.databinding.ActivityMyProfileBinding
import com.example.doubledotproject.databinding.DialogDeleteAccountBinding
import com.example.doubledotproject.localDatabase.PrefManager
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.CommonUtils
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
        setData()
        observer()
        listener()

    }

    private fun observer() {
        viewModel.editedUserProfileData.observe(this) {
            when (it) {
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }

                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        App.app.prefManager.logginUserData = it.value.data
                        setData()
                    }
                }

                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this, "Opps! Something Went Wrong", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, "Error while Fetching Data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.deleteAccountUser.observe(this){
            when(it){
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS){
                        val prefManager = PrefManager.get(this)
                        prefManager.clearPreferences()
                        val intent = Intent(this, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Toast.makeText(this , "Opps! Something Went Wrong" , Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this , "Error While Fetching Data" , Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.deleteAccount.setOnClickListener {
            val dialogBinding = DialogDeleteAccountBinding.inflate(layoutInflater)
            val layoutDialog = BlurDialog(this, R.style.TransparentDialogTheme)
            layoutDialog.setContentView(dialogBinding.root)

            dialogBinding.crossBtn.setOnClickListener {
                layoutDialog.dismiss()
            }

            dialogBinding.btnCancel.setOnClickListener {
                layoutDialog.dismiss()
            }
            dialogBinding.btnConfirm.setOnClickListener {
                viewModel.deleteUserAccount(App.app.prefManager.logginUserData.jwtToken)
                layoutDialog.dismiss()
            }

            layoutDialog.show()

        }
        binding.editProfileBtn.setOnClickListener {
            if (binding.editProfileBtn.text == "Edit") {
                updateData(true)
                binding.icEditProfileImage.visibility = View.VISIBLE
                binding.editProfileBtn.text = "Save"
            } else {
                updateData(false)
                binding.icEditProfileImage.visibility = View.GONE


                val fullName = binding.etFullName.text.toString()
                val email = binding.etEmail.text.toString()

                if (fullName.isEmpty()) {
                    Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
                } else if (!CommonUtils.isValidEmail(email)) {
                    Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
                } else {
                    val params = HashMap<String, Any>()
                    params["fullName"] = fullName
                    params["email"] = email
                    params["gender"] = "Male"
                    params["dob"] = "123445"
                    params["image"] = ""
                    params["state"] = "Uttar Pradesh"
                    params["city"] = "Noida"
                    params["emailIsVerify"] = true
                    viewModel.editUserProfile(App.app.prefManager.logginUserData.jwtToken, params)
                }
                binding.editProfileBtn.text = "Edit"
            }
        }
    }

    private fun initview() {
        Glide.with(this)
            .load(App.app.prefManager.logginUserData.image)
            .placeholder(R.drawable.dummy_avatar)
            .error(R.drawable.dummy_avatar)
            .into(binding.profileImage);
        updateData(false)
    }

    private fun updateData(isEditable: Boolean) {
        val textColor = if (isEditable) {
            ContextCompat.getColor(this, R.color.red)
        } else {
            ContextCompat.getColor(this, R.color.greyText)
        }
        binding.etFullName.apply {
            isEnabled = isEditable
            setTextColor(textColor)
        }
        binding.etEmail.apply {
            isEnabled = isEditable
            setTextColor(textColor)
        }
        binding.etLocation.apply {
            isEnabled = isEditable
            setTextColor(textColor)
        }
    }

    private fun setData() {
        //Showing pref data in edit text Hint
        binding.etFullName.setText(App.app.prefManager.logginUserData.fullName)
        binding.etEmail.setText(App.app.prefManager.logginUserData.email)
        binding.etPhoneNumber.setText(App.app.prefManager.logginUserData.phoneNumber.toString())
        binding.etLocation.setText(App.app.prefManager.logginUserData.location)

    }
}

