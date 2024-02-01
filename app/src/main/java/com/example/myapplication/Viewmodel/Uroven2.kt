package com.example.myapplication.Viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class Uroven2 : AppCompatActivity() {

    private val playerCardsIndices = mutableMapOf<ImageView, Int>()
    lateinit var iv_card1: ImageView
    lateinit var iv_card2: ImageView // Karta AI
    lateinit var iv_card3: ImageView
    lateinit var iv_card4: ImageView

    lateinit var tv_player1: TextView
    lateinit var tv_player2: TextView
    lateinit var tv_war: TextView
    lateinit var b_deal: Button
    lateinit var random: Random
    var i = 0
    var player1 = 0
    var player2 = 0
    var cardsArray = intArrayOf(
        R.drawable.hearts2,
        R.drawable.hearts3,
        R.drawable.hearts4,
        R.drawable.hearts5,
        R.drawable.hearts6,
        R.drawable.hearts7,
        R.drawable.hearts8,
        R.drawable.hearts9,
        R.drawable.hearts10,
        R.drawable.hearts12,
        R.drawable.hearts13,
        R.drawable.hearts14,
        R.drawable.hearts15,
                R.drawable.diamond
    )

    var aiCardValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uroven2)

        random = Random
        iv_card1 = findViewById(R.id.iv_card1)
        iv_card2 = findViewById(R.id.iv_card2)
        iv_card3 = findViewById(R.id.iv_card3)
        iv_card4 = findViewById(R.id.iv_card4)

        tv_player1 = findViewById(R.id.tv_player1)
        tv_player2 = findViewById(R.id.tv_player2)
        tv_war = findViewById(R.id.tv_war)
        b_deal = findViewById(R.id.b_deal)

        setupGame()
        setupCardClickListener(iv_card1, iv_card2)
        setupCardClickListener(iv_card3, iv_card2)
        setupCardClickListener(iv_card4, iv_card2)

        val b_end = findViewById<Button>(R.id.b_end)
        b_end.setOnClickListener {
            showScoreAndSave()
            finish()
        }
        b_deal.setOnClickListener {
            setupGame()
        }
    }
    private fun showScoreAndSave() {
        // Zobrazení skóre
        val scoreMessage = "Skóre Hráč 1: $player1, Hráč 2: $player2"
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()

        // Připravíme nový záznam
        val newScoreEntry = mapOf(
            "date" to getCurrentDate(),
            "player1" to player1,
            "player2" to player2
        )

        // Načteme stávající záznamy
        val scoresList = readScoresFromFile()

        // Přidáme nový záznam
        scoresList.add(newScoreEntry)

        // Uložíme upravený seznam zpět do souboru
        saveJsonToFile(scoresList)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun readScoresFromFile(): MutableList<Map<String, Any>> {
        val file = File(filesDir, "score.json")
        if (!file.exists()) {
            return mutableListOf()
        }
        val json = file.readText()
        val type = object : TypeToken<MutableList<Map<String, Any>>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun saveJsonToFile(jsonData: List<Map<String, Any>>) {
        val json = Gson().toJson(jsonData)
        val file = File(filesDir, "score.json")
        file.writeText(json)
    }
    private fun setupCardClickListener(playerCardView: ImageView, aiCardView: ImageView) {
        playerCardView.setOnClickListener {
            val playerCardIndex = playerCardsIndices[playerCardView] ?: return@setOnClickListener
            val playerCardValue = cardValues[cardsArray[playerCardIndex]]

            // Použijte aktuální hodnotu karty AI pro porovnání
            val currentAiCardValue = cardValues[cardsArray[aiCardValue]]

            if (playerCardValue != null) {
                if (currentAiCardValue != null) {
                    compareCards(playerCardValue, currentAiCardValue)
                }
            }

            // Generujte novou hodnotu pro AI kartu
            aiCardValue = random.nextInt(cardsArray.size)
            val newAiCardValue = cardValues[cardsArray[aiCardValue]]
            setCardImage(aiCardValue, aiCardView)

            playerCardView.visibility = View.INVISIBLE // Skrytí kliknuté karty hráče
        }
    }

    val cardValues = mapOf(
        R.drawable.hearts2 to 2,
        R.drawable.hearts3 to 3,
        R.drawable.hearts4 to 4,
        R.drawable.hearts5 to 5,
        R.drawable.hearts6 to 6,
        R.drawable.hearts7 to 7,
        R.drawable.hearts8 to 8,
        R.drawable.hearts9 to 9,
        R.drawable.hearts10 to 10,
        R.drawable.diamond to  15,
        R.drawable.hearts12 to 12,
        R.drawable.hearts13 to 13,
        R.drawable.hearts14 to 14,

        R.drawable.hearts15 to 15
    )
    private fun compareCards(playerCardValue: Int, aiCardValue: Int)  {


        if (playerCardValue!! > aiCardValue!!) {
            if (i!=0)
            {
                player1 = player1 + i
                i = 0

            }
            player1++
            tv_player1.text = "Hrac 1: $player1"
        } else if (playerCardValue < aiCardValue) {
            if (i!=0)
            {
                player2 = player2 + i
                i = 0

            }
            player2++
            tv_player2.text = "Hrac 2: $player2"
        } else {
            tv_war.visibility = View.VISIBLE
            i++
            // Zde chybí logika pro remízu.
        }
    }


    private fun setupGame() {
        val playerCardViews = listOf(iv_card1, iv_card3, iv_card4)
        playerCardViews.forEach { cardView ->
            val cardIndex = random.nextInt(cardsArray.size)
            setCardImage(cardIndex, cardView)
            playerCardsIndices[cardView] = cardIndex // Uložení indexu karty hráče
        }

        // Nastavení karet AI


        // Nastavení posluchačů kliknutí
        setupCardClickListener(iv_card1, iv_card2)
        setupCardClickListener(iv_card3, iv_card2)
        setupCardClickListener(iv_card4, iv_card2)

        iv_card4.visibility = View.VISIBLE
        iv_card3.visibility = View.VISIBLE
        iv_card1.visibility = View.VISIBLE

        tv_war.visibility = View.INVISIBLE

        // ... a tak dále pro ostatní karty ...
    }

    private fun setCardImage(number: Int, image: ImageView) {
        image.setImageResource(cardsArray[number])
    }
}