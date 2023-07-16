package com.example.fusion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.designsystem.adapters.FusionListAdapter
import com.example.fusion.databinding.FragmentMainFusionBinding
import com.example.fusion.di.fusionModule
import com.example.fusion.viewmodels.MainFusionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainFusionFragment : Fragment() {

    private val fusionListAdapter = FusionListAdapter()
    private lateinit var binding: FragmentMainFusionBinding
    private val mainFusionViewModel by viewModel<MainFusionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainFusionBinding.inflate(inflater, container, false)
        loadKoinModules(fusionModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFusionViewModel.loadAllFusions()

        with(binding) {
            setupObservers()
            setupListeners()
        }
    }

    private fun FragmentMainFusionBinding.setupObservers() {
        mainFusionViewModel.allFusions.observe(viewLifecycleOwner) { allFusions ->
            fusionListAdapter.allFusions = allFusions
            recyclerViewMainFusionAllFusions.adapter = fusionListAdapter
            recyclerViewMainFusionAllFusions.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun FragmentMainFusionBinding.setupListeners() {
        buttonMainFusionAddFusion.setOnClickListener {
            val action =
                MainFusionFragmentDirections.actionMainFusionFragmentToCreateFusionFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(fusionModule)
    }

}