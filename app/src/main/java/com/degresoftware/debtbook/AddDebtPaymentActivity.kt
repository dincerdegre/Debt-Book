package com.degresoftware.debtbook

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.degresoftware.debtbook.databinding.ActivityAddDebtPaymentBinding
import com.degresoftware.debtbook.databinding.ActivityClientAccountBinding
import java.lang.Exception

class AddDebtPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddDebtPaymentBinding
    private lateinit var database : SQLiteDatabase
    private lateinit var selectedId : String
    private lateinit var recordType : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDebtPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = this.openOrCreateDatabase("DebtBook", Context.MODE_PRIVATE,null)
        val intent = intent
        val info = intent.getStringExtra("info")
        selectedId = intent.getStringExtra("clientId").toString()

        if (info.equals("debt")) {
        recordType = "debt"   // Red

        } else if (info.equals("payment")) {
        recordType = "payment" // Green

        }

    }


    fun save (view: View){
        val clientId = selectedId.toString()
        val amount = binding.editTextNumberDecimal.text.toString().toDouble()
        val type = recordType
        val info = binding.editTextInfo.text.toString()

        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS transactions (id INTEGER PRIMARY KEY, clientId VARCHAR, amount DECIMAL,type VARCHAR,info VARCHAR)")
            val sqlString = "INSERT INTO transactions (clientId,amount,type,info) VALUES (?,?,?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,clientId)
            statement.bindDouble(2,amount)
            statement.bindString(3,type)
            statement.bindString(4,info)
            statement.execute()
        } catch (e: Exception){
            e.printStackTrace()
        }
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("clientId",clientId)
        startActivity(intent)
        finish()
    }
}