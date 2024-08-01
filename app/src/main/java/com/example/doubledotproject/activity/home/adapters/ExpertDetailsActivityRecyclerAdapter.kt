package com.example.doubledotproject.activity.home.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.activity.home.ExpertReviewActivity
import com.example.doubledotproject.apiResponse.ExpertRatingDetail
import com.example.doubledotproject.databinding.ExpertDetailsReviewEachItemBinding
import com.example.doubledotproject.databinding.ExpertDetailsReviewEachItemFirstLayoutBinding
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExpertDetailsActivityRecyclerAdapter(private var ratingDetails: List<ExpertRatingDetail> , private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_FIRST = 1
        private const val VIEW_TYPE_OTHER = 2
    }

    fun setNewList(newList: ArrayList<ExpertRatingDetail>){
        this.ratingDetails = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_FIRST else VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FIRST -> {
                val binding = ExpertDetailsReviewEachItemFirstLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FirstViewHolder(binding)
            }
            VIEW_TYPE_OTHER -> {
                val binding = ExpertDetailsReviewEachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Replace with actual binding for other layout
                OtherViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val expert = ratingDetails[position]
        when (holder) {
            is FirstViewHolder -> holder.bind(expert)
            is OtherViewHolder -> holder.bind(expert)
        }


    }

    override fun getItemCount(): Int = ratingDetails.size

    inner class FirstViewHolder(private val binding: ExpertDetailsReviewEachItemFirstLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: ExpertRatingDetail) {
            // Bind data specific to the first view type
            // Example:
            // binding.detailedReview.text = reviewData.description
            // binding.reviewOwnerName.text = reviewData.fullName
            val ratingData = String.format("%.1f", reviewData.rating.toFloat())
            binding.averageRating.text = ratingData
            binding.numberOfRatings.text = "Overall rating based on ${ratingDetails.size.toString()} reviews"

            binding.viewAllReviewBtn.setOnClickListener {
                val intent = Intent(context , ExpertReviewActivity::class.java).apply {
                    val completeReviewData = Gson().toJson(ratingDetails)
                    putExtra("reviewData" , completeReviewData )
                }
                context.startActivity(intent)
            }
        }
    }

    inner class OtherViewHolder(private val binding: ExpertDetailsReviewEachItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: ExpertRatingDetail) {
            // Bind data specific to the other view type
            // Example:
            // binding.someTextView.text = reviewData.someField
            binding.reviewDescription.text = reviewData.description.toString()
            binding.reviewWrittenBy.text = reviewData.fullName.toString()
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
