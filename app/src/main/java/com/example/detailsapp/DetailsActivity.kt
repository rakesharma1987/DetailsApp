package com.example.detailsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import com.example.detailsapp.databinding.ActivityDetailsBinding
import com.example.detailsapp.db.Details
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        val data = intent.getStringExtra("DATA")
        val detail = Gson().fromJson(data, Details::class.java)
        if (detail.isMore){
            binding.tvName.text = detail.name
            binding.tvPhone1.text = detail.phoneNo1
            if (detail.phoneNo2.isNotEmpty()) binding.tvPhone2.text = detail.phoneNo2
            binding.tvMsg.text = detail.message
            binding.tvEmail.text = detail.email
            binding.tvAddress.text = detail.address
            binding.tvDob.text = detail.dob
        }else{
            binding.tvAddress.visibility = GONE
            binding.tvLineBelowAddress.visibility = GONE
            binding.tvEmail.visibility = GONE
            binding.tvLineBelowEmail.visibility = GONE
            binding.tvDob.visibility = GONE
            binding.tvLineBelowDob.visibility = GONE

            binding.tvName.text = detail.name
            binding.tvPhone1.text = detail.phoneNo1
            if (detail.phoneNo2.isNotEmpty()) binding.tvPhone2.text = detail.phoneNo2
            binding.tvMsg.text = detail.message
        }
    }
}