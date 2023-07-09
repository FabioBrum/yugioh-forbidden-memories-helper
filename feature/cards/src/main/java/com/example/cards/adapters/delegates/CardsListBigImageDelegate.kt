package com.example.cards.adapters.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.databinding.CardListBigImageItemBinding
import com.example.domain.model.Card

class CardsListBigImageDelegate {

    fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): CardsListBigImageViewHolder {
        return CardsListBigImageViewHolder(
            CardListBigImageItemBinding.inflate(
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
        with(holder as CardsListBigImageViewHolder) {
            name.text = card.name
            card.image?.let {
                image.setImageBitmap(it)
            }
            attributes.text = "%d/%d".format(card.attack,card.defense)
        }
    }

    inner class CardsListBigImageViewHolder(itemView: CardListBigImageItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.textViewCardListDetailsCardName
        val image = itemView.imageViewCardListDetailsCardImage
        val attributes = itemView.textViewCardListDetailsCardAttributes
    }
}