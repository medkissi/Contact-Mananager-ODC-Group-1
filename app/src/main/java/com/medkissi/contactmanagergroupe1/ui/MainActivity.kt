package com.medkissi.contactmanagergroupe1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultRegistry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.medkissi.contactmanagergroupe1.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                val intent = Intent(this, AddEditActivity::class.java)
                startActivity(intent)
            }

    }
}