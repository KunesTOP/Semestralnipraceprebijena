package com.example.myapplication.View



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ScoresAdapter
import com.example.myapplication.model.ScoreEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class StatisticsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics) // Nahraďte správným layoutem

        recyclerView = findViewById(R.id.recyclerViewStatistics)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val scores = loadScores()
        adapter = ScoresAdapter(scores)
        recyclerView.adapter = adapter
    }

    private fun loadScores(): List<ScoreEntry> {
        val file = File(filesDir, "score.json")
        if (!file.exists()) return emptyList()

        val type = object : TypeToken<List<ScoreEntry>>() {}.type
        return Gson().fromJson(file.readText(), type)
    }
}
