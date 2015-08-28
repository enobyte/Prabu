package com.larizon.prabu.koneksi;

/**
 * Created by EnoByte on 5/14/2015. INI ADALAH CLASS UNTUK CEK KONEKSI INTERNET
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context){
        this.context = context;
    }

    public boolean ketikKonekInternet(){
        ConnectivityManager koneksi = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (koneksi != null){
            NetworkInfo[]info = koneksi.getAllNetworkInfo();
            if (info != null){
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState()== NetworkInfo.State.CONNECTED){
                        return true;
                    }
            }

        }
        return false;
    }
}
