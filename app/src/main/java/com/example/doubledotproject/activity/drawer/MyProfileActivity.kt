package com.example.doubledotproject.activity.drawer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.auth.SignInActivity
import com.example.doubledotproject.activity.reusableClasses.BlurDialog
import com.example.doubledotproject.apiResponse.LoginData
import com.example.doubledotproject.databinding.ActivityMyProfileBinding
import com.example.doubledotproject.databinding.DialogDeleteAccountBinding
import com.example.doubledotproject.localDatabase.PrefManager
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.CommonUtils
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private val viewModel: HomeViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var emailVerificationFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
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
                        setData(it.value.data)
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

        viewModel.deleteAccountUser.observe(this) {
            when (it) {
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }

                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        val prefManager = PrefManager.get(this)
                        prefManager.clearPreferences()
                        val intent = Intent(this, SignInActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "Opps! Something Went Wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(this, "Error While Fetching Data", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        viewModel.userProfileData.observe(this) {
            when (it) {
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }

                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        setData(it.value.data[0])
                        emailVerificationFlag = it.value.data[0].emailIsVerify

                    } else {
                        Toast.makeText(this, "Opps! Somethig Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(this, "Unable to fetch data", Toast.LENGTH_SHORT).show()

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

                if (emailVerificationFlag) {
                    binding.verifiedTV.visibility = View.VISIBLE
                    binding.sendOtpTV.visibility = View.GONE
                    binding.unverifiedTV.visibility = View.GONE
                } else {
                    binding.verifiedTV.visibility = View.GONE
                    binding.sendOtpTV.visibility = View.VISIBLE
                    binding.unverifiedTV.visibility = View.GONE
                }

            } else {
                updateData(false)
                binding.icEditProfileImage.visibility = View.GONE

                val editfullName = binding.etFullName.text.toString()
                val editemail = binding.etEmail.text.toString()
                val editDOB = binding.dobEt.text.toString()
                val editGender = binding.genderEt.text.toString()

                if (editfullName.isEmpty()) {
                    Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
                } else if (!CommonUtils.isValidEmail(editemail)) {
                    Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
                } else if (editDOB.isEmpty()) {
                    Toast.makeText(this, "Enter your DOB", Toast.LENGTH_SHORT).show()
                } else if (editGender.isEmpty()) {
                    Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
                } else {
                    val params = HashMap<String, Any>()
                    params["fullName"] = editfullName.trim()
                    params["email"] = editemail.trim()
                    params["gender"] = editGender
                    params["dob"] = editDOB
                    params["image"] = ""
                    params["state"] = "Uttar Pradesh"
                    params["city"] = "Noida"
                    params["emailIsVerify"] = emailVerificationFlag
                    viewModel.editUserProfile(App.app.prefManager.logginUserData.jwtToken, params)

                }
                binding.editProfileBtn.text = "Edit"
            }
        }
    }

    private fun initview() {

        viewModel.profileDetails(App.app.prefManager.logginUserData.jwtToken)

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
        binding.dobEt.apply {
            isEnabled = isEditable
            setTextColor(textColor)
            dobCalender()
        }
        binding.genderEt.apply {
            isEnabled = isEditable
            setTextColor(textColor)
            genderDropdown()
        }

    }

    private fun setData(data: LoginData) {
        //Showing pref data in edit text Hint
        binding.etFullName.setText(data.fullName)
        binding.etEmail.setText(data.email)
        binding.etPhoneNumber.setText(data.phoneNumber.toString())
        binding.etLocation.setText(data.location)
        binding.genderEt.setText(data.gender)
        binding.dobEt.setText(data.dob)

        if (data.emailIsVerify) {
            binding.verifiedTV.visibility = View.VISIBLE
            binding.sendOtpTV.visibility = View.GONE
            binding.unverifiedTV.visibility = View.GONE
        } else {
            binding.verifiedTV.visibility = View.GONE
            binding.sendOtpTV.visibility = View.GONE
            binding.unverifiedTV.visibility = View.VISIBLE
        }

    }

    private fun genderDropdown() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.gender_dropdown_each_item, gender)
        binding.genderEt.setAdapter(arrayAdapter)
    }

    private fun dobCalender() {
        // Define the constraints
        val calendarConstraintsBuilder = CalendarConstraints.Builder()
            .setEnd(Calendar.getInstance().apply {
                set(Calendar.YEAR, 2012)
                set(Calendar.MONTH, Calendar.DECEMBER) // December
                set(Calendar.DAY_OF_MONTH, 31)
            }.timeInMillis)

        // Create a MaterialDatePicker with constraints
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setCalendarConstraints(calendarConstraintsBuilder.build())
            .build()

        // Set a click listener on the EditText
        binding.dobEt.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        // Set a listener for when a date is selected
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val date = Date(selectedDate)
            binding.dobEt.setText(dateFormat.format(date))
        }
    }
}

