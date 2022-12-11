#include "FirebaseESP32.h"
#include <WiFi.h>
#include <DHT.h>

#define FIREBASE_HOST "techgrow-d6fd7-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "BH2R1URdnb96RxGcSgGQmD9GMUb31OLGc6mZGzR1"
#define WIFI_SSID "SLT-4G_16D5AA"
#define WIFI_PASSWORD "A5DF3CEB"

#define DHTPIN 4  // Connect Data pin of DHT to D2

// Set pins to sensors and actuators
int led_device_on = 5;
int led_day_or_night = 19;
int led_water_pump_indicator = 21;

int relay_water_pump = 33;
int relay_window_fan = 32;

int ldr_sensor = 18;
int soil_moisture_sensor = 13;

#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

FirebaseData firebaseData;

int temperatureDetectLine = 0;
int humidityDetectLine = 0;
int soilMoistureDetectLine = 0;

int temperatureValue = 0;
int humidityValue = 0;
int soilMoistureValue = 0;

void setup() {

  Serial.begin(9600);

  dht.begin();
  pinMode(led_device_on, OUTPUT);
  pinMode(led_day_or_night, OUTPUT);
  pinMode(led_water_pump_indicator, OUTPUT);
  pinMode(relay_water_pump, OUTPUT);
  pinMode(relay_window_fan, OUTPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
}

void humidity_sensor_update() {

  humidityValue = dht.readHumidity();
  temperatureValue = dht.readTemperature();

  if (isnan(humidityValue) || isnan(temperatureValue)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  if (Firebase.setInt(firebaseData, "/TechGrow/Temperature/CurrentValue", temperatureValue)) {
    Serial.println("Passed Temperature!");
  } else {
    Serial.println("Failed Temperature!");
  }

  if (Firebase.setInt(firebaseData, "/TechGrow/Humidity/CurrentValue", humidityValue)) {
    Serial.println("Passed Humidity!");
  } else {
    Serial.println("Failed Humidity!");
  }
}


void ldr_sensor_update() {

  int temp = digitalRead(ldr_sensor);
  String day_or_night_status = "Day";

  if (temp == 1) {
    digitalWrite(led_day_or_night, HIGH);
    day_or_night_status = "Night";

  } else if (temp == 0) {
    digitalWrite(led_day_or_night, LOW);
  }

  if (Firebase.setString(firebaseData, "/TechGrow/DayOrNight", day_or_night_status)) {
    Serial.println("Passed Day or Night Status!");
  } else {
    Serial.println("Passed Day or Night Status!");
  }
}

void soil_moisture_sensor_update() {

  soilMoistureValue = (100 - ((analogRead(soil_moisture_sensor) / 1023.00) * 100));

  if (Firebase.setInt(firebaseData, "/TechGrow/SoilMoisture/CurrentValue", soilMoistureValue)) {
    Serial.println("Passed Soil Moisture!");
  } else {
    Serial.println("Failed Soil Moisture!");
  }
  
}

void update_actuators() {

  if (Firebase.getInt(firebaseData, "/TechGrow/Temperature/DetectLine")) {

    temperatureDetectLine = firebaseData.intData();
  }

  if (Firebase.getInt(firebaseData, "/TechGrow/Humidity/DetectLine")) {

    humidityDetectLine = firebaseData.intData();
  }

  if (Firebase.getInt(firebaseData, "/TechGrow/SoilMoisture/DetectLine")) {

    soilMoistureDetectLine = firebaseData.intData();
  }
  
  // Check values and detect
  // Humidity and soil moisture
  String fan_and_window_status = "Off";
  String water_pump_status = "Off";

  String water_pump_date_time = "";
  String window_and_fan_date_time = "";
  
  if ((humidityDetectLine < humidityValue) && (soilMoistureDetectLine < soilMoistureValue)) {
    
    digitalWrite(led_water_pump_indicator, HIGH);
    digitalWrite(relay_water_pump, HIGH);
    water_pump_status = "On";

  } else {
    digitalWrite(led_water_pump_indicator, LOW);
    digitalWrite(relay_water_pump, LOW);

  }

  // Tempurature  
  if (temperatureDetectLine < temperatureValue) {
    
    digitalWrite(relay_window_fan, HIGH);
    fan_and_window_status = "On";

  } else {
    digitalWrite(relay_window_fan, LOW);

  }

  // Update actuators status
  if (Firebase.setString(firebaseData, "/TechGrow/Actuator/Window/Status", fan_and_window_status)) {
    Serial.println("Passed Window Status!");
  } else {
    Serial.println("Failed Window Status!");
  }

  if (Firebase.setString(firebaseData, "/TechGrow/Actuator/WaterPump/Status", water_pump_status)) {
    Serial.println("Passed Water Pump Status!");
  } else {
    Serial.println("Failed Water Pump Status!");
  }

}

void loop() {
  
  if (Firebase.getString(firebaseData, "/TechGrow/Device")) {

    if (firebaseData.stringData() == "On") {
      digitalWrite(led_device_on, HIGH);
      
      ldr_sensor_update();
      humidity_sensor_update();
      soil_moisture_sensor_update();

      update_actuators();

    } else if (firebaseData.stringData() == "Off") {
      digitalWrite(led_device_on, LOW);
    }
  }
}
