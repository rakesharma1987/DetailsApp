package com.example.detailsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailsapp.*
import com.example.detailsapp.databinding.FragmentMoreFiledsBinding
import com.example.detailsapp.databinding.FragmentSimpleFieldsBinding
import com.example.detailsapp.db.AppDatabase
import com.example.detailsapp.db.Details

class MoreFiledsFragment : Fragment() {
    private lateinit var binding: FragmentMoreFiledsBinding
    private lateinit var factory: AppFactory
    private lateinit var viewModel: AppViewModel
    private var isMore: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_fileds, container, false)
        binding.recyclerViewMoreFields.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMoreFields.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = AppDatabase.getInstance(requireContext()).dao
        factory = AppFactory(AppRepository(dao))
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        viewModel.getAllAdvancedDetails.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                var list = mutableListOf<Details>()
                list.add(Details(0, "Name 1", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 2", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 3", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 4", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 5", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 6", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 7", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 8", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 9", "", "", "", "", "", "", true))
                list.add(Details(0, "Name 10", "", "", "", "", "", "", true))
                val adapter = DetailsAdapter(requireContext(), list)
                binding.recyclerViewMoreFields.adapter = adapter
            }else {
                var listTemp = mutableListOf<Details>()
                for (item in it){
                    listTemp.add(item)
                }
                if (listTemp.size < 10){
                    for (i in listTemp.size..9){
                        listTemp.add(Details(i+1, "Name ${i+1}", "", "", "", "", "", "", true))
                    }
                }
                val adapter = DetailsAdapter(requireContext(), listTemp)
                binding.recyclerViewMoreFields.adapter = adapter
            }
        })
    }
}