package com.example.doubledotproject.activity.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.R
import com.example.doubledotproject.databinding.ActivitySignUpBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.CommonUtils
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.AuthViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: AuthViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
        observer()
        genderDropdown()
        dobCalender()

    }

    private fun dobCalender() {
        // Define the constraints
        val calendarConstraintsBuilder = CalendarConstraints.Builder()
            .setEnd(Calendar.getInstance().apply {
                set(Calendar.YEAR, 2012)
                set(Calendar.MONTH, Calendar.DECEMBER) // December
                set(Calendar.DAY_OF_MONTH, 31)
            }.timeInMillis)
            .build()

        // Create a MaterialDatePicker with constraints
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setCalendarConstraints(calendarConstraintsBuilder)
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


    private fun genderDropdown() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.gender_dropdown_each_item, gender)
        binding.genderEt.setAdapter(arrayAdapter)
    }

    private fun observer() {
        viewModel.userSignUp.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }

                is Resource.Success -> {
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        ProgressBarUtils.hideProgressDialog()
                        App.app.prefManager.isLoggedIn = true
                        App.app.prefManager.logginUserData = it.value.data
                        startActivity(Intent(this, AuthSuccessActivity::class.java))
                        finish()

                    }
                }

                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this, "Opp's Something Went Wrong", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    private fun listener() {


        binding.signUpContinueBtn.setOnClickListener {
            val fullName = binding.fullNameET.text.toString()
            val email = binding.emailEt.text.toString()
            val mobileNo = intent.getStringExtra(Enums.MobileNo.toString()).toString()
            val gender = binding.genderEt.text.toString()
            val dob = binding.dobEt.text.toString()

            if (fullName.isEmpty()) {
                Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
            } else if (!CommonUtils.isValidEmail(email)) {
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show()
            } else if (gender.isEmpty()) {
                Toast.makeText(this, "Choose Gender", Toast.LENGTH_SHORT).show()
            } else if (dob.isEmpty()) {
                Toast.makeText(this, "Enter DOB", Toast.LENGTH_SHORT).show()
            }else {
                val params = HashMap<String, String>()
                params["fullName"] = fullName
                params["email"] = email
                params["phoneNumber"] = mobileNo
                params["gender"] = gender
                params["dob"] = dob
                params["state"] = "Uttar Pradesh"
                params["city"] = "Noida"
                viewModel.signUpUser(params)
            }
        }

        binding.backToLoginBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

    }


}