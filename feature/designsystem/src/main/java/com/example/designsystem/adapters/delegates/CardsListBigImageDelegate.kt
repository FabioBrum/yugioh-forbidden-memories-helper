package com.example.designsystem.adapters.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.designsystem.databinding.CardListBigImageItemBinding
import com.example.domain.model.Card

class CardsListBigImageDelegate(private val listener: CardsListBigImageListener) {

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

            cardView.setOnClickListener {
                listener.onCardClicked(card)
            }
        }
    }

    interface CardsListBigImageListener {
        fun onCardClicked(card: Card)
    }

    inner class CardsListBigImageViewHolder(itemView: CardListBigImageItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val cardView = itemView.cardViewCardListDetailsContent
        val name = itemView.textViewCardListDetailsCardName
        val image = itemView.imageViewCardListDetailsCardImage
        val attributes = itemView.textViewCardListDetailsCardAttributes
    }
}