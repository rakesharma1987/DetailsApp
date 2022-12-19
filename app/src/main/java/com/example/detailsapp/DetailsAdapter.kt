package com.example.detailsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.detailsapp.databinding.LayoutCustomRecyclerviewBinding
import com.example.detailsapp.db.Details

class DetailsAdapter(private val detailsList: List<Details>): RecyclerView.Adapter<DetailsAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(val layoutCustomRecyclerviewBinding: LayoutCustomRecyclerviewBinding): RecyclerView.ViewHolder(layoutCustomRecyclerviewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding: LayoutCustomRecyclerviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_custom_recyclerview,
            parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val details = detailsList[position]
        holder.layoutCustomRecyclerviewBinding.btnShow.setOnClickListener {
            holder.layoutCustomRecyclerviewBinding.tvDetails.visibility = View.VISIBLE
            holder.layoutCustomRecyclerviewBinding.btnShow.visibility = View.GONE
            holder.layoutCustomRecyclerviewBinding.btnHide.visibility = View.VISIBLE
            holder.layoutCustomRecyclerviewBinding.tvDetails.text = "Name: ${details.name} \n" +
                    "Phone - 1: ${details.phoneNo1} \n" + "Phone - 2: ${details.phoneNo2} \n"+ "Message: ${details.message}"
        }
        holder.layoutCustomRecyclerviewBinding.btnHide.setOnClickListener {
            holder.layoutCustomRecyclerviewBinding.tvDetails.visibility = View.GONE
            holder.layoutCustomRecyclerviewBinding.btnHide.visibility = View.GONE
            holder.layoutCustomRecyclerviewBinding.btnShow.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
       return detailsList.size
    }
}