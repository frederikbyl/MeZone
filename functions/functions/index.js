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


  // Push the new message into the Realtime Database using the Firebase Admin SDK.
  return res.send('frederik');

});
