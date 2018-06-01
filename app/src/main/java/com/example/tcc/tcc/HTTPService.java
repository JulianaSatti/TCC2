package com.example.tcc.tcc;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void,Void,String> {
    private final String parametros;
    private final String arquivo;

    public HTTPService(String arquivo, String parametros){
        this.parametros = parametros;
        this.arquivo = arquivo;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        URL url;

        try {
            String strUrl = "http://35.199.87.88/api/"+ this.arquivo +".php";
            if(!(this.parametros.trim().equals(""))){
                strUrl = strUrl + "/?"+ this.parametros;
            }

            url = new URL(strUrl.replaceAll(" ","%20"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            //connection.setDoOutput(true);
            connection.setConnectTimeout(5000);

            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                resposta.append(scanner.nextLine());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resposta.toString();
    }
}
