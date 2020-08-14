package com.renovavision.heroesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, HeroActivity::class.java)

        goblinBtn.setOnClickListener {
            intent.putExtra(MODEL_NAME, goblin)

            startActivity(intent)
        }

        zombieBtn.setOnClickListener {
            intent.putExtra(MODEL_NAME, zombie)

            startActivity(intent)
        }

        fighterBtn.setOnClickListener {
            intent.putExtra(MODEL_NAME, fighter)

            startActivity(intent)
        }

        dancerBtn.setOnClickListener {
            intent.putExtra(MODEL_NAME, dancer)

            startActivity(intent)
        }
    }
}