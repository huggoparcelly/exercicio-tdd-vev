package main;

public class Boleto {
    private int boletoCode;
    private String date;
    private double totalPaid;

    public Boleto(int boletoCode, String date, double totalPaid) {
        if (boletoCode <= 0) {
            throw new IllegalArgumentException("Boleto code must be a positive number.");
        }
        if (date == "") {
            throw new IllegalArgumentException("Invalid date");
        }
        if (totalPaid < 0) {
            throw new IllegalArgumentException("Total paid must be a non-negative value.");
        }

        this.boletoCode = boletoCode;
        this.date = date;
        this.totalPaid = totalPaid;
    }

    public int getBoletoCode(){
        return boletoCode;
    }

    public double getTotalPaid(){
        return totalPaid;
    }

    public String getDate(){
        return date;
    }


}