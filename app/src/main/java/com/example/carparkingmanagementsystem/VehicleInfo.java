package com.example.carparkingmanagementsystem;

public class VehicleInfo {
    private String Name;

    // string variable for storing
    // employee contact number
    private String ContactNumber;

    // string variable for storing
    // employee address.
    private String VehicleNumber;
    private String VehicleType;
    private String Parkingslot;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public VehicleInfo() {

    }

    // created getter and setter methods
    // for all our variables.

    public VehicleInfo(String Name,String ContactNumber,String VehicleNumber,String VehicleType,String ParkingSlot){
        this.Name=Name;
        this.ContactNumber=ContactNumber;
        this.VehicleNumber=VehicleNumber;
        this.VehicleType=VehicleType;
        this.Parkingslot=ParkingSlot;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }


    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public String getParkingslot() {
        return Parkingslot;
    }

    public void setVehicleType(String vehicleType) {
        this.VehicleType = vehicleType;
    }

    public void setParkingslot(String parkingslot) {
        this.Parkingslot = parkingslot;
    }
}
