package com.smt.chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smt.chat.databinding.ActivityMainBinding
import com.smt.chat.model.MainViewModel
import com.smt.chat.model.Message

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

//    private val message = arrayListOf<Message>()

    private val adapter: ExtensionDataAdapter by lazy {
        ExtensionDataAdapter()
    }

    private val viewmodel by viewModels<MainViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter =   adapter
        binding.button.setOnClickListener {
            viewmodel.addItem(Message(binding.sendText.text.toString(), "보냅니다"))
            viewmodel.itemList.observe(this,{ msg ->
                adapter.data = msg
                binding.sendText.text.clear()
//                binding.recyclerView.adapter?.notifyDataSetChanged()
            })

            viewmodel.itemList.value?.let{
                binding.recyclerView.scrollToPosition(it.size - 1)
                adapter?.notifyDataSetChanged()
            }

        }




    }
}