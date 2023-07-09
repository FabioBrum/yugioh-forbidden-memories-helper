package com.example.cards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cards.adapters.delegates.CardsListBigImageDelegate
import com.example.designsystem.delegates.CardsListDetailsDelegate
import com.example.domain.model.Card

class CardsListAdapter: RecyclerView.Adapter<ViewHolder>() {

    var allCards: List<Card> = emptyList(); set(value) {
        field = value
    }

    private val cardsListDetailsDelegate = CardsListDetailsDelegate()
    private val cardsListBigImageDelegate = CardsListBigImageDelegate()
    var listState: CardsListAdapterState = CardsListAdapterState.CARD_LIST_DETAILS_TYPE; private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            CardsListAdapterState.CARD_LIST_DETAILS_TYPE.value -> {
                cardsListDetailsDelegate.onCreateViewHolder(
                    LayoutInflater.from(parent.context),
                    parent
                )
            }
            CardsListAdapterState.CARD_LIST_BIG_IMAGES_TYPE.value -> {
                cardsListBigImageDelegate.onCreateViewHolder(
                    LayoutInflater.from(parent.context),
                    parent
                )
            }
            else -> {
                cardsListBigImageDelegate.onCreateViewHolder(
                    LayoutInflater.from(parent.context),
                    parent
                )
            }
        }
    }

    override fun getItemCount() = allCards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(listState) {
            CardsListAdapterState.CARD_LIST_DETAILS_TYPE -> {
                cardsListDetailsDelegate.onBindViewHolder(
                    holder,
                    allCards[position]
                )
            }
            CardsListAdapterState.CARD_LIST_BIG_IMAGES_TYPE -> {
                cardsListBigImageDelegate.onBindViewHolder(
                    holder,
                    allCards[position]
                )
            }
        }
    }

    override fun getItemViewType(position: Int) = listState.value

    fun toggleListType() {
        listState = when(listState) {
            CardsListAdapterState.CARD_LIST_DETAILS_TYPE -> CardsListAdapterState.CARD_LIST_BIG_IMAGES_TYPE
            CardsListAdapterState.CARD_LIST_BIG_IMAGES_TYPE -> CardsListAdapterState.CARD_LIST_DETAILS_TYPE
        }
        notifyDataSetChanged()
    }

    enum class CardsListAdapterState(val value: Int) {
        CARD_LIST_DETAILS_TYPE(1),
        CARD_LIST_BIG_IMAGES_TYPE(2)
    }
}