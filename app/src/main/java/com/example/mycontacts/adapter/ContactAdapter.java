package com.example.mycontacts.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontacts.R;
import com.example.mycontacts.databinding.ContactItemBinding;
import com.example.mycontacts.model.Contact;
import com.example.mycontacts.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {


    private List<Contact> contactList = new ArrayList<>();

    private MainActivity mainActivity;

    Context context;

    public ContactAdapter(List<Contact> contactList, MainActivity mainActivity, Context context) {
        this.contactList = contactList;
        this.mainActivity = mainActivity;
        this.context = context;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/*        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent,false);
        return new ContactViewHolder(view);*/

        ContactItemBinding contactItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_item, parent, false);
        return new ContactViewHolder(contactItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView")final int position) {
        final Contact contact = contactList.get(position);

        holder.contactItemBinding.setContact(contact);

/*      holder.firstName.setText(contact.getFirstName());
        holder.lastName.setText(contact.getLastName());
        holder.email.setText(contact.getEmail());
        holder.phone.setText(contact.getPhone());*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addAndEditContact(true, contact, position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{


/*      private TextView firstName;
        private TextView lastName;
        private TextView email;
        private TextView phone;*/

        private ContactItemBinding contactItemBinding;

        public ContactViewHolder(@NonNull ContactItemBinding contactItemBinding) {
            super(contactItemBinding.getRoot());

            this.contactItemBinding = contactItemBinding;
/*            firstName = itemView.findViewById(R.id.editTextFirstName);
            lastName = itemView.findViewById(R.id.editTextLastName);
            email = itemView.findViewById(R.id.editTextEmail);
            phone = itemView.findViewById(R.id.editTextPhone);*/

        }
    }

}
