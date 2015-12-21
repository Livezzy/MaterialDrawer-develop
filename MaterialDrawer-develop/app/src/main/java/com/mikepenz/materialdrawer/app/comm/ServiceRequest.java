package com.mikepenz.materialdrawer.app.comm;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ServiceRequest {

    private static ServiceRequest singleton = null;
    private static RequestQueue requestQueue;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private ServiceRequest() {
    }

    /* Static 'instance' method */
    public static ServiceRequest getInstance(Context context) {
        if (singleton == null) {
            requestQueue = Volley.newRequestQueue(context);
            singleton = new ServiceRequest();
        }
        return singleton;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

}