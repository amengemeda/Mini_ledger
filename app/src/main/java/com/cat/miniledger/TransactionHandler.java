package com.cat.miniledger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionHandler {
    private double transactionAmount;
    private String transactionDate;
    private String transactionSource;
    private String transactionType;

    public TransactionHandler(double transactionAmount, String transactionType, String transactionDate,String transactionSource ) {
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionSource = transactionSource;
        this.transactionType = transactionType;
    }

    public TransactionHandler() {
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public String getTransactionDate() {
        return parseDateToddMMyyyy(transactionDate);
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionSource() { return transactionSource; }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
