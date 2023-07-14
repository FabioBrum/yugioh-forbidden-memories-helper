package com.example.characters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.characters.databinding.CharactersListItemBinding
import com.example.characters.model.UICharacter

class CharactersListAdapter(
    private val listener: CharactersListAdapterListener
): RecyclerView.Adapter<CharactersListAdapter.CharactersListViewHolder>() {

    var allCharacters: List<UICharacter> = emptyList(); private set

    fun setAllCharacters(allCharacters: List<UICharacter>) {
        this.allCharacters = allCharacters
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        return CharactersListViewHolder(
            CharactersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = allCharacters.size

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        with(allCharacters[position]) {
            holder.image.setImageBitmap(image)
            holder.name.text = name
            holder.content.setOnClickListener {
                listener.onCharacterClicked(name)
            }
        }
    }

    interface CharactersListAdapterListener {
        fun onCharacterClicked(name: String)
    }

    inner class CharactersListViewHolder(itemView: CharactersListItemBinding): ViewHolder(itemView.root) {
        val image = itemView.imageViewCharactersListImage
        val name = itemView.textViewCharactersListName
        val content = itemView.cardViewCharactersListContent
    }
}