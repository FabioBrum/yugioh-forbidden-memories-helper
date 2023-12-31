package com.example.designsystem.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.designsystem.databinding.FragmentCardDetailDialogBinding
import com.example.domain.model.Card
import com.example.domain.model.Type
import java.util.Locale

class CardDetailDialogFragment(private val card: Card) : DialogFragment() {

    private lateinit var binding: FragmentCardDetailDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardDetailDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            when(card.type) {
                Type.MONSTER -> handleMonsterTypeCard(card)
                else -> handleOtherTypesOfCard(card)
            }

            setupListeners()
        }
    }

    private fun FragmentCardDetailDialogBinding.handleMonsterTypeCard(card: Card) {
        textViewCardDetailsDialogCardName.text = card.name
        textViewCardDetailsDialogCardAttributes.text = "%d/%d".format(card.attack, card.defense)
        card.image?.let {
            imageViewCardDetailsDialogCardImage.setImageBitmap(card.image)
        }

        textViewCardDetailsDialogGuardians.text = "%s / %s".format(
            card.guardians.first.name.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            card.guardians.second.name.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        )
    }

    private fun FragmentCardDetailDialogBinding.handleOtherTypesOfCard(card: Card) {
        textViewCardDetailsDialogCardName.text = card.name
        textViewCardDetailsDialogCardAttributes.text =
            card.type.name.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }

    private fun FragmentCardDetailDialogBinding.setupListeners() {
        materialButtonCardDetailsDialogHowToMakeIt.setOnClickListener {
            navigateToCardFusions(getFusionsThatGenerateCard = true)
        }
        materialButtonCardDetailsDialogWhatDoesItMake.setOnClickListener {
            navigateToCardFusions(getFusionsThatGenerateCard = false)
        }
    }

    private fun navigateToCardFusions(getFusionsThatGenerateCard: Boolean) {
        val uri =
            Uri.parse(
                "app://com.example.yugioh_forbidden_memories_helper/cardFusionsFragment/cardId=${card.id}&getFusionsThatGenerateCard=${getFusionsThatGenerateCard}&cardName=${card.name}"
            )
        val request = NavDeepLinkRequest.Builder.fromUri(uri).build()
        findNavController().navigate(request)
    }
}