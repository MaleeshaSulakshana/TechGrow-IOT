package com.example.techgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import soup.neumorphism.NeumorphCardView;

public class DashboardActivity extends AppCompatActivity {

//    private SeekBar whetherSeeker;
    private NeumorphCardView btnTurnOnCard, btnSoilMoistureCard, btnHumidityCard, btnTemperatureCard;
    private ImageView btnTurnOnCardIcon, btnSoilMoistureCardIcon, btnHumidityCardIcon1, btnHumidityCardIcon2, btnTemperatureCardIcon;

    private LinearLayout layoutDashboard, layoutSoilMoisture, layoutHumidity, layoutTemperature;

    private String deviceIsNo = "no";

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

//        whetherSeeker = (SeekBar) this.findViewById(R.id.whetherSeeker);
//        whetherSeeker.setProgressDrawable(ContextCompat.getDrawable(DashboardActivity.this, R.drawable.seekbar_progress_color));

        layoutDashboard.setVisibility(View.VISIBLE);
        layoutSoilMoisture.setVisibility(View.GONE);
        layoutHumidity.setVisibility(View.GONE);
        layoutTemperature.setVisibility(View.GONE);

        btnTurnOnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deviceIsNo.equals("no")) {
                    deviceIsNo = "yes";
                    btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon1));
                    btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon12));
                } else {
                    deviceIsNo = "no";
                    btnTurnOnCard.setBackgroundColor(btnTurnOnCard.getContext().getResources().getColor(R.color.neon12));
                    btnTurnOnCardIcon.setColorFilter(btnTurnOnCardIcon.getContext().getResources().getColor(R.color.neon1));
                }
            }
        });

        btnSoilMoistureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutDashboard.setVisibility(View.GONE);
                layoutSoilMoisture.setVisibility(View.VISIBLE);
                layoutHumidity.setVisibility(View.GONE);
                layoutTemperature.setVisibility(View.GONE);

            }
        });

        btnHumidityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutDashboard.setVisibility(View.GONE);
                layoutSoilMoisture.setVisibility(View.GONE);
                layoutHumidity.setVisibility(View.VISIBLE);
                layoutTemperature.setVisibility(View.GONE);

            }
        });

        btnTemperatureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutDashboard.setVisibility(View.GONE);
                layoutSoilMoisture.setVisibility(View.GONE);
                layoutHumidity.setVisibility(View.GONE);
                layoutTemperature.setVisibility(View.VISIBLE);

            }
        });

    }
}