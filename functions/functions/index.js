const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();


// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.createEvent = functions.https.onRequest((req, res) => {

  //console.log('Logged event body to string json stringify : '+JSON.stringify(req.body));

  var event = req.body.data;
  //log event in DB
  console.log('Logged event2 : '+req.body.data);
  console.log('Logged event22 : '+req.body.data.name);
  // Push the new message into the Realtime Database using the Firebase Admin SDK.


  return res.send(req.body);

});

//send event to Trello when Logged
// String trelloTestUrl = "https://api.trello.com/1/cards?key=de550134f2d5442e79227c8301ddd52b&token=63c3a68d47dde6f9099c2aa5535c184801997d3712ec0add584d3b9b566deeec";
