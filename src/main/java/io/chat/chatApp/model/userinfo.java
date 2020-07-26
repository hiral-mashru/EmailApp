package io.chat.chatApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class userinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UID;

    private String Name;
    private String EmailID;
    private String Password;

    public userinfo() {
    }

    public userinfo(String name, String emailID, String password) {
        Name = name;
        EmailID = emailID;
        Password = password;
    }

    public userinfo(int UID, String name, String emailID, String password) {
        this.UID = UID;
        Name = name;
        EmailID = emailID;
        Password = password;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "userinfo{"+
                "UID=" + UID +
                ", Name=" + Name + '\'' +
                ", EmailID=" + EmailID + '\'' +
                ", Password=" + Password + '\'' +
                '}';
    }
}
