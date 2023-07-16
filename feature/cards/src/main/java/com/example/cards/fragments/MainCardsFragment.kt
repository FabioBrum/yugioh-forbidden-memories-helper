package com.example.cards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cards.R
import com.example.designsystem.adapters.CardsListAdapter
import com.example.cards.databinding.FragmentMainCardsBinding
import com.example.cards.di.featureCardsModule
import com.example.domain.model.ListType
import com.example.cards.viewmodels.MainCardsViewModel
import com.example.designsystem.dialog.CardDetailDialogFragment
import com.example.domain.model.Card
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainCardsFragment : Fragment(), CardsListAdapter.CardsListListener {

    private val cardsListAdapter = CardsListAdapter(this)
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

        with(binding) {
            setupObservers()
            setupListeners()
        }
    }

    private fun FragmentMainCardsBinding.setupObservers() {
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

    private fun FragmentMainCardsBinding.setupListeners() {
        imageButtonMainCardsGoToFiltersScreen.setOnClickListener {
            val action = MainCardsFragmentDirections.mainCardsFragmentToMainCardsFiltersFragment()
            findNavController().navigate(action)
        }

        editTextMainCardsSearchName.addTextChangedListener {
            val allCards = mainCardsViewModel.allCards.value.orEmpty()

            cardsListAdapter.allCards =
                allCards.filter { card -> card.name.contains(it.toString(), true) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(featureCardsModule)
    }

    override fun onCardClicked(card: Card) {
        CardDetailDialogFragment(card).show(childFragmentManager, "CardDetailDialogFragment")
    }

}