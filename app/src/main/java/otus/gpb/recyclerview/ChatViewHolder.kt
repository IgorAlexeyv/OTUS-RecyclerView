package otus.gpb.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class ChatViewHolder(
    private val view: View,
    val listener: Listener
) : RecyclerView.ViewHolder(view) {

    private var root = view.findViewById<ViewGroup>(R.id.chat_item_conteiner)
    private var avatar = view.findViewById<ImageView>(R.id.avatar)
    private var name = view.findViewById<TextView>(R.id.name)
    private var message = view.findViewById<TextView>(R.id.message)
    private var time = view.findViewById<TextView>(R.id.time)
    private var sound_on_off = view.findViewById<ImageView>(R.id.sound_on_off)

    lateinit var item :Chat

    fun bind(item: Chat){
        root.setOnClickListener { listener.onItemClick(item) }
        item.avatar?.let{ avatar.setImageDrawable(it) }
        name.text = item.name
        message.text = item.message
        time.text = item.time
        if( item.sound == Chat.Sound.ON ){
            sound_on_off.setImageDrawable(ResourcesCompat.getDrawable(view.resources, R.drawable.sound_on,null))
        } else {
            sound_on_off.setImageDrawable(ResourcesCompat.getDrawable(view.resources, R.drawable.sound_off,null))
        }
        sound_on_off.setOnClickListener { listener.onSoundClick(item) }
        this.item = item
    }
}