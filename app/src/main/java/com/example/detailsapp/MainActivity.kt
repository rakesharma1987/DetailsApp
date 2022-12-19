package com.example.detailsapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.detailsapp.databinding.ActivityMainBinding
import com.example.detailsapp.databinding.LayoutMoreFiledsBinding
import com.example.detailsapp.databinding.LayoutSampleFieldsBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnSimpleFileds.setOnClickListener(this)
        binding.btnMoreFileds.setOnClickListener(this)
        binding.btnProVersion.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_simple_fileds -> {
                val dialog = Dialog(this)
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val sampleFields: LayoutSampleFieldsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(dialog.context),
                    R.layout.layout_sample_fields,
                    null,
                    false
                )
                dialog.setContentView(sampleFields.root)
                dialog.show()
                dialog.window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

                sampleFields.ivClose.setOnClickListener {
                    dialog.dismiss()
                }

                sampleFields.btnSubmit.setOnClickListener {
                    // TODO: insert into db
                }
            }

            R.id.btn_more_fileds -> {
                val dialog = Dialog(this)
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val moreFilds: LayoutMoreFiledsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(dialog.context),
                    R.layout.layout_more_fileds,
                    null,
                    false
                )
                dialog.setContentView(moreFilds.root)
                dialog.show()
                dialog.window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

                moreFilds.ivClose.setOnClickListener {
                    dialog.dismiss()
                }

                moreFilds.btnSubmit.setOnClickListener {
                    // TODO: insert into db
                }
            }

            R.id.btn_pro_version -> {

            }
        }
    }
}