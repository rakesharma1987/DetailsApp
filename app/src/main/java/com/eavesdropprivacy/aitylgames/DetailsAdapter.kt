package com.eavesdropprivacy.aitylgames

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eavesdropprivacy.aitylgames.databinding.LayoutCustomRecyclerviewBinding
import com.eavesdropprivacy.aitylgames.databinding.LayoutSampleFieldsBinding
import com.eavesdropprivacy.aitylgames.db.Details
import com.google.gson.Gson
import java.util.Calendar

class DetailsAdapter(private val context: Context, private val detailsList: List<Details>): RecyclerView.Adapter<DetailsAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(val layoutCustomRecyclerviewBinding: LayoutCustomRecyclerviewBinding): RecyclerView.ViewHolder(layoutCustomRecyclerviewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding: LayoutCustomRecyclerviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_custom_recyclerview,
            parent, false)
        return CustomViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//        val random = Random()
//        val currectColor =
//            Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
//        holder.layoutCustomRecyclerviewBinding.cardView.setCardBackgroundColor(currectColor)
        if(position % 2 == 0){
            holder.layoutCustomRecyclerviewBinding.cardView.setCardBackgroundColor(context.getColor(R.color.card_background_color_even))
        }else{
            holder.layoutCustomRecyclerviewBinding.cardView.setCardBackgroundColor(context.getColor(R.color.card_background_color_odd))
        }

        val details = detailsList[position]
        holder.layoutCustomRecyclerviewBinding.tvDetails.text = "${details.name}"

        holder.layoutCustomRecyclerviewBinding.btnShow.setOnClickListener {
            if (GooglePlayBillingPreferences.isPurchased()) {
                (context as MainActivity).interstitialAd.show()
            }
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("DATA", Gson().toJson(details))
            context.startActivity(intent)
        }
        holder.layoutCustomRecyclerviewBinding.btnEdit.setOnClickListener {
            if (!GooglePlayBillingPreferences.isPurchased()) {
                (context as MainActivity).interstitialAd.show()
            }

            val dialog = Dialog(context)
            dialog.setCancelable(false)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val sampleFields: LayoutSampleFieldsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(dialog.context),
                R.layout.layout_sample_fields,
                null,
                false
            )
            if (details.isNew) sampleFields.tvTitle.text = "Enter Details"
            else sampleFields.tvTitle.text = "Update Details"
            if (details.isMore) {
                sampleFields.llMoreFields.visibility = View.VISIBLE
                sampleFields.etDob.setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)


                    val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        sampleFields.tilDob.editText!!.setText("" + dayOfMonth + "/" + dayOfMonth.plus(1) + "/" + year)

                    }, year, month, day)

                    dpd.show()
                }
                if (!details.isNew) {
                    sampleFields.tilName.editText!!.setText(details.name)
                    sampleFields.tilPhone1.editText!!.setText(details.phoneNo1)
                    sampleFields.tilPhone2.editText!!.setText(details.phoneNo2)
                    sampleFields.tilEmail.editText!!.setText(details.email)
                    sampleFields.tilDob.editText!!.setText(details.dob)
                    sampleFields.tilAddress.editText!!.setText(details.address)
                    sampleFields.tilMessage.editText!!.setText(details.message)
                }
            } else {
                if (details.isNew) {
                    sampleFields.llMoreFields.visibility = View.GONE
                }else{
                    sampleFields.llMoreFields.visibility = View.GONE
                    sampleFields.tilName.editText!!.setText(details.name)
                    sampleFields.tilPhone1.editText!!.setText(details.phoneNo1)
                    sampleFields.tilPhone2.editText!!.setText(details.phoneNo2)
                    sampleFields.tilMessage.editText!!.setText(details.message)
                }
            }
            dialog.setContentView(sampleFields.root)
            dialog.show()
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            sampleFields.ivClose.setOnClickListener {
                dialog.dismiss()
            }

            sampleFields.btnSubmit.setOnClickListener {
                if (details.isMore) {
                    val detail = Details(
                        details.userId,
                        sampleFields.tilName.editText!!.text.toString(),
                        sampleFields.tilPhone1.editText!!.text.toString(),
                        sampleFields.tilPhone2.editText!!.text.toString(),
                        sampleFields.tilMessage.editText!!.text.toString(),
                        sampleFields.tilEmail.editText!!.text.toString(),
                        sampleFields.tilDob.editText!!.text.toString(),
                        sampleFields.tilAddress.editText!!.text.toString(),
                        details.isMore,
                        false
                    )
                    if(details.isNew){
                        (context as MainActivity).viewModel.insertDetails(detail)
                    } else{
                        (context as MainActivity).viewModel.updateDetails(detail)
                    }
                } else {
                    val detail = Details(
                        details.userId,
                        sampleFields.tilName.editText!!.text.toString(),
                        sampleFields.tilPhone1.editText!!.text.toString(),
                        sampleFields.tilPhone2.editText!!.text.toString(),
                        sampleFields.tilMessage.editText!!.text.toString(),
                        "",
                        "",
                        "",
                        details.isMore,
                        false
                    )
                    if(details.isNew){
                        (context as MainActivity).viewModel.insertDetails(detail)
                    } else{
                        (context as MainActivity).viewModel.updateDetails(detail)
                    }
                }
                dialog.dismiss()
            }

        }
    }

    override fun getItemCount(): Int {
       return detailsList.size
    }
}