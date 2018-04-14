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

int n = 0,counter=0,i=0;
int epoch = 100;
int my_value[100];
int readingIn;
String values;
void loop() {

  //Change 42 to real time value from sensor
  if((digitalRead(16) == 1)||(digitalRead(5) == 1)){
    Serial.println('!');
  }
  else{
    readingIn= analogRead(A0);
    my_value[counter]=readingIn;
    values = values+","+readingIn;
    if(counter>=epoch)
    {
      //for(i=0;i<counter;i++)
      {
        values.remove(0,1);
        String json = "{\"value\":\""+values+"\"}";//"{\"value\":\""+String(my_value[i])+"\"}";

        StaticJsonBuffer<1000> jsonBuffer;
        JsonObject& root = jsonBuffer.parseObject(json);
        //root.printTo(Serial);
        String  path = "ECGReading/asvhatkar/"+String(n++);
        //Serial.println(path+"----"+counter);
        Firebase.set(path, root);
        if (Firebase.failed()) {
          Serial.print("pushing /logs failed:");
          Serial.println(Firebase.error());  
          return;
        }
        delay(12);
      }
      counter=0;
      values.remove(0);
    }
    else
    {
      counter+=1;
    }
    Serial.println(readingIn);
  }
  
    //Serial.println(n);
  
  delay(1);
  if(n == 5000){
    Serial.println(n);
    n = 0;
  }
}
