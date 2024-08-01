package com.example.doubledotproject.activity.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.home.adapters.ReviewActivityRecyclerAdapter
import com.example.doubledotproject.apiResponse.ExpertRatingDetail
import com.example.doubledotproject.databinding.ActivityExpertReviewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExpertReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpertReviewBinding
    private lateinit var adapter: ReviewActivityRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<ExpertRatingDetail>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()

    }

    private fun initview() {

        val reviewData = intent.getStringExtra("reviewData")
        val reviewType = object : TypeToken<List<ExpertRatingDetail>>() {}.type
        val completeReviewData: List<ExpertRatingDetail> = Gson().fromJson(reviewData, reviewType)


        recyclerView = binding.reviewRecyclerView
        adapter = ReviewActivityRecyclerAdapter(completeReviewData)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}