package com.example.myapplication.Viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Uroven1 : AppCompatActivity() {

    lateinit var iv_card1: ImageView
    lateinit var iv_card2: ImageView // Karta AI
    lateinit var iv_card3: ImageView
    lateinit var iv_card4: ImageView
    lateinit var iv_card5: ImageView
    lateinit var iv_card6: ImageView
    lateinit var tv_player1: TextView
    lateinit var tv_player2: TextView
    lateinit var tv_war: TextView
    lateinit var b_deal: Button
    lateinit var random: Random
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
        R.drawable.hearts15
    )

    var aiCardValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uroven1)

        random = Random
        iv_card1 = findViewById(R.id.iv_card1)
        iv_card2 = findViewById(R.id.iv_card2)
        iv_card3 = findViewById(R.id.iv_card3)
        iv_card4 = findViewById(R.id.iv_card4)
        iv_card5 = findViewById(R.id.iv_card5)
        iv_card6 = findViewById(R.id.iv_card6)
        tv_player1 = findViewById(R.id.tv_player1)
        tv_player2 = findViewById(R.id.tv_player2)
        tv_war = findViewById(R.id.tv_war)
        b_deal = findViewById(R.id.b_deal)

        setupGame()
        val b_end = findViewById<Button>(R.id.b_end)
        b_end.setOnClickListener {
            showScoreAndSave()
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
    private fun setupCardClickListener(cardView: ImageView) {
        cardView.setOnClickListener {
            val playerCardIndex = random.nextInt(cardsArray.size)
            val playerCardValue = cardValues[cardsArray[playerCardIndex]]
            setCardImage(playerCardIndex, cardView)

            // Vygenerovat novou kartu pro AI
            aiCardValue = random.nextInt(cardsArray.size)
            setCardImage(aiCardValue, iv_card2)

            compareCards(playerCardIndex, aiCardValue)
            cardView.visibility = View.INVISIBLE

            // Vypsat hodnoty karet do konzole pro debugování
            Log.d("GameDebug", "Player Card Value: $playerCardValue, AI Card Value: ${cardValues[cardsArray[aiCardValue]]}")
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

        R.drawable.hearts12 to 12,
        R.drawable.hearts13 to 13,
        R.drawable.hearts14 to 14,

        R.drawable.hearts15 to 15
    )
    private fun compareCards(playerCardIndex: Int, aiCardIndex: Int) {
        val playerCardValue = cardValues[cardsArray[playerCardIndex]]
        val aiCardValue = cardValues[cardsArray[aiCardIndex]]

        if (playerCardValue!! > aiCardValue!!) {
            player1++
            tv_player1.text = "Hrac 1: $player1"

        } else if (playerCardValue < aiCardValue) {

        } else {
            tv_war.visibility = View.VISIBLE
        }
    }

    private fun setupGame() {
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card2)
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card1)
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card3)
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card4)
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card5)
        aiCardValue = random.nextInt(cardsArray.size)
        setCardImage(aiCardValue, iv_card6)


        setupCardClickListener(iv_card1)
        setupCardClickListener(iv_card3)
        setupCardClickListener(iv_card4)
        setupCardClickListener(iv_card5)
        setupCardClickListener(iv_card6)

        iv_card1.visibility = View.VISIBLE
        iv_card3.visibility = View.VISIBLE
        iv_card4.visibility = View.VISIBLE
        iv_card5.visibility = View.VISIBLE
        iv_card6.visibility = View.VISIBLE
        tv_war.visibility = View.GONE
    }

    private fun setCardImage(cardIndex: Int, imageView: ImageView) {
        imageView.setImageResource(cardsArray[cardIndex])
    }
}
