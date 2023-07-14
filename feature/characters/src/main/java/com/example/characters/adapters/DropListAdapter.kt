package com.example.characters.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.characters.databinding.DropListItemBinding
import com.example.characters.model.UICardWithOdd

class DropListAdapter(
    private val listener: DropListAdapterListener
): RecyclerView.Adapter<DropListAdapter.DropListViewHolder>() {

    var cardList: List<UICardWithOdd> = emptyList(); set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropListViewHolder {
        return DropListViewHolder(
            DropListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = cardList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DropListViewHolder, position: Int) {
        with(cardList[position]) {
            holder.name.text = cardName
            holder.attribute.text = cardAttributes
            holder.odd.text = "$cardOdds%"

            holder.content.setOnClickListener {
                listener.onCardClicked(cardId)
            }
        }
    }

    interface DropListAdapterListener {
        fun onCardClicked(cardId: String)
    }

    inner class DropListViewHolder(itemView: DropListItemBinding): ViewHolder(itemView.root) {
        val content = itemView.constraintLaoyoutDropListItemContent
        val name = itemView.textViewDropListItemName
        val attribute = itemView.textViewDropListItemAttributes
        val odd = itemView.textViewDropListItemOdd
    }
}