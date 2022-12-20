package com.example.detailsapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailsapp.databinding.ActivityMainBinding
import com.example.detailsapp.databinding.LayoutMoreFiledsBinding
import com.example.detailsapp.databinding.LayoutSampleFieldsBinding
import com.example.detailsapp.db.AppDatabase
import com.example.detailsapp.db.Details

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: AppFactory
    private lateinit var viewModel: AppViewModel
    private var isMore: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = AppDatabase.getInstance(this).dao
        factory = AppFactory(AppRepository(dao))
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)


        binding.btnSimpleFileds.setOnClickListener(this)
        binding.btnMoreFileds.setOnClickListener(this)
        binding.btnProVersion.setOnClickListener(this)

        viewModel.getAllDetails.observe(this, Observer {
            val adapter = DetailsAdapter(this, it)
            binding.recyclerView.adapter = adapter
        })
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_simple_fileds -> {
                isMore = false
                val dialog = Dialog(this)
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val sampleFields: LayoutSampleFieldsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(dialog.context),
                    R.layout.layout_sample_fields,
                    null,
                    false
                )
                sampleFields.llMoreFields.visibility = View.GONE
                dialog.setContentView(sampleFields.root)
                dialog.show()
                dialog.window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

                sampleFields.ivClose.setOnClickListener {
                    dialog.dismiss()
                }

                sampleFields.btnSubmit.setOnClickListener {
                    val detail = Details(0, sampleFields.tilName.editText!!.text.toString(), sampleFields.tilPhone1.editText!!.text.toString(),
                    sampleFields.tilPhone2.editText!!.text.toString(), sampleFields.tilMessage.editText!!.text.toString(), "", "", "", isMore)
                    viewModel.insertDetails(detail)
                    dialog.dismiss()
                }
            }

            R.id.btn_more_fileds -> {
                isMore = true
                val dialog = Dialog(this)
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val sampleFields: LayoutSampleFieldsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(dialog.context),
                    R.layout.layout_sample_fields,
                    null,
                    false
                )
                sampleFields.llMoreFields.visibility = View.VISIBLE
                dialog.setContentView(sampleFields.root)
                dialog.show()
                dialog.window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

                sampleFields.ivClose.setOnClickListener {
                    dialog.dismiss()
                }

                sampleFields.btnSubmit.setOnClickListener {
                    val detail = Details(0, sampleFields.tilName.editText!!.text.toString(), sampleFields.tilPhone1.editText!!.text.toString(),
                        sampleFields.tilPhone2.editText!!.text.toString(), sampleFields.tilMessage.editText!!.text.toString(),
                        sampleFields.tilPhone2.editText!!.text.toString(), sampleFields.tilEmail.editText!!.text.toString(),
                        sampleFields.tilMessage.editText!!.text.toString(), isMore)
                    viewModel.insertDetails(detail)
                    dialog.dismiss()
                }
            }

            R.id.btn_pro_version -> {

            }
        }
    }
}