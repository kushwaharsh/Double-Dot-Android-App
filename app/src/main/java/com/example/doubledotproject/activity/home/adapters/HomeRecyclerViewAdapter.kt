package com.example.doubledotproject.activity.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.apiResponse.Data
import com.example.doubledotproject.databinding.HomeRecyclerviewEachItemBinding
import com.example.doubledotproject.utiles.App

class HomeRecyclerViewAdapter (private var expertListData: List<Data>,val clickListner:(Int)->Unit) : RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder>() {

    fun setNewList(newList: ArrayList<Data>){
        this.expertListData = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeRecyclerviewEachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expert = expertListData[position]
        holder.bind(expert , position)
    }

    override fun getItemCount(): Int = expertListData.size

    inner class MyViewHolder(private val binding: HomeRecyclerviewEachItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expertData: Data, position: Int) {
            // Load image using Glide or other image loading libraries
           /* Glide.with(binding.expertImage.context)
                .load(expertData.image)
                .into(binding.expertImage)*/

            when (expertData.gender) {
                "Male" -> {
                    Glide.with(binding.expertImage.context)
                        .load(expertData.image)
                        .placeholder(R.drawable.male_avatar)
                        .error(R.drawable.male_avatar)
                        .into(binding.expertImage)
                }
                "Female" -> {
                    Glide.with(binding.expertImage.context)
                        .load(expertData.image)
                        .placeholder(R.drawable.female_avatar)
                        .error(R.drawable.female_avatar)
                        .into(binding.expertImage)
                }
                else -> {}
            }


            binding.expertNameTV.text = expertData.fullName
            binding.expertExpertiseFeild.text = expertData.keywordExpertise.joinToString(", ")
            binding.expertSessionsCountTV.text = expertData.sessionCount.toString()
            binding.expertLanguageTV.text = expertData.language
            binding.expertDiscountedConsultationFee.text = "${expertData.discountedConsultationFee}/"
            binding.expertConsultationFee.text = "${expertData.consultationFee}/"
            val ratingData = String.format("%.1f", expertData.rating.toFloat())
            binding.expertRatingTV.text = ratingData

            itemView.setOnClickListener{
                clickListner.invoke(position)
            }

        }
    }
}
