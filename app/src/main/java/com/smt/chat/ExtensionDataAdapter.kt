package com.smt.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smt.chat.databinding.ItemMessageBinding

import com.smt.chat.model.Message
import java.util.*

class ExtensionDataAdapter: RecyclerView.Adapter<ExtensionDataAdapter.ExtensionViewHolder<ItemMessageBinding>>() {

    var data = ArrayList<Message>()
    set(value){
        field = value
//        notifyDataSetChanged()
    }

    fun setData(message: Message){
        data.add(message)
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return data.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtensionViewHolder<ItemMessageBinding> {
        val view = DataBindingUtil.inflate<ItemMessageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_message,
            parent,
            false)
        return ExtensionViewHolder( view)

    }

    override fun onBindViewHolder(holder: ExtensionViewHolder<ItemMessageBinding>, position: Int) {
        val item = data[position]
        holder.apply {
            bind(item)
//            itemView.tag = item
        }

    }


    // ③ 이너 클래스로 사용자 뷰홀더 클래스를 지정
//    inner class ExtensionViewHolder(override val containerView: View)
//        : RecyclerView.ViewHolder(containerView), LayoutContainer {
    inner class ExtensionViewHolder<T : ItemMessageBinding>(val binding: T)
            : RecyclerView.ViewHolder(binding.root) {
//    inner class ExtensionViewHolder( val containerView: View)
//        : RecyclerView.ViewHolder(containerView) {

        fun bind(msg: Message) {  // ⑤ 데이터를 연결
            binding.data = msg

            //  ④ 항목이 클릭되면 itemSelect 람다식 함수가 처리
//            itemView.setOnClickListener() { itemSelect(msg) }

        }
    }
}
