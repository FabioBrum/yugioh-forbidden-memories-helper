package com.example.characters.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.characters.R
import com.example.characters.adapters.DropListAdapter
import com.example.characters.databinding.FragmentCharacterDetailsBinding
import com.example.characters.model.OddType
import com.example.characters.viewmodels.CharacterDetailsViewModel
import com.example.designsystem.dialog.CardDetailDialogFragment
import org.koin.androidx.navigation.koinNavGraphViewModel

class CharacterDetailsFragment : Fragment(), DropListAdapter.DropListAdapterListener {

    private lateinit var binding: FragmentCharacterDetailsBinding

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val characterDetailsViewModel: CharacterDetailsViewModel by koinNavGraphViewModel(R.id.characters_navigation)

    private val powSAAdapter = DropListAdapter(this)
    private val tecSAAdapter = DropListAdapter(this)
    private val bCDAdapter = DropListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterDetailsViewModel.getCharacterInfo(args.characterName)

        with(binding) {
            recyclerViewCharacterDetailsCards.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewCharacterDetailsCards.adapter = powSAAdapter
            setupObservers()
            setupListeners()
        }
    }

    private fun FragmentCharacterDetailsBinding.setupObservers() {
        with(characterDetailsViewModel) {
            character.observe(viewLifecycleOwner) { character ->
                character ?: return@observe

                character.image?.let {
                    imageViewCharacterDetailsImage.setImageBitmap(it)
                }

                textViewCharacterDetailsName.text = character.name
            }

            uICardsWithOdds.observe(viewLifecycleOwner) { map ->
                powSAAdapter.cardList = map[OddType.POW_SA].orEmpty()
                tecSAAdapter.cardList = map[OddType.TEC_SA].orEmpty()
                bCDAdapter.cardList = map[OddType.POW_TEC_BCD].orEmpty()
            }
        }
    }

    private fun FragmentCharacterDetailsBinding.setupListeners() {
        radioGroupCharacterDetailsDropTypes.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                radioButtonCharacterDetailsPowSA.id ->
                    recyclerViewCharacterDetailsCards.adapter = powSAAdapter
                radioButtonCharacterDetailsTecSA.id ->
                    recyclerViewCharacterDetailsCards.adapter = tecSAAdapter
                else ->
                    recyclerViewCharacterDetailsCards.adapter = bCDAdapter
            }
        }

        imageButtonMainCardFiltersGoBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onCardClicked(cardId: String) {
        characterDetailsViewModel.loadCard(cardId).observe(viewLifecycleOwner) { card ->
            card?.let {
                CardDetailDialogFragment(it).show(childFragmentManager, "CardDetailDialogFragment")
            }
        }
    }

}