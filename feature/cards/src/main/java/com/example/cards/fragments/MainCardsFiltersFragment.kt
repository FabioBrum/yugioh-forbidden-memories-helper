package com.example.cards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.cards.R
import com.example.cards.databinding.FragmentMainCardsFiltersBinding
import com.example.domain.model.ListType
import com.example.domain.model.OrderBy
import com.example.domain.model.Ordination
import com.example.cards.viewmodels.MainCardsFiltersViewModel
import com.example.cards.viewmodels.MainCardsViewModel
import com.example.domain.model.Nature
import com.example.domain.model.Type
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainCardsFiltersFragment : Fragment() {
    private lateinit var binding: FragmentMainCardsFiltersBinding

    private val mainCardsFiltersViewModel by viewModel<MainCardsFiltersViewModel>()
    private val mainCardsViewModel: MainCardsViewModel by koinNavGraphViewModel(R.id.cards_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCardsFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainCardsViewModel.filters.value?.let { filters ->
                mainCardsFiltersViewModel.initFilter(filters)
                handleListType(filters.listType)
                handleCardTypes(filters.cardTypes)
                handleOrderBy(filters.orderBy)
                handleOrdination(filters.ordination)
                handleAttackRange(filters.attackRange)
                handleDefenseRange(filters.defenseRange)
                handleCardNatures(filters.cardNatures)
                handleFiltersWithMonsters(filters.cardTypes.contains(Type.MONSTER))
            }
            setupListeners()
        }
    }

    private fun FragmentMainCardsFiltersBinding.setupListeners() {
        radioGroupMainCardFiltersListType.setOnCheckedChangeListener { _, checkedId ->
            val listType = when(checkedId) {
                radioButtonMainCardFiltersExpanded.id -> ListType.EXPENDED
                else -> ListType.COMPACT
            }
            mainCardsFiltersViewModel.updateListType(listType)
        }

        radioGroupMainCardFiltersOrderBy.setOnCheckedChangeListener { _, checkedId ->
            val orderBy = when(checkedId) {
                radioButtonMainCardFiltersName.id -> OrderBy.NAME
                radioButtonMainCardFiltersAttack.id -> OrderBy.ATTACK
                else -> OrderBy.DEFENSE
            }
            mainCardsFiltersViewModel.updateOrderBy(orderBy)
        }

        rangeSliderMainCardFiltersAttackRange.addOnChangeListener { _, _, _ ->
            val values = rangeSliderMainCardFiltersAttackRange.values
            textViewMainCardFiltersMinAttack.text = values[0].toInt().toString()
            textViewMainCardFiltersMaxAttack.text = values[1].toInt().toString()

            mainCardsFiltersViewModel.updateAttackRange(values[0].toInt(), values[1].toInt())
        }

        rangeSliderMainCardFiltersDefenseRange.addOnChangeListener { _, _, _ ->
            val values = rangeSliderMainCardFiltersDefenseRange.values
            textViewMainCardFiltersMinDefense.text = values[0].toInt().toString()
            textViewMainCardFiltersMaxDefense.text = values[1].toInt().toString()

            mainCardsFiltersViewModel.updateDefenseRange(values[0].toInt(), values[1].toInt())
        }

        radioGroupMainCardFiltersOrdination.setOnCheckedChangeListener { _, checkedId ->
            val ordination = when(checkedId) {
                radioButtonMainCardFiltersAscending.id -> Ordination.ASCENDING
                else -> Ordination.DESCENDING
            }
            mainCardsFiltersViewModel.updateOrdination(ordination)
        }

        val cardTypeClickListener =
            OnCheckedChangeListener { buttonView, isChecked ->
                val type = when(buttonView) {
                    checkBoxMainCardFiltersEquip -> Type.EQUIP
                    checkBoxMainCardFiltersMagic -> Type.MAGIC
                    checkBoxMainCardFiltersMonster -> {
                        handleFiltersWithMonsters(isChecked)
                        Type.MONSTER
                    }
                    checkBoxMainCardFiltersRitual -> Type.RITUAL
                    else -> Type.TRAP
                }
                mainCardsFiltersViewModel.updateCardTypes(type, isChecked)
            }

        flexboxMainCardFiltersAllCardTypes.children.forEach {
            (it as CheckBox).setOnCheckedChangeListener(cardTypeClickListener)
        }

        val natureTypeClickListener =
            OnCheckedChangeListener { buttonView, isChecked ->
                val nature = mapCheckBoxToNature(buttonView)
                mainCardsFiltersViewModel.updateCardNature(nature, isChecked)
            }

        flexboxMainCardFiltersAllNatureTypes.children.forEach {
            (it as CheckBox).setOnCheckedChangeListener(natureTypeClickListener)
        }

        textViewMainCardFiltersCardNatureClearAll.setOnClickListener {
            flexboxMainCardFiltersAllNatureTypes.children.forEach {
                (it as CheckBox).isChecked = false
            }
        }

        imageButtonMainCardFiltersGoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        materialButtonMainCardFiltersSave.setOnClickListener {
            mainCardsViewModel.updateFilters(mainCardsFiltersViewModel.filters.value)
            findNavController().popBackStack()
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleListType(listType: ListType) {
        when(listType) {
            ListType.EXPENDED ->
                radioGroupMainCardFiltersListType.check(radioButtonMainCardFiltersExpanded.id)
            else ->
                radioGroupMainCardFiltersListType.check(radioButtonMainCardFiltersCompact.id)
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleCardTypes(cardTypes: List<Type>) {
        if(cardTypes.contains(Type.TRAP)) {
            checkBoxMainCardFiltersTrap.isChecked = true
        }
        if(cardTypes.contains(Type.RITUAL)) {
            checkBoxMainCardFiltersRitual.isChecked = true
        }
        if(cardTypes.contains(Type.MONSTER)) {
            checkBoxMainCardFiltersMonster.isChecked = true
        }
        if(cardTypes.contains(Type.MAGIC)) {
            checkBoxMainCardFiltersMagic.isChecked = true
        }
        if(cardTypes.contains(Type.EQUIP)) {
            checkBoxMainCardFiltersEquip.isChecked = true
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleOrderBy(orderBy: OrderBy) {
        when(orderBy) {
            OrderBy.NAME ->
                radioGroupMainCardFiltersOrderBy.check(radioButtonMainCardFiltersName.id)
            OrderBy.ATTACK ->
                radioGroupMainCardFiltersOrderBy.check(radioButtonMainCardFiltersAttack.id)
            else ->
                radioGroupMainCardFiltersOrderBy.check(radioButtonMainCardFiltersDefense.id)
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleOrdination(ordination: Ordination) {
        when(ordination) {
            Ordination.ASCENDING ->
                radioGroupMainCardFiltersOrdination.check(radioButtonMainCardFiltersAscending.id)
            Ordination.DESCENDING ->
                radioGroupMainCardFiltersOrdination.check(radioButtonMainCardFiltersDescending.id)
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleAttackRange(attackRange: Pair<Int, Int>) {
        textViewMainCardFiltersMinAttack.text = attackRange.first.toString()
        textViewMainCardFiltersMaxAttack.text = attackRange.second.toString()

        rangeSliderMainCardFiltersAttackRange.values =
            listOf(attackRange.first.toFloat(), attackRange.second.toFloat())
    }

    private fun FragmentMainCardsFiltersBinding.handleDefenseRange(defenseRange: Pair<Int, Int>) {
        textViewMainCardFiltersMinDefense.text = defenseRange.first.toString()
        textViewMainCardFiltersMaxDefense.text = defenseRange.second.toString()

        rangeSliderMainCardFiltersDefenseRange.values =
            listOf(defenseRange.first.toFloat(), defenseRange.second.toFloat())
    }

    private fun FragmentMainCardsFiltersBinding.handleCardNatures(cardNatures: List<Nature>) {
        flexboxMainCardFiltersAllNatureTypes.children.forEach { view ->
            val nature = mapCheckBoxToNature(view)
            (view as CheckBox).isChecked = cardNatures.contains(nature)
        }
    }

    private fun FragmentMainCardsFiltersBinding.handleFiltersWithMonsters(isChecked: Boolean) {
        groupMainCardFiltersMonsterFilters.isVisible = isChecked
        if(!isChecked) {
            radioGroupMainCardFiltersOrderBy.check(radioButtonMainCardFiltersName.id)
        }
    }

    private fun FragmentMainCardsFiltersBinding.mapCheckBoxToNature(view: View) = when(view) {
        checkBoxMainCardFiltersAqua -> Nature.AQUA
        checkBoxMainCardFiltersBeast -> Nature.BEAST
        checkBoxMainCardFiltersBeastWarrior -> Nature.BEAST_WARRIOR
        checkBoxMainCardFiltersDinosaur -> Nature.DINOSAUR
        checkBoxMainCardFiltersDragon -> Nature.DRAGON
        checkBoxMainCardFiltersFairy -> Nature.FAIRY
        checkBoxMainCardFiltersFiend -> Nature.FIEND
        checkBoxMainCardFiltersFish -> Nature.FISH
        checkBoxMainCardFiltersInsect -> Nature.INSECT
        checkBoxMainCardFiltersMachine -> Nature.MACHINE
        checkBoxMainCardFiltersPlant -> Nature.PLANT
        checkBoxMainCardFiltersPyro -> Nature.PYRO
        checkBoxMainCardFiltersReptile -> Nature.REPTILE
        checkBoxMainCardFiltersRock -> Nature.ROCK
        checkBoxMainCardFiltersSeaSerpent -> Nature.SEA_SERPENT
        checkBoxMainCardFiltersSpellCaster -> Nature.SPELL_CASTER
        checkBoxMainCardFiltersThunder -> Nature.THUNDER
        checkBoxMainCardFiltersWarrior -> Nature.WARRIOR
        checkBoxMainCardFiltersWingedBeast -> Nature.WINGED_BEAST
        checkBoxMainCardFiltersZombie -> Nature.ZOMBIE
        else -> Nature.NONE
    }
}