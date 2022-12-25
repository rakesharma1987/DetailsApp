package com.eavesdropprivacy.aitylgames.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eavesdropprivacy.aitylgames.*
import com.eavesdropprivacy.aitylgames.databinding.FragmentSimpleFieldsBinding
import com.eavesdropprivacy.aitylgames.db.AppDatabase
import com.eavesdropprivacy.aitylgames.db.Details

class SimpleFieldsFragment : Fragment() {
    private lateinit var binding: FragmentSimpleFieldsBinding
    private lateinit var factory: AppFactory
    private lateinit var viewModel: AppViewModel
    private var isMore: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_simple_fields, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = AppDatabase.getInstance(requireContext()).dao
        factory = AppFactory(AppRepository(dao))
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        viewModel.getAllDetails.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                var list = mutableListOf<Details>()
                list.add(Details(0, "Name 1", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 2", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 3", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 4", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 5", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 6", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 7", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 8", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 9", "", "", "", "", "", "", false, true))
                list.add(Details(0, "Name 10", "", "", "", "", "", "", false, true))
                val adapter = DetailsAdapter(requireContext(), list)
                binding.recyclerView.adapter = adapter
            }else {
                var listTemp = mutableListOf<Details>()
                for (item in it){
                    listTemp.add(item)
                }
                if (listTemp.size < 10){
                    for (i in listTemp.size..9){
                        listTemp.add(Details(i+1, "Name ${i+1}", "", "", "", "", "", "", false, true))
                    }
                }
                val adapter = DetailsAdapter(requireContext(), listTemp)
                binding.recyclerView.adapter = adapter
            }
        })
    }

}