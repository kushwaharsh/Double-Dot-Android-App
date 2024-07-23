package com.example.doubledotproject.activity.auth
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.activity.home.HomeMainActivity
import com.example.doubledotproject.apiResponse.AuthResponseModel
import com.example.doubledotproject.databinding.ActivitySignInMobOtpactivityBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.AuthViewModel
import com.google.gson.Gson

class SignInMobOTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInMobOtpactivityBinding
    var responseData: AuthResponseModel? = null
    private val viewModel : AuthViewModel by viewModels()
    private var mobileNo: String = ""
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInMobOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        startCountdown()
        listener()
        observer()
    }

    private fun observer() {
        viewModel.userResendMobOtp.observe(this){
            when(it){
                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    binding.resendLayout.visibility = View.GONE
                    binding.timerLayout.visibility = View.VISIBLE
                    Toast.makeText(this , "Mobile Verification OTP : "+it.value?.data?.otp , Toast.LENGTH_LONG).show()
                    startCountdown()
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this , "Opp's ! Something Went Wrong" , Toast.LENGTH_SHORT).show()
                }
                else ->{}
            }
        }
    }

    private fun listener(){

        Toast.makeText(this , "Mobile verification Otp : " + responseData?.otp , Toast.LENGTH_LONG).show()

        binding.mobileOtpPinView.addTextChangedListener(object : TextWatcher {
            val correctOtp = responseData?.otp

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 6) {
                    val enteredOtp = s.toString()
                    if (enteredOtp == responseData?.otp) {

                        when (responseData?.isExist){
                            true -> {
                                App.app.prefManager.isLoggedIn = true
                                App.app.prefManager.logginUserData = responseData!!.data
                                startActivity(
                                Intent(this@SignInMobOTPActivity , HomeMainActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK))
                             }

                            false -> { startActivity(
                                Intent(this@SignInMobOTPActivity , SignUpActivity::class.java)
                                    .putExtra(Enums.MobileNo.toString() , mobileNo))
                                     finish()
                            }

                            null -> {

                            }
                        }
                    } else {
                        // Show invalid OTP toast
                        Toast.makeText(this@SignInMobOTPActivity, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.backtoLoginBtn.setOnClickListener {
            startActivity(Intent(this , SignInActivity::class.java))
            finish()
        }

        binding.resendOtpBtn.setOnClickListener{
            viewModel.resendMobOtp(mobileNo.toLong())
        }
    }

    private fun initView(){
        val completeResponseJson = intent.getStringExtra(Enums.CompleteResponseData.toString())
        responseData = Gson().fromJson(completeResponseJson, AuthResponseModel::class.java)
        mobileNo = intent.getStringExtra(Enums.MobileNo.toString()).toString()
        binding.displayCountryCodeTv.text = intent.getStringExtra(Enums.CountryCode.toString())
        binding.displayMobNoTv.text = mobileNo
    }

    private fun startCountdown() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                binding.countDownTimerTv.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.resendLayout.visibility = View.VISIBLE
                binding.timerLayout.visibility = View.GONE


            }
        }.start()
    }

}