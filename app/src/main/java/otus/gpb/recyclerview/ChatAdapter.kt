package otus.gpb.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var list: MutableList<Chat>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as ChatViewHolder).bind(item)

        if(position == list.size - 1) {
            listener.onLoadMoreItem()
        }
    }

    fun setItems(items: MutableList<Chat>) {
        list = items
        notifyItemInserted(0)
    }
}