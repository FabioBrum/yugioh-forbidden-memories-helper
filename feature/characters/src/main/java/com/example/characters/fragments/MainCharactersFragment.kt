package com.example.characters.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.characters.R
import com.example.characters.viewmodels.MainCharactersViewModel
import com.example.characters.adapters.CharactersListAdapter
import com.example.characters.databinding.FragmentMainCharactersBinding
import com.example.characters.di.featureCharactersModule
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainCharactersFragment : Fragment(), CharactersListAdapter.CharactersListAdapterListener {

    private val charactersListAdapter = CharactersListAdapter(this)
    private lateinit var binding: FragmentMainCharactersBinding
    private val mainCharactersViewModel: MainCharactersViewModel by koinNavGraphViewModel(R.id.characters_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCharactersBinding.inflate(inflater, container, false)
        loadKoinModules(featureCharactersModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupObservers()
        }
    }

    private fun FragmentMainCharactersBinding.setupObservers() {
        mainCharactersViewModel.allCharacters.observe(viewLifecycleOwner) {
            charactersListAdapter.setAllCharacters(it)
            recyclerViewMainCharactersCharacters.adapter = charactersListAdapter
            recyclerViewMainCharactersCharacters.layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(featureCharactersModule)
    }

    override fun onCharacterClicked(name: String) {
        val action = MainCharactersFragmentDirections.actionMainCharactersFragmentToCharacterDetailsFragment(
            name
        )
        findNavController().navigate(action)
    }

}