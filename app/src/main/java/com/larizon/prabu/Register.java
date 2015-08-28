package com.larizon.prabu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.larizon.prabu.AlertDialog.AlertDialogManager;
import com.larizon.prabu.koneksi.ConnectionDetector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends Activity {
    JSONParser jsonParser = new JSONParser();
    ProgressDialog pDialog;
    Button register;
    EditText username, password, repassword, nama, la, lo;
    Boolean adaInternet = false;
    ConnectionDetector cd;
    private static String url = "http://192.168.11.12/testprabu/Register.php";
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        repassword = (EditText) findViewById(R.id.repassword);
        nama = (EditText) findViewById(R.id.nama);
        register = (Button) findViewById(R.id.daftar);

        // Code untuk mengecek Aktifasi GPS
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        GPSTracker gpsTracker = new GPSTracker(this);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Not Active");
            builder.setMessage("Please enable GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    finish();
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else {
            String latitude = String.valueOf(gpsTracker.latitude);
            la = (EditText) findViewById(R.id.la);
            la.setText(latitude);

            String longitude = String.valueOf(gpsTracker.longitude);
            lo = (EditText) findViewById(R.id.lo);
            lo.setText(longitude);
        }
        cekInternet();

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!adaInternet.equals(cd.ketikKonekInternet())) {
                    Toast.makeText(getApplicationContext(), "Perika Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)); //MASUK KE SISTEM SETTING NETWORK

                } else if (!repassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(Register.this, "PASSWORD TIDAK SAMA", Toast.LENGTH_LONG).show();
                    password.setText("");
                    repassword.setText("");

                } else if (password.getText().toString().trim().length() < 8) {
                    Toast.makeText(Register.this, "PASSWORD MINIMAL 8 KARAKTER", Toast.LENGTH_LONG).show();
                    repassword.setText("");
                    password.setText("");
                } else if (username.getText().toString().trim().length() > 0 && nama.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
                    new daftarAku().execute();

                } else {

                    Toast.makeText(Register.this, "TERDAPAT INPUTAN YANG MASIH KOSONG", Toast.LENGTH_LONG).show();


                }

            }
        });


    }

    public class daftarAku extends AsyncTask<String, String, String> {
        String success;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Proses Mendaftar....");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            String strusername = username.getText().toString();
            String strpassword = password.getText().toString();
            String strnama = nama.getText().toString();
            String strla = la.getText().toString();
            String strlo = lo.getText().toString();


            List<NameValuePair> nvp = new ArrayList<>();
            nvp.add(new BasicNameValuePair("username", strusername));
            nvp.add(new BasicNameValuePair("password", strpassword));
            nvp.add(new BasicNameValuePair("nama", strnama));
            nvp.add(new BasicNameValuePair("la", strla));
            nvp.add(new BasicNameValuePair("lo", strlo));

            JSONObject json;
            json = jsonParser.MakeHttpRequest(url, "POST", nvp);
            try {
                success = json.getString("success");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Registrasi Gagal", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }

    public void cekInternet() {
        cd = new ConnectionDetector(getApplicationContext());
        adaInternet = cd.ketikKonekInternet();
        if (adaInternet) {
            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG).show();
        } else {
            alert.showAlertDialog(Register.this, "peringatan", "Periksa Koneksi Internet Anda", false);
        }
    }

}