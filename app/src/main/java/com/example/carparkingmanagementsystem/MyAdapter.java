package com.example.carparkingmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<VehicleInfo> list;

    public MyAdapter(Context context, ArrayList<VehicleInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VehicleInfo user = list.get(position);
        holder.name.setText(user.getName());
        holder.contactNumber.setText(user.getContactNumber());
        holder.vehicleNumber.setText(user.getVehicleNumber());
        holder.vehicleType.setText(user.getVehicleType());
        holder.parkingslot.setText(user.getParkingslot());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, contactNumber,vehicleNumber,vehicleType,parkingslot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvfirstName);
            contactNumber = itemView.findViewById(R.id.tvContact);
            vehicleNumber= itemView.findViewById(R.id.tvVehicleNumber);
            vehicleType=itemView.findViewById(R.id.tvVehicleType);
            parkingslot=itemView.findViewById(R.id.tvParkingLocation);
        }
    }
}
