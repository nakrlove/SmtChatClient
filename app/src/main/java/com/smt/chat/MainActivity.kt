package com.smt.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.smt.chat.databinding.ActivityMainBinding
import com.smt.chat.model.Message

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val message = arrayListOf<Message>()

//    private val adapter: ExtensionDataAdapter by lazy {
//        ExtensionDataAdapter(message,this){
//            println(" message ")
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView
//        binding.recyclerView.adapter = adapter
        binding.recyclerView.adapter =   ExtensionDataAdapter(message)
//        {
//            println(" message ")
//        }
        binding.button.setOnClickListener {
            val msg = Message("첫번째", "보냅니다")
            message.add(msg)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }



//        setContentView(R.layout.activity_main)
    }
}