package com.smt.chat.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val list = arrayListOf<Message>()

    private val _itemList = MutableLiveData<ArrayList<Message>>()
    val itemList: LiveData<ArrayList<Message>> = _itemList

    init {
//        for(  i  in 0..1000000){
//                list.add(Message("${i}번째"," 보낼메시지 테스트"))
//        }
        _itemList.value = list
    }

    fun addItem(item: Message) {
        list.add(item)
        _itemList.value = list
    }

    fun removeItem(item: Message) {
        list.remove(item)
        _itemList.value = list
    }


    override fun onCleared() {
        super.onCleared()
    }

}