package jsonparse.kangondiaravind.com.jsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String jsonurl = "https://api.myjson.com/bins/zodef";
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button hit = (Button)findViewById(R.id.btnHit);
        display = (TextView)findViewById(R.id.tvdisplay);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new JsonTask().execute();
            }
        });
    }
    class JsonTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream =urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line=bufferedReader.readLine())!=null)
                {
                    buffer.append(line);
                }

                String finaljson = buffer.toString();

                JSONObject parentobject = new JSONObject(finaljson);
                JSONArray moviesArray = parentobject.getJSONArray("movies");

                StringBuffer buffer1 = new StringBuffer();
                for(int i=0;i<moviesArray.length();i++)
                {
                    JSONObject moviesobject = moviesArray.getJSONObject(i);

                    String movie = moviesobject.getString("movie");
                    int year = moviesobject.getInt("year");
                    buffer1.append(movie+" - "+year+"\n");
                }

                return buffer1.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            display.setText(s);

        }
    }
}
