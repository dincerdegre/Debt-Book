package com.degresoftware.debtbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.degresoftware.debtbook.databinding.RecyclerTransactionRowBinding

class TransactionAdapter(val transactionList : ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {
    class TransactionHolder(val binding: RecyclerTransactionRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val binding = RecyclerTransactionRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransactionHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.binding.recyclerViewTextViewTransactionAmount.text = transactionList.get(position).amount.toString()
        holder.binding.recyclerViewTextViewTransactionInfo.text = transactionList.get(position).type.toString()
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

}