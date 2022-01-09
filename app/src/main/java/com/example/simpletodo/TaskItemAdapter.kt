package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * delievering the data from recyclerview and display on the screen
 */
//TaskItemAdapter only holds the reference of an item. so if we wanna modify the list, we have interact with main activity
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){
    //: is the extending.
    //Reason for red lining is because there is certain methods that are not implemented or overriden

    interface OnLongClickListener{
        //define what happen when specific item is LongClicked
        fun onItemLongClicked(position: Int) //which specific integer possition is clicked

    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getting an item from the liste of item.
        val item = listOfItems.get(position)
        //setting up how text will look like whatever the item is
        holder.textView.text = item;
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in our layout view
        // Stores the list of items.
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)
            //itemView represents the one entire row of the list
            itemView.setOnLongClickListener {
                //setting up the onclickListener // detects clicking on it
                //it has to return true or false in order to check if the android handle this or not.
                // Before* Log.i("Andrew", "Long Click Listener working" + adapterPosition)
                //adapter positions tells us what it clicked on. The position of it clicked.
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}