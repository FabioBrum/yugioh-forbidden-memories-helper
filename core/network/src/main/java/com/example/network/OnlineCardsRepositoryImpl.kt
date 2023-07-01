package com.example.network

import android.util.Log
import com.example.domain.model.Card
import com.example.domain.model.Guardian
import com.example.domain.model.Nature
import com.example.domain.model.Type
import com.example.domain.repositories.OnlineCardsRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class OnlineCardsRepositoryImpl: OnlineCardsRepository {

    override suspend fun downloadCards(): List<Card> {
        return try {
            val htmlPage = Jsoup.connect(URL).get()
            val table = htmlPage.select("table").first()

            val rows = table?.select("tr").orEmpty().toMutableList()

            rows.removeFirst()

            val allCards = rows.map { row ->
                val elements = row.select("td").toMutableList()
                val id = elements.getNextRow()
                val name = elements.getNextRow()
                val type = elements.getNextRow()
                val nature = elements.getNextRow()
                val guardians = elements.getNextRow()
                val level = elements.getNextRow()
                val attack = elements.getNextRow()
                val defense = elements.getNextRow()
                val password = elements.getNextRow()
                val starCost = elements.getNextRow()

                Card(
                    id = id,
                    name = name,
                    type = mapType(type),
                    nature = mapNature(nature),
                    guardians = mapGuardians(guardians),
                    level = convertValueToInt(level),
                    attack = convertValueToInt(attack),
                    defense = convertValueToInt(defense),
                    password = password,
                    starCost = convertValueToInt(starCost)
                )
            }

            Log.i("First Card: ", allCards.first().toString())
            Log.i("Last Card: ", allCards.last().toString())
            allCards
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun MutableList<Element>.getNextRow(): String {
        val text = this.first().text()
        this.removeFirst()
        return text
    }

    private fun mapType(type: String) =
        when(type) {
            "Equip" -> Type.EQUIP
            "Magic" -> Type.MAGIC
            "Monster" -> Type.MONSTER
            "Ritual" -> Type.RITUAL
            "Trap" -> Type.RITUAL
            else -> Type.EQUIP
        }

    private fun mapNature(nature: String) =
        when(nature) {
            "Aqua" -> Nature.AQUA
            "Beast" -> Nature.BEAST
            "Beast-Warrior" -> Nature.BEAST_WARRIOR
            "Dinosaur" -> Nature.DINOSAUR
            "Dragon" -> Nature.DRAGON
            "Fairy" -> Nature.FAIRY
            "Fiend" -> Nature.FIEND
            "Fish" -> Nature.FISH
            "Insect" -> Nature.INSECT
            "Machine" -> Nature.MACHINE
            "Plant" -> Nature.PLANT
            "Pyro" -> Nature.PYRO
            "Reptile" -> Nature.REPTILE
            "Rock" -> Nature.ROCK
            "Sea Serpent" -> Nature.SEA_SERPENT
            "Spellcaster" -> Nature.SPELL_CASTER
            "Thunder" -> Nature.THUNDER
            "Warrior" -> Nature.WARRIOR
            "Winged Beast" -> Nature.WINGED_BEAST
            "Zombie" -> Nature.ZOMBIE
            else -> Nature.NONE
        }

    private fun mapGuardians(guardians: String): Pair<Guardian, Guardian> {
        val guardianPairStrings = guardians.split(", ")

        return Pair(mapGuardian(guardianPairStrings.first()), mapGuardian(guardianPairStrings.last()))
    }

    private fun mapGuardian(guardian: String) =
        when(guardian) {
            "Sun" -> Guardian.SUN
            "Moon" -> Guardian.MOON
            "Mercury" -> Guardian.MERCURY
            "Venus" -> Guardian.VENUS
            "Mars" -> Guardian.MARS
            "Jupiter" -> Guardian.JUPITER
            "Saturn" -> Guardian.SATURN
            "Uranus" -> Guardian.URANUS
            "Pluto" -> Guardian.PLUTO
            "Neptune" -> Guardian.NEPTUNE
            else -> Guardian.NONE
        }

    private fun convertValueToInt(value: String) =
        if(value.isNotBlank() && value.isNotEmpty()) {
            value.replace(",", "").toInt()
        } else {
            0
        }

    companion object {
        const val URL = "https://yugipedia.com/wiki/List_of_Yu-Gi-Oh!_Forbidden_Memories_cards"
    }
}