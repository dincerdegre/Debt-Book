package com.degresoftware.debtbook

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.degresoftware.debtbook.databinding.ActivityAddDebtPaymentBinding
import com.degresoftware.debtbook.databinding.ActivityClientAccountBinding

class AddDebtPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddDebtPaymentBinding
    private lateinit var database : SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDebtPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = this.openOrCreateDatabase("DebtBook", Context.MODE_PRIVATE,null)
        val intent = intent
        val info = intent.getStringExtra("info")
        val selectedId = intent.getStringExtra("clientId")
        binding.textView.text = selectedId
        if (info.equals("debt")) {
        val recordType = "debt"   // Red
        } else if (info.equals("payment")) {
            val recordType = "payment" // Green
        }

    }
}