package com.gari.canarychallenge.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * Created by gerard on 2016-05-22.
 */
public class Route {



    public float langtitue;
    public float lotituede;
    public String routename;

    public float getLotituede() {
        return lotituede;
    }

    public void setLotituede(float lotituede) {
        this.lotituede = lotituede;
    }

    public String getRoutename() {

        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public float getLangtitue() {

        return langtitue;
    }

    public void setLangtitue(float langtitue) {
        this.langtitue = langtitue;
    }

    public List<LatLng> points;
}
