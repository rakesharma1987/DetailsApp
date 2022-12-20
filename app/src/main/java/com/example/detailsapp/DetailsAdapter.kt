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
        holder.layoutCustomRecyclerviewBinding.tvDetails.text = "Name - ${details.name}"

        holder.layoutCustomRecyclerviewBinding.btnShow.setOnClickListener {

        }
        holder.layoutCustomRecyclerviewBinding.btnEdit.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
       return detailsList.size
    }
}