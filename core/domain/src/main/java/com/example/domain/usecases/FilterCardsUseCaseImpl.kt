package com.example.domain.usecases

import com.example.domain.model.Card
import com.example.domain.model.CardListFilters
import com.example.domain.model.OrderBy
import com.example.domain.model.Ordination
import com.example.domain.model.Type

internal class FilterCardsUseCaseImpl : FilterCardsUseCase {

    override fun invoke(cards: List<Card>, filters: CardListFilters): List<Card> {
        var filteredCards =
            cards.filter { card ->
                filters.monsterIsInAttackAndDefenseRangesAndHasSelectedNature(card) &&
                filters.cardTypes.contains(card.type)
            }

        filteredCards = when(filters.orderBy) {
            OrderBy.NAME -> filteredCards.sortedBy { it.name }
            OrderBy.ATTACK -> filteredCards.sortedBy { it.attack }
            OrderBy.DEFENSE -> filteredCards.sortedBy { it.defense }
        }

        return when(filters.ordination) {
            Ordination.ASCENDING -> filteredCards
            Ordination.DESCENDING -> filteredCards.reversed()
        }
    }

    private fun CardListFilters.monsterIsInAttackAndDefenseRangesAndHasSelectedNature(card: Card) =
        if(card.type == Type.MONSTER) {
            card.attack in (attackRange.first..attackRange.second) &&
            card.defense in (defenseRange.first..defenseRange.second)  &&
            cardNatures.contains(card.nature)
        } else {
            true
        }
}