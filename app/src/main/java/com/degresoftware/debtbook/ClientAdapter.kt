package com.degresoftware.debtbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.degresoftware.debtbook.databinding.RecyclerRowBinding

class ClientAdapter(val clientList: ArrayList<Client>) : RecyclerView.Adapter<ClientAdapter.ClientHolder>() {
    class ClientHolder(val binding:RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ClientHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientHolder, position: Int) {
        holder.binding.recyclerViewTextViewClientName.text = clientList.get(position).name
        holder.binding.recyclerViewTextViewClientTotal.text = clientList.get(position).total.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ClientAccountActivity::class.java)
            intent.putExtra("clientId",clientList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return clientList.size
    }

}