package com.example.doubledotproject.activity.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.auth.SignInActivity
import com.example.doubledotproject.activity.drawer.AboutUsActivity
import com.example.doubledotproject.activity.drawer.ContactUsActivity
import com.example.doubledotproject.activity.drawer.MyProfileActivity
import com.example.doubledotproject.activity.drawer.PrivacyPolicyActivity
import com.example.doubledotproject.activity.drawer.TermsAndConditionsActivity
import com.example.doubledotproject.activity.drawer.WalletActivity
import com.example.doubledotproject.activity.home.adapters.HomeViewPagerAdapter
import com.example.doubledotproject.activity.reusableClasses.BlurDialog
import com.example.doubledotproject.databinding.ActivityHomeMainBinding
import com.example.doubledotproject.databinding.DialogLogoutBinding
import com.example.doubledotproject.databinding.DrawerLayoutBinding
import com.example.doubledotproject.localDatabase.PrefManager
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeMainBinding
    private lateinit var viewPager: CustomViewPager
    private var slidingRootNav: SlidingRootNav? = null
    private lateinit var drawerBinding: DrawerLayoutBinding
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewPager
        viewPager = binding.fragmentContainer
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.setPagingEnabled(false)

        // Initialize SlidingRootNav
        slidingRootNav = SlidingRootNavBuilder(this)
            .withMenuOpened(false)  // Initial state of the menu
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withDragDistance(200)  // Distance for dragging
            .withRootViewScale(0.8f)  // Scale of the root view
            .withRootViewElevation(10)  // Elevation of the root view
            .withGravity(SlideGravity.RIGHT)  // Direction of the sliding menu
            .addRootTransformation(CustomRootTransformation())
            .withMenuLayout(R.layout.drawer_layout)  // Layout of the menu
            .addDragListener(object : DragListener {
                override fun onDrag(progress: Float) {
                    // Detect when drawer is closed
                    if (progress == 0f) {
                        updateIcons(0)
                        viewPager.currentItem = 0
                    }
                }
            })
            .inject()

        // Bind drawer layout views
        val drawerView = slidingRootNav!!.layout.findViewById<View>(R.id.drawer_layout_root)
        drawerBinding = DrawerLayoutBinding.bind(drawerView)

        observer()
        setupDrawerListeners()
        setupListeners()
        updateIcons(0)
    }

    private fun observer() {
        viewModel.logoutUserAccount.observe(this){
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
                        Toast.makeText(this ,"Opps! Error While Fetching Data" , Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this ,"Something Went Wrong" , Toast.LENGTH_SHORT).show()

                }
                else -> {}
            }
        }
    }

    private fun setupDrawerListeners() {
        drawerBinding.aboutUsBtn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, AboutUsActivity::class.java))
        }
        drawerBinding.contactUsBtn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, ContactUsActivity::class.java))
        }
        drawerBinding.myProfileBtn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, MyProfileActivity::class.java))
        }
        drawerBinding.walletBn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, WalletActivity::class.java))
        }
        drawerBinding.tncBtn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, TermsAndConditionsActivity::class.java))
        }
        drawerBinding.privacyPolicyBtn.setOnClickListener {
            startActivity(Intent(this@HomeMainActivity, PrivacyPolicyActivity::class.java))
        }
        drawerBinding.backCrossBtn.setOnClickListener {
            slidingRootNav?.closeMenu()
        }
        drawerBinding.userNameTv.text = App.app.prefManager.logginUserData.fullName
        drawerBinding.userPhoneNoTv.text = App.app.prefManager.logginUserData.phoneNumber.toString()
        drawerBinding.phnCountryCodeTv.text =
            App.app.prefManager.logginUserData.counterCode.toString()
        Glide.with(this)
            .load(App.app.prefManager.logginUserData.image)
            .placeholder(R.drawable.dummy_avatar)
            .error(R.drawable.dummy_avatar)
            .into(drawerBinding.userProfileImg);

        drawerBinding.logOutBtn.setOnClickListener {
            val dialogBinding = DialogLogoutBinding.inflate(layoutInflater)
            val logoutDialog = BlurDialog(this, R.style.TransparentDialogTheme)
            logoutDialog.setContentView(dialogBinding.root)

            dialogBinding.btnCancel.setOnClickListener {
                logoutDialog.dismiss()
            }
            dialogBinding.crossBtn.setOnClickListener {
                logoutDialog.dismiss()
            }
            dialogBinding.btnConfirm.setOnClickListener {
                viewModel.logoutUser(App.app.prefManager.logginUserData.jwtToken)
                logoutDialog.dismiss()
            }
            logoutDialog.show()
        }
    }

    private fun setupListeners() {
        binding.homeIcon.setOnClickListener {
            updateIcons(0)
            viewPager.currentItem = 0
        }
        binding.historyIcon.setOnClickListener {
            updateIcons(1)
            viewPager.currentItem = 1
        }
        binding.profileIcon.setOnClickListener {
            updateIcons(2)
            slidingRootNav?.openMenu()
        }
    }

    private fun updateIcons(selectedTab: Int) {
        when (selectedTab) {
            0 -> {
                binding.homeIconWithTitle.customHomeIcon.visibility = View.VISIBLE
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
                binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.GONE
                binding.historyIcon.visibility = View.VISIBLE
                binding.profileIcon.visibility = View.VISIBLE
            }

            1 -> {
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.VISIBLE
                binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
                binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
                binding.historyIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.VISIBLE
                binding.profileIcon.visibility = View.VISIBLE
            }

            2 -> {
                binding.profileIconWithTitle.customProfileIcon.visibility = View.VISIBLE
                binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
                binding.profileIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.VISIBLE
                binding.historyIcon.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawerBinding.userNameTv.text = App.app.prefManager.logginUserData.fullName
        drawerBinding.userPhoneNoTv.text = App.app.prefManager.logginUserData.phoneNumber.toString()
        drawerBinding.phnCountryCodeTv.text =
            App.app.prefManager.logginUserData.counterCode.toString()
        Glide.with(this)
            .load(App.app.prefManager.logginUserData.image)
            .placeholder(R.drawable.dummy_avatar)
            .error(R.drawable.dummy_avatar)
            .into(drawerBinding.userProfileImg);
    }
}
