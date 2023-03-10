package com.eavesdropprivacy.aitylgames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import com.eavesdropprivacy.aitylgames.databinding.ActivityDetailsBinding
import com.eavesdropprivacy.aitylgames.db.Details
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        supportActionBar!!.title = "Personal Details"

        val data = intent.getStringExtra("DATA")
        val detail = Gson().fromJson(data, Details::class.java)
        if (detail.isMore){
            binding.tvName.text = "Name: ${detail.name}"
            binding.tvPhone1.text = "Phone 1: ${detail.phoneNo1}"
            binding.tvPhone2.text = "Phone 2: ${detail.phoneNo2}"
            binding.tvMsg.text = "Message: \n${detail.message}"
            binding.tvEmail.text = "Email: ${detail.email}"
            binding.tvAddress.text = "Address: ${detail.address}"
            binding.tvDob.text = "Dob: ${detail.dob}"
        }else{
            binding.tvAddress.visibility = GONE
            binding.tvLineBelowAddress.visibility = GONE
            binding.tvEmail.visibility = GONE
            binding.tvLineBelowEmail.visibility = GONE
            binding.tvDob.visibility = GONE
            binding.tvLineBelowDob.visibility = GONE

            binding.tvName.text = "Name: ${detail.name}"
            binding.tvPhone1.text = "Phone 1: ${detail.phoneNo1}"
            binding.tvPhone2.text = "Phone 2: ${detail.phoneNo2}"
            binding.tvMsg.text = "Message: \n${detail.message}"
        }
    }
}