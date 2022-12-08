package com.example.techgrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import soup.neumorphism.NeumorphCardView;

public class DashboardActivity extends AppCompatActivity {

    private NeumorphCardView btnTurnOnCard, btnSoilMoistureCard, btnHumidityCard, btnTemperatureCard;
    private ImageView btnTurnOnCardIcon, btnSoilMoistureCardIcon, btnHumidityCardIcon1, btnHumidityCardIcon2, btnTemperatureCardIcon;

    private LinearLayout layoutDashboard, layoutSoilMoisture, layoutHumidity, layoutTemperature;
    private TextView dashboardSoilMoistureText, dashboardHumidityText, dashboardTemperatureText,
            txtSeekerSoilValue, txtSeekerHumidityValue, txtSeekerTemperatureValue;
    private SeekBar soilMoistureSeeker, humiditySeeker, temperatureSeeker;

    private String deviceIsOn = "Off";
    private int soilMoistureSeekValue = 0, humiditySeekValue = 0, temperatureSeekValue = 0;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnTurnOnCard = (NeumorphCardView) this.findViewById(R.id.btnTurnOnCard);
        btnSoilMoistureCard = (NeumorphCardView) this.findViewById(R.id.btnSoilMoistureCard);
        btnHumidityCard = (NeumorphCardView) this.findViewById(R.id.btnHumidityCard);
        btnTemperatureCard = (NeumorphCardView) this.findViewById(R.id.btnTemperatureCard);

        btnTurnOnCardIcon = (ImageView) this.findViewById(R.id.btnTurnOnCardIcon);
        btnSoilMoistureCardIcon = (ImageView) this.findViewById(R.id.btnSoilMoistureCardIcon);
        btnHumidityCardIcon1 = (ImageView) this.findViewById(R.id.btnHumidityCardIcon1);
        btnHumidityCardIcon2 = (ImageView) this.findViewById(R.id.btnHumidityCardIcon2);
        btnTemperatureCardIcon = (ImageView) this.findViewById(R.id.btnTemperatureCardIcon);

        layoutDashboard = (LinearLayout) this.findViewById(R.id.layoutDashboard);
        layoutSoilMoisture = (LinearLayout) this.findViewById(R.id.layoutSoilMoisture);
        layoutHumidity = (LinearLayout) this.findViewById(R.id.layoutHumidity);
        layoutTemperature = (LinearLayout) this.findViewById(R.id.layoutTemperature);

        dashboardSoilMoistureText = (TextView) this.findViewById(R.id.dashboardSoilMoistureText);
        dashboardHumidityText = (TextView) this.findViewById(R.id.dashboardHumidityText);
        dashboardTemperatureText = (TextView) this.findViewById(R.id.dashboardTemperatureText);

        txtSeekerSoilValue = (TextView) this.findViewById(R.id.txtSeekerSoilValue);
        txtSeekerHumidityValue = (TextView) this.findViewById(R.id.txtSeekerHumidityValue);
        txtSeekerTemperatureValue = (TextView) this.findViewById(R.id.txtSeekerTemperatureValue);

        soilMoistureSeeker = (SeekBar) this.findViewById(R.id.soilMoistureSeeker);
        humiditySeeker = (SeekBar) this.findViewById(R.id.humiditySeeker);
        temperatureSeeker = (SeekBar) this.findViewById(R.id.temperatureSeeker);

//        Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("TechGrow");

        showDashboardLayout();
        setValuesFromDatabase();

        dashboardSoilMoistureText.setText(String.valueOf(soilMoistureSeekValue));
        dashboardHumidityText.setText(String.valueOf(humiditySeekValue));
        dashboardTemperatureText.setText(String.valueOf(temperatureSeekValue) + " \u2103");

        txtSeekerSoilValue.setText(String.valueOf(soilMoistureSeekValue));
        txtSeekerHumidityValue.setText(String.valueOf(humiditySeekValue));
        txtSeekerTemperatureValue.setText(String.valueOf(temperatureSeekValue));

        soilMoistureSeeker.setProgress(soilMoistureSeekValue);
        humiditySeeker.setProgress(humiditySeekValue);
        temperatureSeeker.setProgress(temperatureSeekValue);

        btnTurnOnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deviceIsOn.equals("Off")) {
                    deviceIsOn = "On";
                    setDeviceOnOffValueToDatabase("On");
                    btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon1));
                    btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon12));
                } else {
                    deviceIsOn = "Off";
                    setDeviceOnOffValueToDatabase("Off");
                    btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon12));
                    btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon1));
                }
            }
        });

        btnSoilMoistureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layoutSoilMoisture.getVisibility() == View.VISIBLE) {
                    showDashboardLayout();
                } else {

                    changeCardIconDefaultColor();
                    setPressedTypeToCardButtons();

                    layoutDashboard.setVisibility(View.GONE);
                    layoutSoilMoisture.setVisibility(View.VISIBLE);
                    layoutHumidity.setVisibility(View.GONE);
                    layoutTemperature.setVisibility(View.GONE);

                    btnSoilMoistureCardIcon.setColorFilter(btnSoilMoistureCardIcon.getContext().getResources().getColor(R.color.neon12));

                    btnSoilMoistureCard.setShapeType(1);
                }

            }
        });

        btnHumidityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layoutHumidity.getVisibility() == View.VISIBLE) {
                    showDashboardLayout();
                } else {

                    changeCardIconDefaultColor();
                    setPressedTypeToCardButtons();

                    layoutDashboard.setVisibility(View.GONE);
                    layoutSoilMoisture.setVisibility(View.GONE);
                    layoutHumidity.setVisibility(View.VISIBLE);
                    layoutTemperature.setVisibility(View.GONE);

                    btnHumidityCardIcon1.setColorFilter(btnHumidityCardIcon1.getContext().getResources().getColor(R.color.neon12));
                    btnHumidityCardIcon2.setColorFilter(btnHumidityCardIcon2.getContext().getResources().getColor(R.color.neon12));

                    btnHumidityCard.setShapeType(1);
                }

            }
        });

        btnTemperatureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layoutTemperature.getVisibility() == View.VISIBLE) {
                    showDashboardLayout();
                } else {

                    changeCardIconDefaultColor();
                    setPressedTypeToCardButtons();

                    layoutDashboard.setVisibility(View.GONE);
                    layoutSoilMoisture.setVisibility(View.GONE);
                    layoutHumidity.setVisibility(View.GONE);
                    layoutTemperature.setVisibility(View.VISIBLE);

                    btnTemperatureCardIcon.setColorFilter(btnTemperatureCardIcon.getContext().getResources().getColor(R.color.neon12));

                    btnTemperatureCard.setShapeType(1);
                }

            }
        });

        soilMoistureSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                soilMoistureSeekValue = i;
                txtSeekerSoilValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setSoilMoistureSeekValueToDatabase(soilMoistureSeekValue);
            }
        });

        humiditySeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                humiditySeekValue = i;
                txtSeekerHumidityValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setHumiditySeekValueToDatabase(humiditySeekValue);
            }
        });

        temperatureSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                temperatureSeekValue = i;
                txtSeekerTemperatureValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setTemperatureSeekValueToDatabase(temperatureSeekValue);
            }
        });

    }

    private void showDashboardLayout() {

        layoutDashboard.setVisibility(View.VISIBLE);
        layoutSoilMoisture.setVisibility(View.GONE);
        layoutHumidity.setVisibility(View.GONE);
        layoutTemperature.setVisibility(View.GONE);

        changeCardIconDefaultColor();
        setPressedTypeToCardButtons();
    }

    private void changeCardIconDefaultColor() {

        btnSoilMoistureCardIcon.setColorFilter(btnSoilMoistureCardIcon.getContext().getResources().getColor(R.color.dark_blue0));
        btnHumidityCardIcon1.setColorFilter(btnHumidityCardIcon1.getContext().getResources().getColor(R.color.dark_blue0));
        btnHumidityCardIcon2.setColorFilter(btnHumidityCardIcon2.getContext().getResources().getColor(R.color.dark_blue0));
        btnTemperatureCardIcon.setColorFilter(btnTemperatureCardIcon.getContext().getResources().getColor(R.color.dark_blue0));

    }

    private void setPressedTypeToCardButtons() {

        btnSoilMoistureCard.setShapeType(0);
        btnHumidityCard.setShapeType(0);
        btnTemperatureCard.setShapeType(0);

    }

//    Set soil moisture seek value
    private void setSoilMoistureSeekValueToDatabase(int value) {
        databaseReference.child("SoilMoisture").child("DetectLine").setValue(value).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Soil moisture detect line not set!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    Set humidity seek value
    private void setHumiditySeekValueToDatabase(int value) {
        databaseReference.child("Humidity").child("DetectLine").setValue(value).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Humidity detect line not set!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    Set temperature seek value
    private void setTemperatureSeekValueToDatabase(int value) {
        databaseReference.child("Temperature").child("DetectLine").setValue(value).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Temperature detect line not set!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    Set device on/off
    private void setDeviceOnOffValueToDatabase(String value) {
        databaseReference.child("Device").setValue(value).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Device not '" + value + "'!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    Get values from database
    private void setValuesFromDatabase() {

        Toast.makeText(DashboardActivity.this,"Waiting for get data from database!", Toast.LENGTH_SHORT).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    if (snapshot.child("Device").exists()) {
                        if (snapshot.child("Device").exists()) {

                            deviceIsOn = snapshot.child("Device").getValue().toString();

                            if (deviceIsOn.equals("On")) {

                                btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon1));
                                btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon12));
                            } else {

                                btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon12));
                                btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon1));
                            }

                        }
                    }

                    if (snapshot.child("SoilMoisture").exists()) {
                        if (snapshot.child("SoilMoisture").child("DetectLine").exists()) {

                            soilMoistureSeekValue = Integer.valueOf(snapshot.child("SoilMoisture").child("DetectLine").getValue().toString());
//                            dashboardSoilMoistureText.setText(String.valueOf(soilMoistureSeekValue));
                            txtSeekerSoilValue.setText(String.valueOf(soilMoistureSeekValue));
                            soilMoistureSeeker.setProgress(soilMoistureSeekValue);

                        }
                    }

                    if (snapshot.child("Humidity").exists()) {
                        if (snapshot.child("Humidity").child("DetectLine").exists()) {

                            humiditySeekValue = Integer.valueOf(snapshot.child("Humidity").child("DetectLine").getValue().toString());
                            txtSeekerHumidityValue.setText(String.valueOf(humiditySeekValue));
                            humiditySeeker.setProgress(humiditySeekValue);

                        }
                    }

                    if (snapshot.child("Temperature").exists()) {
                        if (snapshot.child("Temperature").child("DetectLine").exists()) {

                            temperatureSeekValue = Integer.valueOf(snapshot.child("Temperature").child("DetectLine").getValue().toString());
                            txtSeekerTemperatureValue.setText(String.valueOf(temperatureSeekValue));
                            temperatureSeeker.setProgress(temperatureSeekValue);

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}