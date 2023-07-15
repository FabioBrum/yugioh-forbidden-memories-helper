package com.example.fusion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.designsystem.adapters.CardsListAdapter
import com.example.domain.model.Card
import com.example.domain.model.ListType
import com.example.fusion.databinding.FragmentCreateFusionBinding
import com.example.fusion.di.fusionModule
import com.example.fusion.viewmodels.CreateFusionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class CreateFusionFragment : Fragment(), CardsListAdapter.CardsListListener {

    private val cardListAdapter = CardsListAdapter(this)
    private lateinit var binding: FragmentCreateFusionBinding
    private val createFusionViewModel by viewModel<CreateFusionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateFusionBinding.inflate(inflater, container, false)
        loadKoinModules(fusionModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupObservers()
            setupListeners()
        }
    }

    private fun FragmentCreateFusionBinding.setupObservers() {
        createFusionViewModel.allCards.observe(viewLifecycleOwner) { allCards ->
            cardListAdapter.allCards = allCards
            cardListAdapter.setListState(ListType.EXPENDED)
            recyclerViewCreateFusionCardsList.adapter = cardListAdapter
            recyclerViewCreateFusionCardsList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        createFusionViewModel.selectedCardOneId.observe(viewLifecycleOwner) { cardId ->
            val card = createFusionViewModel.allCards.value?.firstOrNull { it.id == cardId }
            card?.image?.let {
                imageViewCreateFusionCardOne.setImageBitmap(card.image)
            } ?: imageViewCreateFusionCardOne.setImageBitmap(null)
            updateSaveButtonState()
        }
        createFusionViewModel.selectedCardTwoId.observe(viewLifecycleOwner) { cardId ->
            val card = createFusionViewModel.allCards.value?.firstOrNull { it.id == cardId }
            card?.image?.let {
                imageViewCreateFusionCardTwo.setImageBitmap(card.image)
            } ?: imageViewCreateFusionCardTwo.setImageBitmap(null)
            updateSaveButtonState()
        }
        createFusionViewModel.selectedCardResultId.observe(viewLifecycleOwner) { cardId ->
            val card = createFusionViewModel.allCards.value?.firstOrNull { it.id == cardId }
            card?.image?.let {
                imageViewCreateFusionResult.setImageBitmap(card.image)
            } ?: imageViewCreateFusionResult.setImageBitmap(null)
            updateSaveButtonState()
        }

        imageViewCreateFusionCardOne.setOnClickListener {
            createFusionViewModel.clearCardOne()
        }
        imageViewCreateFusionCardTwo.setOnClickListener {
            createFusionViewModel.clearCardTwo()
        }
        imageViewCreateFusionResult.setOnClickListener {
            createFusionViewModel.clearCardResult()
        }
    }

    private fun FragmentCreateFusionBinding.setupListeners() {
        editTextCreateFusionSearchName.addTextChangedListener {
            cardListAdapter.filterByName(it.toString())
        }

        editTextCreateFusionSearchAttack.addTextChangedListener {
            cardListAdapter.filterByAttack(it.toString())
        }

        editTextCreateFusionSearchDefense.addTextChangedListener {
            cardListAdapter.filterByDefense(it.toString())
        }
    }

    private fun FragmentCreateFusionBinding.updateSaveButtonState() {
        materialButtonCreateFusionSave.isEnabled =
            with(createFusionViewModel) {
                selectedCardOneId.value != null &&
                selectedCardTwoId.value != null &&
                selectedCardResultId.value != null
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(fusionModule)
    }

    override fun onCardClicked(card: Card) {
        createFusionViewModel.updateSelectedCards(card.id)
    }

}