package com.example.hpp;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {

    EditText Square_feet, Rooms, Bathrooms, Construction_year, Number_of_levels, Floor, Number_of_balconies, Postcard;
    Button predict;
    TextView result;
    AutoCompleteTextView Location, Construction_material, Close_to_the_sea, Close_to_the_center, Heat, Renovated, Garden, Parking;
    String url = "https://house-price-predictor-ekgz.onrender.com/predict";
    Map<String, String> locationMap, constructionMaterialMap, yesNoMap, heatMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        // Initialize mappings
        initializeMappings();

        Location = findViewById(R.id.autoCompleteTextView1);
        Location.setHint(res.getString(R.string.select));
        setupAutoCompleteTextView(Location, res.getStringArray(R.array.location_values));

        Construction_material = findViewById(R.id.autoCompleteTextView2);
        Construction_material.setHint(res.getString(R.string.construction_material));
        setupAutoCompleteTextView(Construction_material, res.getStringArray(R.array.construction_material_values));

        Close_to_the_sea = findViewById(R.id.autoCompleteTextView3);
        Close_to_the_sea.setHint(res.getString(R.string.close_to_the_sea));
        setupAutoCompleteTextView(Close_to_the_sea, res.getStringArray(R.array.yes_no_values));

        Close_to_the_center = findViewById(R.id.autoCompleteTextView4);
        Close_to_the_center.setHint(res.getString(R.string.close_to_the_center));
        setupAutoCompleteTextView(Close_to_the_center, res.getStringArray(R.array.yes_no_values));

        Heat = findViewById(R.id.autoCompleteTextView5);
        Heat.setHint(res.getString(R.string.heat));
        setupAutoCompleteTextView(Heat, res.getStringArray(R.array.heat_values));

        Renovated = findViewById(R.id.autoCompleteTextView6);
        Renovated.setHint(res.getString(R.string.renovated));
        setupAutoCompleteTextView(Renovated, res.getStringArray(R.array.yes_no_values));

        Garden = findViewById(R.id.autoCompleteTextView7);
        Garden.setHint(res.getString(R.string.garden));
        setupAutoCompleteTextView(Garden, res.getStringArray(R.array.yes_no_values));

        Parking = findViewById(R.id.autoCompleteTextView8);
        Parking.setHint(res.getString(R.string.parking));
        setupAutoCompleteTextView(Parking, res.getStringArray(R.array.yes_no_values));

        Square_feet = findViewById(R.id.square_feet);
        Rooms = findViewById(R.id.rooms);
        Bathrooms = findViewById(R.id.bathrooms);
        Construction_year = findViewById(R.id.construction_year);
        Number_of_levels = findViewById(R.id.number_of_levels);
        Floor = findViewById(R.id.floor);
        Number_of_balconies = findViewById(R.id.number_of_balconies);
        Postcard = findViewById(R.id.postcard);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("price");
                                    String housePriceText = getString(R.string.house_price_is) + " " + data;
                                    result.setText(housePriceText);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                String message = (volleyError.getMessage() != null) ? volleyError.getMessage() : "Missing Data";
                                Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                Log.e("VolleyError", "Error during HTTP request", volleyError);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Location", getMappedValue(Location.getText().toString(), locationMap));
                        params.put("Square_feet", Square_feet.getText().toString());
                        params.put("Rooms", Rooms.getText().toString());
                        params.put("Bathrooms", Bathrooms.getText().toString());
                        params.put("Construction_year", Construction_year.getText().toString());
                        params.put("Construction_material", getMappedValue(Construction_material.getText().toString(), constructionMaterialMap));
                        params.put("Number_of_levels", Number_of_levels.getText().toString());
                        params.put("Close_to_the_sea", getMappedValue(Close_to_the_sea.getText().toString(), yesNoMap));
                        params.put("Close_to_the_center", getMappedValue(Close_to_the_center.getText().toString(), yesNoMap));
                        params.put("Floor", Floor.getText().toString());
                        params.put("Heat", getMappedValue(Heat.getText().toString(), heatMap));
                        params.put("Number_of_balconies", Number_of_balconies.getText().toString());
                        params.put("Renovated", getMappedValue(Renovated.getText().toString(), yesNoMap));
                        params.put("Garden", getMappedValue(Garden.getText().toString(), yesNoMap));
                        params.put("Postcard", Postcard.getText().toString());
                        params.put("Parking", getMappedValue(Parking.getText().toString(), yesNoMap));
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }

    private void setupAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView, String[] values) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spin, values);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private void initializeMappings() {
        locationMap = new HashMap<>();
        locationMap.put("Athens", "Athens");
        locationMap.put("Αθήνα", "Athens");
        locationMap.put("Thessaloniki", "Thessaloniki");
        locationMap.put("Θεσσαλονίκη", "Thessaloniki");
        locationMap.put("Patra", "Patra");
        locationMap.put("Πάτρα", "Patra");
        locationMap.put("Piraeus", "Piraeus");
        locationMap.put("Πειραιάς", "Piraeus");
        locationMap.put("Larissa", "Larissa");
        locationMap.put("Λάρισα", "Larissa");
        locationMap.put("Iraklion", "Iraklion");
        locationMap.put("Ηράκλειο", "Iraklion");
        locationMap.put("Bolos", "Bolos");
        locationMap.put("Βόλος", "Bolos");
        locationMap.put("Ioannina", "Ioannina");
        locationMap.put("Ιωάννινα", "Ioannina");
        locationMap.put("Trikala", "Trikala");
        locationMap.put("Τρίκαλα", "Trikala");
        locationMap.put("Chalkida", "Chalkida");
        locationMap.put("Χαλκίδα", "Chalkida");
        locationMap.put("Serres", "Serres");
        locationMap.put("Σέρρες", "Serres");
        locationMap.put("Alexandroupoli", "Alexandroupoli");
        locationMap.put("Αλεξανδρούπολη", "Alexandroupoli");
        locationMap.put("Xanthi", "Xanthi");
        locationMap.put("Ξάνθη", "Xanthi");
        locationMap.put("Katerini", "Katerini");
        locationMap.put("Κατερίνη", "Katerini");
        locationMap.put("Kalamata", "Kalamata");
        locationMap.put("Καλαμάτα", "Kalamata");
        locationMap.put("Rhodes", "Rhodes");
        locationMap.put("Ρόδος", "Rhodes");
        locationMap.put("Chania", "Chania");
        locationMap.put("Χανιά", "Chania");
        locationMap.put("Komotini", "Komotini");
        locationMap.put("Κομοτηνή", "Komotini");
        locationMap.put("Kavala", "Kavala");
        locationMap.put("Καβάλα", "Kavala");
        locationMap.put("Agrinio", "Agrinio");
        locationMap.put("Αγρίνιο", "Agrinio");
        locationMap.put("Drama", "Drama");
        locationMap.put("Δράμα", "Drama");
        locationMap.put("Veroia", "Veroia");
        locationMap.put("Βέροια", "Veroia");

        constructionMaterialMap = new HashMap<>();
        constructionMaterialMap.put("brick", "brick");
        constructionMaterialMap.put("τούβλο", "brick");
        constructionMaterialMap.put("wood", "wood");
        constructionMaterialMap.put("ξύλο", "wood");

        yesNoMap = new HashMap<>();
        yesNoMap.put("yes", "yes");
        yesNoMap.put("ναι", "yes");
        yesNoMap.put("no", "no");
        yesNoMap.put("όχι", "no");

        heatMap = new HashMap<>();
        heatMap.put("central", "central");
        heatMap.put("κεντρική", "central");
        heatMap.put("autonomous", "autonomous");
        heatMap.put("αυτόνομη", "autonomous");
        heatMap.put("gas", "gas");
        heatMap.put("αέριο", "gas");
        heatMap.put("pellet", "pellet");
        heatMap.put("πελλέτ", "pellet");
    }

    private String getMappedValue(String inputValue, Map<String, String> map) {
        return map.getOrDefault(inputValue, inputValue);
    }
}