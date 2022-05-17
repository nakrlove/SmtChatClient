package com.smt.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.smt.chat.model.Message
import kotlinx.android.extensions.LayoutContainer
import java.util.*

class ExtensionDataAdapter(
    val items: ArrayList<Message>
) : RecyclerView.Adapter<ExtensionDataAdapter.ExtensionViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtensionViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_message,
            parent,
            false
        )
//         ItemMessageBinding.inflate(parent.layoutInflater)

//        val view = DataBindingUtil.setContentView<ItemMessageBinding>(parent.context as Activity,R.layout.item_message)

        return ExtensionViewHolder( view)

    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            bind(item)
            itemView.tag = item
        }

//        holder.bind(items[position])
    }


    //    inner class ExtensionViewHolder(val binding: ItemMessageSendBinding, itemSelect: (Message) -> Unit)
//        : RecyclerView.ViewHolder(binding.root), LayoutContainer {
//    fun bind(msg: Message) {  // ⑤ 데이터를 연결
//            containerView.
//
//                .apply {
//                message = msg
//            }

    // ③ 이너 클래스로 사용자 뷰홀더 클래스를 지정
    inner class ExtensionViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(msg: Message) {  // ⑤ 데이터를 연결

//            tvmessage.text = ""
//            if (pet.photo != "") {  // 애완동물 이미지
//                val resourceId = context.resources.getIdentifier(
//                    pet.photo,
//                    "drawable",
//                    context.packageName
//                )
//                img_pet?.setImageResource(resourceId)
//            } else {  // 없으면 기본 아이콘으로
//                img_pet?.setImageResource(R.mipmap.ic_launcher)
//            }
//            // findViewById 없이 리소스명 사용
//            tv_breed.text = pet.breed
//            tv_age.text = pet.age
//            tv_gender.text = pet.gender


            //  ④ 항목이 클릭되면 itemSelect 람다식 함수가 처리
//            itemView.setOnClickListener() { itemSelect(msg) }

        }
    }
}
