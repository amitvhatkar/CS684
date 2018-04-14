#include <ESP8266WiFi.h>
  #include <FirebaseArduino.h>
    
    // Set these to run example.
    #define FIREBASE_HOST "realheart-26433.firebaseio.com"
    #define FIREBASE_AUTH "ntxFNz0ODwMqMBTwe5JDIYdsIP3XhWWkfHSMoJxV"
    //Change line with your WiFi router name and password
    #define WIFI_SSID "amitvhatkar"  
    #define WIFI_PASSWORD "Im@iitb17"
    
    void setup() {
          Serial.begin(9600);
          
          // connect to wifi.
         
          
          WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
          //Serial.print("connecting");
          while (WiFi.status() != WL_CONNECTED) {
              //Serial.print(".");
              delay(500);
          }
          pinMode(16,INPUT_PULLUP);
          pinMode(5,INPUT_PULLUP);
          //Serial.println();
          //Serial.print("connected: ");
          //Serial.println(WiFi.localIP());
          
          Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    }
    
    int n = 0;
    
    void loop() {
        int readingIn = analogRead(A0);
        //Change 42 to real time value from sensor
        if((digitalRead(16) == 1)||(digitalRead(5) == 1)){
          Serial.println('!');
        }
        else{
          String json = "{\"value\":\""+String(readingIn)+"\"}";
          Serial.println(readingIn);
          StaticJsonBuffer<200> jsonBuffer;
          JsonObject& root = jsonBuffer.parseObject(json);
          //root.printTo(Serial);
          String  path = "ECGReading/asvhatkar/"+String(n++);
          Firebase.set(path, root);
          // handle error
          if (Firebase.failed()) {
            Serial.print("pushing /logs failed:");
            Serial.println(Firebase.error());  
            return;
          }
        }
        
        delay(1);
}
