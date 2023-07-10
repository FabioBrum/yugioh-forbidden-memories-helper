package com.example.cards.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cards.R
import com.example.cards.adapters.CardsListAdapter
import com.example.cards.databinding.FragmentMainCardsBinding
import com.example.cards.di.featureCardsModule
import com.example.cards.model.ListType
import com.example.cards.viewmodels.MainCardsViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainCardsFragment : Fragment() {

    private val cardsListAdapter = CardsListAdapter()
    private lateinit var binding: FragmentMainCardsBinding
    private val mainCardsViewModel: MainCardsViewModel by koinNavGraphViewModel(R.id.cards_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCardsBinding.inflate(inflater, container, false)
        loadKoinModules(featureCardsModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonMainCardsGoToFiltersScreen.setOnClickListener {
            val action = MainCardsFragmentDirections.mainCardsFragmentToMainCardsFiltersFragment()
            findNavController().navigate(action)
        }

        mainCardsViewModel.allCards.observe(viewLifecycleOwner) {
            val currentFilter = mainCardsViewModel.filters.value

            val numOfColumns = when(currentFilter?.listType) {
                ListType.COMPACT -> 1
                ListType.EXPENDED, null -> 2
            }
            val gridLayoutManager = GridLayoutManager(context, numOfColumns)
            cardsListAdapter.setListState(currentFilter?.listType)

            with(recyclerViewMainCardsCardsList) {
                cardsListAdapter.allCards = mainCardsViewModel.filterCards()
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