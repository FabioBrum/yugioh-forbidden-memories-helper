package com.example.fusion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
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

        createFusionViewModel.selectedCardOne.observe(viewLifecycleOwner) { card ->
            val selectedCard = createFusionViewModel.allCards.value?.firstOrNull { it.id == card?.id }
            selectedCard?.image?.let {
                imageViewCreateFusionCardOne.setImageBitmap(selectedCard.image)
            } ?: imageViewCreateFusionCardOne.setImageBitmap(null)
            updateSaveButtonState()
        }
        createFusionViewModel.selectedCardTwo.observe(viewLifecycleOwner) { card ->
            val selectedCard = createFusionViewModel.allCards.value?.firstOrNull { it.id == card?.id }
            selectedCard?.image?.let {
                imageViewCreateFusionCardTwo.setImageBitmap(selectedCard.image)
            } ?: imageViewCreateFusionCardTwo.setImageBitmap(null)
            updateSaveButtonState()
        }
        createFusionViewModel.selectedCardResult.observe(viewLifecycleOwner) { card ->
            val selectedCard = createFusionViewModel.allCards.value?.firstOrNull { it.id == card?.id }
            selectedCard?.image?.let {
                imageViewCreateFusionResult.setImageBitmap(selectedCard.image)
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

        materialButtonCreateFusionSave.setOnClickListener {
            createFusionViewModel.saveFusion().observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }
    }

    private fun FragmentCreateFusionBinding.setupListeners() {
        editTextCreateFusionSearchName.addTextChangedListener {
            filterCards()
        }

        editTextCreateFusionSearchAttack.addTextChangedListener {
            filterCards()
        }

        editTextCreateFusionSearchDefense.addTextChangedListener {
            filterCards()
        }
    }

    private fun FragmentCreateFusionBinding.filterCards() {
        val name = editTextCreateFusionSearchName.text.toString()
        val attack = editTextCreateFusionSearchAttack.text.toString()
        val defense = editTextCreateFusionSearchDefense.text.toString()

        cardListAdapter.allCards =
            createFusionViewModel.allCards.value.orEmpty().filter { card ->
                (if(name.isNotBlank()) {
                    card.name.lowercase().contains(name.lowercase())
                } else true ) &&
                (if(attack.isNotBlank()) {
                    attack.toInt() == card.attack
                } else true) &&
                (if(defense.isNotBlank()) {
                    defense.toInt() == card.defense
                } else true)
            }
    }

    private fun FragmentCreateFusionBinding.updateSaveButtonState() {
        materialButtonCreateFusionSave.isEnabled =
            with(createFusionViewModel) {
                selectedCardOne.value != null &&
                selectedCardTwo.value != null &&
                selectedCardResult.value != null
            }
    }

    override fun onCardClicked(card: Card) {
        createFusionViewModel.updateSelectedCards(card)
    }

}