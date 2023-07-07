package com.example.network

import android.content.Context
import com.example.domain.model.Character
import com.example.domain.repositories.OnlineCharacterRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import java.net.URL
import java.util.UUID

class OnlineCharacterRepositoryImpl(
    private val context: Context
): OnlineCharacterRepository {
    override suspend fun getCharacterPageLinks(): List<String> =
        try {
            val htmlPage = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .get()

            getCharacterPageUrls(htmlPage)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    override suspend fun downloadCharacter(characterLink: String): Character? =
        try {
            val characterPage = Jsoup.connect("$BASE_URL$characterLink")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .get()

            val id = UUID.randomUUID().toString()
            val name = characterPage.selectFirst("caption.infobox-title")?.text().orEmpty()
            val tables = characterPage.select("div.tabbertab")

            if(tables.isNotEmpty()) {
                val powSADropOdds = getTableMappedValues(tables[0].child(1))
                val bcdDropOdds = getTableMappedValues(tables[1].child(1))
                val tecSADropOdds = getTableMappedValues(tables[2].child(1))

                val characterImageLink =
                    characterPage.selectFirst("img[alt=$name]")?.attr("src").orEmpty()

                downloadCharacterImage(id, characterImageLink)

                Character(
                    id = id,
                    name = name,
                    powSADropOdds = powSADropOdds,
                    tecSADropOdds = tecSADropOdds,
                    bcdDropOdds = bcdDropOdds
                )
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
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

    private fun downloadCharacterImage(characterId: String, characterImageLink: String) {
        try {
            val imageLink = URL(characterImageLink)

            val imageData = imageLink.readBytes()

            val imageFolder = File("${context.getExternalFilesDir(null)}/characterImages/")
            if (!imageFolder.exists()) {
                imageFolder.mkdirs()
            }

            val file = File(imageFolder, "$characterId.jpeg")
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