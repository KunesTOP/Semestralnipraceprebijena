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

    lateinit var iv_card1: ImageView
    lateinit var iv_card2: ImageView
    lateinit var iv_card3: ImageView
    lateinit var iv_card4: ImageView
    lateinit var tv_player1: TextView
    lateinit var tv_player2: TextView
    lateinit var tv_war: TextView
    lateinit var b_deal: Button

    lateinit var random: Random
    var playerCards = IntArray(3)
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uroven2)
        val b_end = findViewById<Button>(R.id.b_end)
        b_end.setOnClickListener {
            showScoreAndSave()
        }
        random = Random
        var selectedPlayerCard: Int? = null
        iv_card1 = findViewById(R.id.iv_card1)
        iv_card2 = findViewById(R.id.iv_card2)
        iv_card3 = findViewById(R.id.iv_card3)
        iv_card4 = findViewById(R.id.iv_card4)
        tv_player1 = findViewById(R.id.tv_player1)
        tv_player2 = findViewById(R.id.tv_player2)
        tv_war = findViewById(R.id.tv_war)
        b_deal = findViewById(R.id.b_deal)

        val card1 = random.nextInt(cardsArray.size)

        val card3 = random.nextInt(cardsArray.size) // Přidáno
        val card4 = random.nextInt(cardsArray.size)

        setCardImage(card1, iv_card1)

        setCardImage(card3, iv_card3)
        setCardImage(card4, iv_card4)
        iv_card1.setOnClickListener {
            val card2 = random.nextInt(cardsArray.size)
            setCardImage(card2, iv_card2)

            if (card1 > card2) {
                player1++
                tv_player1.text = "Gambler  1: $player1"
            } else if (card1 < card2) {
                player2++
                tv_player2.text = "Player 2: $player2"
            } else {
                tv_war.visibility = View.VISIBLE
            }
            iv_card1.visibility = View.INVISIBLE

        }
        iv_card3.setOnClickListener {
            val card2 = random.nextInt(cardsArray.size)
            setCardImage(card2, iv_card2)

            if (card3 > card2) {
                player1++
                tv_player1.text = "Gambler  1: $player1"
            } else if (card3 < card2) {
                player2++
                tv_player2.text = "Player 2: $player2"
            } else {
                tv_war.visibility = View.VISIBLE
            }
            iv_card3.visibility = View.INVISIBLE

        }
        iv_card4.setOnClickListener {
            val card2 = random.nextInt(cardsArray.size)
            setCardImage(card2, iv_card2)

            if (card4 > card2) {
                player1++
                tv_player1.text = "Gambler 1: $player1"
            } else if (card4 < card2) {
                player2++
                tv_player2.text = "Player 2: $player2"
            } else {
                tv_war.visibility = View.VISIBLE
            }
            iv_card4.visibility = View.INVISIBLE

        }
        b_deal.setOnClickListener {
            val card2 = random.nextInt(cardsArray.size)
            setCardImage(card2, iv_card2)
            val card1 = random.nextInt(cardsArray.size)

            val card3 = random.nextInt(cardsArray.size) // Přidáno
            val card4 = random.nextInt(cardsArray.size)
            setCardImage(card1, iv_card1)

            setCardImage(card3, iv_card3)
            setCardImage(card4, iv_card4)
            iv_card4.visibility = View.VISIBLE
            iv_card3.visibility = View.VISIBLE
            iv_card1.visibility = View.VISIBLE

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

    private fun setCardImage(number: Int, image: ImageView) {
        image.setImageResource(cardsArray[number])
    }
}