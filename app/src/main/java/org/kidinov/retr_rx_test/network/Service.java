package org.kidinov.retr_rx_test.network;

import org.kidinov.retr_rx_test.model.Model;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface Service {
    @GET("/getData1")
    Observable<List<Model>> getData1(@Query("limit") int limit);

    @GET("/getData2")
    Observable<List<Model>> getData2(@Query("limit") int limit);
}
