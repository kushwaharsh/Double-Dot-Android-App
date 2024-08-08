package com.example.doubledotproject.activity.drawer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.R
import com.example.doubledotproject.apiResponse.TransHistory
import com.example.doubledotproject.databinding.WalletTransactionEachItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class WalletTransactionRecyclerAdapter(
    private var walletTransactionData: List<TransHistory>,
    private var context: Context
) : RecyclerView.Adapter<WalletTransactionRecyclerAdapter.MyViewHolder>() {

    fun setNewList(newList: ArrayList<TransHistory>) {
        this.walletTransactionData = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = WalletTransactionEachItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val walletData = walletTransactionData[position]
        holder.bind(walletData)
    }

    override fun getItemCount(): Int = walletTransactionData.size

    inner class MyViewHolder(private val binding: WalletTransactionEachItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walletData: TransHistory) {

            if (walletData.type == "debit") {
                binding.transactionModeTV.text = walletData.amountType

                val totalMinutes = walletData.durationMinutes
                val minutes = totalMinutes
                val seconds = 0
                val formattedTime = String.format("%02d:%02d", minutes, seconds)

                binding.durationTV.visibility = View.VISIBLE
                binding.durationTV.text = formattedTime

            } else {
                binding.transactionModeTV.text = "Added to wallet"
                binding.durationTV.visibility = View.GONE
            }
            binding.transactionIdTV.text = "Ref Id : ${walletData._id}"
            binding.transactionDateTV.text = walletData.createdAt

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            //TO get the code in 12 hr with AM/PM
            //val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            //For 24hr format
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

            try {
                val date: Date? = inputFormat.parse(walletData.createdAt)
                val formattedDate = if (date != null) dateFormat.format(date) else "N/A"
                val formattedTime = if (date != null) timeFormat.format(date) else "N/A"

                binding.transactionDateTV.text = formattedDate
                binding.transactionTimeTV.text = formattedTime
            } catch (e: ParseException) {
                e.printStackTrace()
                binding.transactionDateTV.text = "Invalid Date"
                binding.transactionTimeTV.text = "Invalid Time"
            }

            when (walletData.type) {
                "debit" -> {
                    binding.transactionAmountTV.text = "-₹ ${walletData.amount}"
                    binding.transactionAmountTV.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_debit
                        )
                    )
                }

                "credit" -> {
                    binding.transactionAmountTV.text = "+₹ ${walletData.amount}"
                    binding.transactionAmountTV.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.color_credit
                        )
                    )
                }
            }


        }
    }
}
