package com.example.domain

import com.example.domain.model.Card
import com.example.domain.model.Guardian
import com.example.domain.model.Nature
import com.example.domain.model.Type
import com.example.domain.repositories.OfflineCardRepository
import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCase
import com.example.domain.usecases.InitializeDatabaseIfNeededUseCaseImpl
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class InitializeDatabaseIfNeededUseCaseTest {

    private val offlineCardRepository = mockk<OfflineCardRepository>()
    private val onlineCardsRepository = mockk<OnlineCardsRepository>()
    private lateinit var initializeDatabaseIfNeededUseCase: InitializeDatabaseIfNeededUseCase

    @Before
    fun setup() {
        initializeDatabaseIfNeededUseCase = InitializeDatabaseIfNeededUseCaseImpl(
            offlineCardRepository, onlineCardsRepository
        )
    }

    @After
    fun teardown() {
        confirmVerified(offlineCardRepository, onlineCardsRepository)
    }

    @Test
    fun `when database already is populated, nothing should be done and return true`() {
        // Mock
        coEvery { offlineCardRepository.databaseHasCards() } returns true

        // Run
        val actual = runBlocking { initializeDatabaseIfNeededUseCase(this) }

        assertTrue(actual)
        coVerify {
            offlineCardRepository.databaseHasCards()
        }
    }

    @Test
    fun `when database already is not populated, downloaded cards should be stored in database and return true`() {
        // Mock
        val cardsWithImageLinks = Pair(listOf<Card>(), listOf<String>())

        coEvery { offlineCardRepository.databaseHasCards() } returns false
        coEvery { onlineCardsRepository.downloadCardsWithCardLinks() } returns cardsWithImageLinks
        coJustRun { onlineCardsRepository.downloadCardImage(any(), any()) }
        coEvery { offlineCardRepository.saveCards(cardsWithImageLinks.first) } returns true

        // Run
        val actual = runBlocking { initializeDatabaseIfNeededUseCase(this) }

        assertTrue(actual)
        coVerify {
            offlineCardRepository.databaseHasCards()
            onlineCardsRepository.downloadCardsWithCardLinks()
            offlineCardRepository.saveCards(cardsWithImageLinks.first)
        }
    }

    @Test
    fun `when database already is not populated, downloaded card images should be stored in device and return true`() {
        // Mock
        val cardsWithImageLinks = Pair(listOf(
            Card("1","",Type.TRAP,Nature.ZOMBIE,Pair(Guardian.NEPTUNE,Guardian.PLUTO),0,0,0,"",0),
            Card("2","",Type.TRAP,Nature.ZOMBIE,Pair(Guardian.NEPTUNE,Guardian.PLUTO),0,0,0,"",0),
        ), listOf("imageLink1", "imageLink2"))

        coEvery { offlineCardRepository.databaseHasCards() } returns false
        coEvery { onlineCardsRepository.downloadCardsWithCardLinks() } returns cardsWithImageLinks
        coJustRun { onlineCardsRepository.downloadCardImage(any(), any()) }
        coEvery { offlineCardRepository.saveCards(cardsWithImageLinks.first) } returns true

        // Run
        val actual = runBlocking { initializeDatabaseIfNeededUseCase(this) }

        assertTrue(actual)
        coVerify {
            offlineCardRepository.databaseHasCards()
            onlineCardsRepository.downloadCardsWithCardLinks()
            onlineCardsRepository.downloadCardImage("1", "imageLink1")
            onlineCardsRepository.downloadCardImage("2", "imageLink2")
            offlineCardRepository.saveCards(cardsWithImageLinks.first)
        }
    }

    @Test
    fun `when an error is thrown, it should return false`() {
        // Mock
        coEvery { offlineCardRepository.databaseHasCards() } returns false
        coEvery { onlineCardsRepository.downloadCardsWithCardLinks() } throws Exception()

        // Run
        val actual = runBlocking { initializeDatabaseIfNeededUseCase(this) }

        // Assert
        assertFalse(actual)
        coVerify {
            offlineCardRepository.databaseHasCards()
            onlineCardsRepository.downloadCardsWithCardLinks()
        }
    }
}