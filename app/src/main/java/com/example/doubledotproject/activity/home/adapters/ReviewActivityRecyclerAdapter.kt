package com.example.doubledotproject.activity.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.apiResponse.ExpertRatingDetail
import com.example.doubledotproject.databinding.ExpertReviewEachItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewActivityRecyclerAdapter(private var reviewDetailsData: List<ExpertRatingDetail>) : RecyclerView.Adapter<ReviewActivityRecyclerAdapter.MyViewHolder>() {

    fun setNewList(newList: ArrayList<ExpertRatingDetail>){
        this.reviewDetailsData = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ExpertReviewEachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expert = reviewDetailsData[position]
        holder.bind(expert , position)
    }

    override fun getItemCount(): Int = reviewDetailsData.size

    inner class MyViewHolder(private val binding: ExpertReviewEachItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewData: ExpertRatingDetail, position: Int) {


            binding.detailedReview.text = reviewData.description.toString()
            binding.reviewOwnerName.text = reviewData.fullName.toString()
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            try {
                // Parse the original date string
                val date: Date? = inputFormat.parse(reviewData.createdAt)
                // Format the date to the desired format
                val formattedDate: String = if (date != null) outputFormat.format(date) else "N/A"

                // Set formatted date to TextView
                binding.reviewDate.text = formattedDate
            } catch (e: ParseException) {
                e.printStackTrace()
                binding.reviewDate.text = "Invalid Date"
            }

        }
    }
}

