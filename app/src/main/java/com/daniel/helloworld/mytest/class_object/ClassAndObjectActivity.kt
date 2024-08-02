package com.daniel.helloworld.mytest.class_object

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityClassAndObjectBinding
import com.daniel.helloworld.mytest.class_object.person.Person

class ClassAndObjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassAndObjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_class_and_object)
//        setContentView(R.layout.activity_class_and_object)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.user = Person("Bella Antrifosa", "8878654125437", "15-Jan-2001")
    }
}