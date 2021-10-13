package com.degresoftware.debtbook

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.degresoftware.debtbook.databinding.ActivityClientAccountBinding
import com.degresoftware.debtbook.databinding.ActivityMainBinding
import java.lang.Exception

class ClientAccountActivity : AppCompatActivity() {
    private lateinit var binding : ActivityClientAccountBinding
    private lateinit var database : SQLiteDatabase
    private lateinit var selectedId : String
    private lateinit var transactionList : ArrayList<Transaction>
    private lateinit var transactionAdapter : TransactionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = this.openOrCreateDatabase("DebtBook", Context.MODE_PRIVATE,null)
        val intent = intent
        selectedId = intent.getIntExtra("clientId",0).toString()
        val cursor = database.rawQuery("SELECT * FROM clients WHERE id = ?", arrayOf(selectedId.toString()))
        val clientNameIx = cursor.getColumnIndex("clientName")
        val clientPictureIx = cursor.getColumnIndex("clientPicture")
        while (cursor.moveToNext()) {
            binding.clientName.text = cursor.getString(clientNameIx)
            binding.clientTotal.text = 0.00.toString()
            val byteArray = cursor.getBlob(clientPictureIx)
            val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            binding.imageView.setImageBitmap(bitmap)
        }
        cursor.close()

        transactionList = ArrayList<Transaction>()
        transactionAdapter = TransactionAdapter(transactionList)
        binding.transactionRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionRecyclerView.adapter = transactionAdapter

        try {
            val cursor = database.rawQuery("SELECT * FROM transactions WHERE clientId = ?",arrayOf(selectedId.toString()))
            val idIx = cursor.getColumnIndex("id")
            val amountIx = cursor.getColumnIndex("amount")
            val typeIx = cursor.getColumnIndex("type")
            while (cursor.moveToNext()){
                val id = cursor.getInt(idIx)
                val amount = cursor.getString(amountIx).toDouble()
                val type = cursor.getString(typeIx)
                val transaction = Transaction(id,amount,type)
                transactionList.add(transaction)
            }
            transactionAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception){
            e.printStackTrace()
        }



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.client_account,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_debt){
            val intent = Intent(this,AddDebtPaymentActivity::class.java)
            intent.putExtra("info","debt")
            intent.putExtra("clientId",selectedId)
            startActivity(intent)
        } else if (item.itemId == R.id.add_payment){
            val intent = Intent(this,AddDebtPaymentActivity::class.java)
            intent.putExtra("info","payment")
            intent.putExtra("clientId",selectedId)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}