package hu.bme.aut.android.moneyconverter.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoneyResult {
    @SerializedName("__deprecation_message__")
    @Expose
    public String deprecationMessage;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("rates")
    @Expose
    public Rates rates;


    public String getDeprecationMessage() {
        return deprecationMessage;
    }

    public void setDeprecationMessage(String deprecationMessage) {
        this.deprecationMessage = deprecationMessage;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}
