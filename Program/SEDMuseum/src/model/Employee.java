/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Annette
 */
public class Employee {

    private int nr;
    private String fname;
    private String lname;
    private ArrayList<String> tlfList;
    private String address;
    private String zipcode;
    private String city;
    private String username;
    private String passwrd;

//    Mangler at få lavet kode til tlfliste og city.
    public Employee(int nr, String fname, String lname, String address, String zipcode, String username, String passwrd) {
        this.nr = nr;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.zipcode = zipcode;
        this.username = username;
        this.passwrd = passwrd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public ArrayList<String> getTlfList() {
        return tlfList;
    }

    public void setTlfList(ArrayList<String> tlfList) {
        this.tlfList = tlfList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "nr: " + nr + ", fname: " + fname + ", lname: " + lname + ", tlfList: " + tlfList + ", address: " + address + ", zipcode: " + zipcode + ", city: " + city;
    }
}
