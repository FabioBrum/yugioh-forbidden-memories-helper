package com.example.domain.usecases

import com.example.domain.model.Card
import com.example.domain.model.CardListFilters

interface FilterCardsUseCase {

    operator fun invoke(cards: List<Card>, filters: CardListFilters): List<Card>
}