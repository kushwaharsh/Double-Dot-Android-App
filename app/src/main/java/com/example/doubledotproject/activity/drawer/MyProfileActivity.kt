package com.example.doubledotproject.activity.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.reusableClasses.BlurDialog
import com.example.doubledotproject.databinding.ActivityMyProfileBinding
import com.example.doubledotproject.databinding.DialogDeleteAccountBinding
import com.example.doubledotproject.utiles.App

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
        setData()
        listener()

    }

    private fun initview() {
        Glide.with(this)
            .load(App.app.prefManager.logginUserData.image)
            .placeholder(R.drawable.dummy_avatar)
            .error(R.drawable.dummy_avatar)
            .into(binding.profileImage);
        updateData(false)
    }

    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.deleteAccount.setOnClickListener {
            val dialogBinding = DialogDeleteAccountBinding.inflate(layoutInflater)
            val layoutDialog = BlurDialog(this , R.style.TransparentDialogTheme)
            layoutDialog.setContentView(dialogBinding.root)

            dialogBinding.crossBtn.setOnClickListener {
                layoutDialog.dismiss()
            }

            dialogBinding.btnCancel.setOnClickListener {
                layoutDialog.dismiss()
            }

            layoutDialog.show()

        }
        binding.editProfileBtn.setOnClickListener {
            if (binding.editProfileBtn.text == "Edit") {
                updateData(true)
                binding.editProfileBtn.text = "Save"
            } else {
                updateData(false)
                binding.editProfileBtn.text = "Edit"
            }
        }
    }

    private fun updateData(isEditable: Boolean) {
        val textColor = if (isEditable) {
            ContextCompat.getColor(this, R.color.phoneGreen)
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
        binding.etPhoneNumber.apply {
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

