const functions = require('firebase-functions');
const express = require('express');
const serviceAccount = require("./realheart-26433-firebase-adminsdk-cvzd2-b676cb74ff.json")
const app = express();
var admin = require("firebase-admin");

admin.initializeApp({
  //credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://realheart-26433.firebaseio.com"
});

const db = admin.firestore();	

var rootRef = admin.database().ref("ECGReading");

rootRef.on("child_changed", function(snapshot) {
	
var user_char = [];		
	snapshot.forEach(function(child){
                var key = child.key;
                var value = child.val();
		console.log(value["value"]+"-----")
                user_char[key] = value;
          });
  		console.log(user_char);			  
}, function (errorObject) {
  console.log("The read failed: " + errorObject.code);
});

/*app.get('/timestamp', (request, response) => {
	response.send('$Date.now()');
});

app.get('/timestamp-chached', (request, response) => {
	resposne.set('Cash-Control', 'public, max-age=300,  s-maxage=600')
	response.send('$Date.now()');
});*/
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
 exports.app = functions.https.onRequest(app);
