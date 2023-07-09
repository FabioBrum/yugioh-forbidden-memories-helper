package com.example.designsystem.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.designsystem.databinding.CardListDetailsItemBinding
import com.example.domain.model.Card

class CardsListDetailsDelegate {

    fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): CardsListDetailsViewHolder {
        return CardsListDetailsViewHolder(
            CardListDetailsItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        card: Card
    ) {
        with(holder as CardsListDetailsViewHolder) {
            name.text = card.name
            attributes.text = "%d/%d".format(card.attack,card.defense)
        }
    }

    inner class CardsListDetailsViewHolder(itemView: CardListDetailsItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.textViewCardListDetailsName
        val attributes = itemView.textViewCardListDetailsAttributes
    }

}