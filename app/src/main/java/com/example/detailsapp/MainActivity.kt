package com.example.detailsapp

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailsapp.databinding.ActivityMainBinding
import com.example.detailsapp.databinding.LayoutMoreFiledsBinding
import com.example.detailsapp.databinding.LayoutSampleFieldsBinding
import com.example.detailsapp.db.AppDatabase
import com.example.detailsapp.db.Details
import com.example.detailsapp.fragments.MoreFiledsFragment
import com.example.detailsapp.fragments.SimpleFieldsFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: AppFactory
    private lateinit var viewModel: AppViewModel
    private var isMore: Boolean = false
    private lateinit var fragmentManager: FragmentManager
    private var isAddSimpleFields: Boolean = false
    private var isAddMoreFields: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = AppDatabase.getInstance(this).dao
        factory = AppFactory(AppRepository(dao))
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        fragmentManager = supportFragmentManager
        var fragmetnTransaction = fragmentManager.beginTransaction()
        fragmetnTransaction.add(R.id.fragment_container, SimpleFieldsFragment())
        fragmetnTransaction.commit()

        binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))

        binding.btnSimpleFileds.setOnClickListener(this)
        binding.btnMoreFileds.setOnClickListener(this)
        binding.btnProVersion.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_simple_fileds -> {
                binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))
                binding.btnSimpleFileds.setBackgroundColor(getColor(R.color.purple_500))
                binding.btnMoreFileds.text = "More Fields"
                isAddMoreFields = false

                var fragmetnTransaction = fragmentManager.beginTransaction()
                for (fragment in fragmentManager.fragments){
                    fragmetnTransaction.remove(fragment)
                }
                fragmetnTransaction.add(R.id.fragment_container, SimpleFieldsFragment())
                fragmetnTransaction.commit()
                if (isAddSimpleFields) {
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
                        val detail = Details(
                            0,
                            sampleFields.tilName.editText!!.text.toString(),
                            sampleFields.tilPhone1.editText!!.text.toString(),
                            sampleFields.tilPhone2.editText!!.text.toString(),
                            sampleFields.tilMessage.editText!!.text.toString(),
                            "",
                            "",
                            "",
                            isMore
                        )
                        viewModel.insertDetails(detail)
                        dialog.dismiss()
                    }
                }
                binding.btnSimpleFileds.text = "Add Simple Fileds"
                isAddSimpleFields = true
            }

            R.id.btn_more_fileds -> {
                binding.btnSimpleFileds.text = "Simple Fields"
                isAddSimpleFields = false
                binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))
                binding.btnSimpleFileds.setBackgroundColor(getColor(R.color.purple_500))
                var fragmetnTransaction1 = fragmentManager.beginTransaction()
                for (fragment in fragmentManager.fragments){
                    fragmetnTransaction1.remove(fragment)
                }
                fragmetnTransaction1.add(R.id.fragment_container, MoreFiledsFragment())
                fragmetnTransaction1.commit()

                if (binding.btnMoreFileds.text.equals("Add More Fields")) {
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
                        val detail = Details(
                            0,
                            sampleFields.tilName.editText!!.text.toString(),
                            sampleFields.tilPhone1.editText!!.text.toString(),
                            sampleFields.tilPhone2.editText!!.text.toString(),
                            sampleFields.tilMessage.editText!!.text.toString(),
                            sampleFields.tilPhone2.editText!!.text.toString(),
                            sampleFields.tilEmail.editText!!.text.toString(),
                            sampleFields.tilAddress.editText!!.text.toString(),
                            isMore
                        )
                        viewModel.insertDetails(detail)
                        dialog.dismiss()
                    }
                }
                binding.btnMoreFileds.text = "Add More Fields"
                isAddMoreFields = true
            }

            R.id.btn_pro_version -> {

            }
        }
    }
}