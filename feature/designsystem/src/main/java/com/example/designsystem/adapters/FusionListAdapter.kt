package com.example.designsystem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.designsystem.databinding.FusionItemBinding
import com.example.domain.model.Fusion

class FusionListAdapter: Adapter<FusionListAdapter.FusionListViewHolder>() {

    var allFusions: List<Fusion> = emptyList(); set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FusionListViewHolder {
        return FusionListViewHolder(
            FusionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = allFusions.size

    override fun onBindViewHolder(holder: FusionListViewHolder, position: Int) {
        with(allFusions[position]) {
            cardOne.image?.let {
                holder.imageCardOne.setImageBitmap(it)
            }

            cardTwo.image?.let {
                holder.imageCardTwo.setImageBitmap(it)
            }

            finalCard.image?.let {
                holder.imageResultCard.setImageBitmap(it)
            }
        }
    }

    inner class FusionListViewHolder(itemView: FusionItemBinding): ViewHolder(itemView.root) {
        val imageCardOne = itemView.imageViewFusionItemCardOne
        val imageCardTwo = itemView.imageViewFusionItemCardTwo
        val imageResultCard = itemView.imageViewFusionItemResult
    }
}