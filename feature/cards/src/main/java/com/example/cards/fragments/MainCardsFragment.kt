package com.example.cards.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cards.R
import com.example.cards.adapters.CardsListAdapter
import com.example.cards.databinding.FragmentMainCardsBinding
import com.example.cards.di.featureCardsModule
import com.example.cards.viewmodels.MainCardsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainCardsFragment : Fragment() {

    private val cardsListAdapter = CardsListAdapter()
    private lateinit var binding: FragmentMainCardsBinding
    private val mainCardsViewModel by viewModel<MainCardsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCardsBinding.inflate(inflater, container, false)
        loadKoinModules(featureCardsModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)

        mainCardsViewModel.allCards.observe(viewLifecycleOwner) { allCards ->
            with(binding.recyclerViewMainCardsCardsList) {
                cardsListAdapter.allCards = allCards
                adapter = cardsListAdapter
                layoutManager = gridLayoutManager
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(featureCardsModule)
    }

}