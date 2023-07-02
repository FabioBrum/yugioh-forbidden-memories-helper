package com.example.network

import android.content.Context
import android.util.Log
import com.example.domain.model.Card
import com.example.domain.model.Character
import com.example.domain.model.Guardian
import com.example.domain.model.Nature
import com.example.domain.model.Type
import com.example.domain.repositories.OnlineCardsRepository
import com.example.domain.repositories.OnlineCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeout
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import java.net.URL
import java.util.UUID

class OnlineCharacterRepositoryImpl(
    private val context: Context
): OnlineCharacterRepository {

    override suspend fun downloadCharacters(): List<Character> =
        try {
            val htmlPage = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .get()

            val characterPagesUrls = getCharacterPageUrls(htmlPage)

            val characters = characterPagesUrls.mapNotNull { characterPageUrl ->
                val characterPage = Jsoup.connect("$BASE_URL$characterPageUrl")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .get()

                val id = UUID.randomUUID().toString()
                val name = characterPage.getElementById("firstHeading")?.text().orEmpty()
                val tables = characterPage.select("div.tabbertab")

                if(tables.isNotEmpty()) {
                    val powSADropOdds = getTableMappedValues(tables[0].child(1))
                    val bcdDropOdds = getTableMappedValues(tables[1].child(1))
                    val tecSADropOdds = getTableMappedValues(tables[2].child(1))

                    Character(
                        id = id,
                        name = name,
                        powSADropOdds = powSADropOdds,
                        tecSADropOdds = tecSADropOdds,
                        bcdDropOdds = bcdDropOdds
                    )
                } else {
                    Log.i("Characters testing", "Character não salvo: $name")
                    null
                }
            }

            Log.i("Characters testing", characters.first().toString())
            Log.i("Characters testing", characters.last().toString())
            Log.i("Characters testing", "Quantidade total: ${characters.size}")

            characters
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    private fun getCharacterPageUrls(htmlPage: Document) =
        htmlPage.getElementsByClass("mw-category-group").map { categoryElement ->
            val listOfCharacters = categoryElement.children().last()
            listOfCharacters?.children()?.map { character ->
                character.children().first()?.attr("href").orEmpty()
            }.orEmpty()
        }.flatten()

    private fun getTableMappedValues(table: Element): Map<String, Double> {
        val rows = table.select("tr").orEmpty().toMutableList()
        rows.removeFirst()

        return rows.associate { row ->
            val elements = row.select("td").toMutableList()
            val cardId = elements.getNextRow()
            elements.getNextRow()
            elements.getNextRow()
            elements.getNextRow()
            elements.getNextRow()
            elements.getNextRow()
            elements.getNextRow()
            val odd = elements.getNextRow().toDouble()

            cardId to odd
        }
    }

    private fun downloadCharacterImage(cardId: String, cardLink: String) {
        try {
            val htmlPageWithCardImage =
                Jsoup.connect("$BASE_URL$cardLink")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .get()

            val imageLink = URL(
                htmlPageWithCardImage.getElementsByClass("cardtable-main_image-wrapper")
                    .first()
                    ?.child(0)
                    ?.child(0)
                    ?.attr("src")
            )

            val imageData = imageLink.readBytes()

            val imageFolder = File("${context.getExternalFilesDir(null)}/cardImages/")
            if (!imageFolder.exists()) {
                imageFolder.mkdirs()
            }

            val file = File(imageFolder, "$cardId.jpeg")
            file.writeBytes(imageData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun MutableList<Element>.getNextRow(): String {
        val text = this.first().text()
        this.removeFirst()
        return text
    }

    companion object {
        const val BASE_URL = "https://yugipedia.com"
        const val URL = "$BASE_URL/wiki/Category:Yu-Gi-Oh!_Forbidden_Memories_characters"
    }
}