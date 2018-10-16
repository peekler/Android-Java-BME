package hu.bme.aut.android.moneyconverter.network;

import hu.bme.aut.android.moneyconverter.data.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyAPI {
    @GET("latest")
    Call<MoneyResult> getRates(@Query("base") String base);
}


//http://api.fixer.io/
//
// latest
//
// ?base=USD