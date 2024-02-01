package com.example.myapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Viewmodel.Uroven1
import com.example.myapplication.Viewmodel.Uroven2
import com.example.myapplication.adapter.LevelAdapter
import com.example.myapplication.model.Level

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var levelAdapter: LevelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu) // Název layoutu obsahujícího RecyclerView

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val levels = listOf(
            Level("Úroveň 1", Uroven1::class.java),
            Level("Úroveň 2", Uroven2::class.java)

        )
        val btnShowStatistics = findViewById<Button>(R.id.btn_show_statistics)
        btnShowStatistics.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }


        levelAdapter = LevelAdapter(levels, this)
        recyclerView.adapter = levelAdapter
    }
}







