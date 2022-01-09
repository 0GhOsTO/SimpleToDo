package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import org.apache.commons.io.FileUtils;
import java.io.IOException
import java.nio.charset.Charset

//kotlin file is used for user interactions and logics. xml files are for layouts
class MainActivity : AppCompatActivity() {

    //Wasn't able to change since it was value. In order to change, we change to variable
    var listOfTasks = mutableListOf<String>();
    //initializing it later and creating over here in order to make adapter accessible outside of method
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our dat set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }


        //1. detect user clicking (find specific view with id)
        findViewById<Button>(R.id.button).setOnClickListener {
            //things happen if user clicking(check if the code is working or not)
            Log.i("Andrew", "User clicked on button")
        }

        loadItems()

        //this will be work by user.
        //listOfTasks.add("Do laundary")
        //listOfTasks.add("Go for a walk")

        // Looking up recycler view in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //since we are calling the same thing multiple times, we shorten it by calling onece.
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Setting up the button and input field, so the user can enter a task and add it to the list
        // grab the button and set up the onclickListener.
        findViewById<Button>(R.id.button).setOnClickListener {
        // 1. Grab the text that user has inputted; like a scanner @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
        // 2. Add the string to our list of tasks lIstOfTasks
            listOfTasks.add(userInputtedTask)

        // Notify the adapter that our data has been updated
        // Telling to the adapter that underlying data set has changed
            adapter.notifyItemInserted(listOfTasks.size-1)

        // 3. Reset the textfield after added.
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save data that user inputted by writing and reading from the file

    // method to get the file we need
    fun getDataFile() : File {

        // Every line will represent a specific task
        return File(filesDir, "listOfTask.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    // Save items by writing them into our data files
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}