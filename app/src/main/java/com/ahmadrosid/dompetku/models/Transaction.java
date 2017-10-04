package com.ahmadrosid.dompetku.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by staf on 04-Oct-17.
 */

@Table(name = "Transaksi")
public class Transaction extends Model implements Serializable {

    @Column(name = "Title")
    public String title;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "Date")
    public long date;

    @Column(name = "Type")
    public TransactionType type;

    public enum TransactionType {
        PENGELUARAN,
        PEMASUKAN
    }

    public Transaction() {
    }

    public Transaction(String title, int amount, long date, TransactionType type) {
        super();
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

}
