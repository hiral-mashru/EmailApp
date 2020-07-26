package io.chat.chatApp.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
//@Table(name = "chatdata")
public class chatdata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CID;
    private String Fromm;
    private String Tooo;
//    @Column(name="Chatt")
    private String Chatt;

    @Column(name="Date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
    private Timestamp Date;

    public chatdata() {
    }

    public chatdata(String fromm, String tooo, String chatt) {
        Fromm = fromm;
        Tooo = tooo;
        Chatt = chatt;
    }

    public chatdata(int CID, String fromm, String tooo, String chatt, Timestamp date) {
        this.CID = CID;
        Fromm = fromm;
        Tooo = tooo;
        Chatt = chatt;
        Date = date;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getFromm() {
        return Fromm;
    }

    public void setFromm(String fromm) {
        Fromm = fromm;
    }

    public String getTooo() {
        return Tooo;
    }

    public void setTooo(String tooo) {
        Tooo = tooo;
    }

    public String getChatt() {
        return Chatt;
    }

    public void setChatt(String chatt) {
        Chatt = chatt;
    }

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp date) {
        this.Date = date;
    }

    @Override
    public String toString() {
        return "chatdata{"+
                "CID=" + CID +
                ", From=" + Fromm + '\'' +
                ", To=" + Tooo + '\'' +
                ", Chat=" + Chatt + '\'' +
                ", Date=" + Date + '\'' +
                '}';
    }
}
