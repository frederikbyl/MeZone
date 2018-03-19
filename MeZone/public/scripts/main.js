/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

// Initializes FriendlyChat.
function MeZone() {
  this.checkSetup();

  // Shortcuts to DOM Elements.
  this.mezoneMap = document.getElementById('map');
  this.submitButton = document.getElementById('submit');
  this.submitImageButton = document.getElementById('submitImage');
  this.userLocation = document.getElementById('user-location');
  this.userName = document.getElementById('user-name');
  this.signInButton = document.getElementById('sign-in');
  this.signOutButton = document.getElementById('sign-out');
  this.signInSnackbar = document.getElementById('must-signin-snackbar');
  this.logEventButton = document.getElementById('log-event');

  this.signOutButton.addEventListener('click', this.signOut.bind(this));
  this.signInButton.addEventListener('click', this.signIn.bind(this));
  this.logEventButton.addEventListener('click', this.createEvent.bind(this));
  this.events = [];
  // Toggle for the button.
  var buttonTogglingHandler = this.toggleButton.bind(this);

  this.mezoneMap.removeAttribute('hidden');
  this.initFirebase();

  this.meZoneEvents = this.database.ref('meZoneEvents');
  this.loadEvents();

  //this.toggleHeatMap();

}

// Sets up shortcuts to Firebase features and initiate firebase auth.
MeZone.prototype.initFirebase = function() {
  // Shortcuts to Firebase SDK features.
  this.auth = firebase.auth();
  this.database = firebase.database();
  this.storage = firebase.storage();
  // Initiates Firebase auth and listen to auth state changes.
  this.auth.onAuthStateChanged(this.onAuthStateChanged.bind(this));
};


// Saves a new event on the Firebase DB.
MeZone.prototype.createEvent = function(e) {
  e.preventDefault();

  // Check that the user entered a message and is signed in.
  if (this.checkSignedInWithMessage()) {
    if (navigator.geolocation) {
       navigator.geolocation.getCurrentPosition(function(position) {
         var pos = {
           lat: position.coords.latitude,
           lng: position.coords.longitude
         };


        //////SAVE//////
         var currentUser = mezone.auth.currentUser;
           // Add a new message entry to the Firebase Database.
         mezone.meZoneEvents.push({
           uid: currentUser.uid,
           email: currentUser.email,
           position: pos,
           eventType: 'public',
           eventTag: 'litter'
         }).then(function() {
           // Clear message text field and SEND button state.
          window.alert("SAVED");
        }.bind(this)).catch(function(error) {
           console.error('Error writing new message to Firebase Database', error);
         });



       })
     } else {
       //TODO: input location
     }

    // TODO(DEVELOPER): push new message to Firebase.

      //////////////////////////////////////////


  }
};

// Signs-in Friendly Chat.
MeZone.prototype.signIn = function() {
  // Sign in Firebase using popup auth and Google as the identity provider.
  var provider = new firebase.auth.GoogleAuthProvider();
  this.auth.signInWithPopup(provider);
};

// Signs-out of Friendly Chat.
MeZone.prototype.signOut = function() {
  // Sign out of Firebase.
  this.auth.signOut();
};

// Triggers when the auth state change for instance when the user signs-in or signs-out.
MeZone.prototype.onAuthStateChanged = function(user) {
  if (user) { // User is signed in!
    // Get profile pic and user's name from the Firebase user object.
    //var location = ;   // TODO(DEVELOPER): Get profile pic.
    var userName = user.displayName;        // TODO(DEVELOPER): Get user's name.

    // Set the user's profile pic and name.
    //this.location =
    this.userName.textContent = userName;

    // Show user's profile and sign-out button.
    this.userName.removeAttribute('hidden');
    this.userLocation.removeAttribute('hidden');
    this.signOutButton.removeAttribute('hidden');

    // Hide sign-in button.
    this.signInButton.setAttribute('hidden', 'true');

    // Enable logging events
    this.logEventButton.removeAttribute('hidden');

    // We save the Firebase Messaging Device token and enable notifications.
    //this.saveMessagingDeviceToken();
  } else { // User is signed out!
    // Hide user's profile and sign-out button.
    this.userName.setAttribute('hidden', 'true');
    this.userLocation.setAttribute('hidden', 'true');
    this.signOutButton.setAttribute('hidden', 'true');

    // Show sign-in button.
    this.signInButton.removeAttribute('hidden');

    // No logging of events
    this.logEventButton.setAttribute('hidden', true);
  }
};

// Returns true if user is signed-in. Otherwise false and displays a message.
MeZone.prototype.checkSignedInWithMessage = function() {
  // Return true if the user is signed in Firebase
  if (this.auth.currentUser) {
    return true;
  }
  // Display a message to the user using a Toast.
/*  var data = {
    message: 'You must sign-in first',
    timeout: 2000
  };
  this.signInSnackbar.MaterialSnackbar.showSnackbar(data);*/
  window.alert("You must sign in first");

  return false;
};

// Enables or disables the submit button depending on the values of the input
// fields.
MeZone.prototype.toggleButton = function() {
  if (this.messageInput.value) {
    this.submitButton.removeAttribute('disabled');
  } else {
    this.submitButton.setAttribute('disabled', 'true');
  }
};

// Checks that the Firebase SDK has been correctly setup and configured.
MeZone.prototype.checkSetup = function() {
  if (!window.firebase || !(firebase.app instanceof Function) || !firebase.app().options) {
    window.alert('You have not configured and imported the Firebase SDK. ' +
        'Make sure you go through the codelab setup instructions and make ' +
        'sure you are running the codelab using `firebase serve`');
  }
};

MeZone.prototype.toggleHeatMap = function() {
  var heatmap = new google.maps.visualization.HeatmapLayer({
          data: this.events,
          map: map
        });
}

MeZone.prototype.loadEvents = function() {
  // Make sure we remove all previous listeners.
  this.meZoneEvents.off();

  // Loads the last 12 messages and listen for new ones.
  var setEvents = function(data) {
    data.forEach(function(childSnapshot) {
      var childKey = childSnapshot.key;
      var childData = childSnapshot.val();
      console.log(childData);
      console.log(childData.position);
      mezone.events.push(new google.maps.LatLng(childData.position.lat, childData.position.lng));

      var marker = new google.maps.Marker({
        position: new google.maps.LatLng(childData.position.lat, childData.position.lng),
        map: map,
        title:"teST!"
      });
    });
    this.toggleHeatMap();
  //  this.events.push(new google.maps.LatLng(val.position.lat, val.position.lng));
  }.bind(this);
  //this.meZoneEvents.limitToLast(20).on('child_added', setEvents);
  //this.meZoneEvents.limitToLast(20).on('child_changed', setEvents);
  //this.meZoneEvents.once('value').then(setEvents);
  this.meZoneEvents.orderByChild("uid").equalTo(this.auth.currentUser.uid).on("value", setEvents);
//  this.meZoneEvents.orderByChild("uid").on("value", setEvents);
  //this.meZoneEvents.on("value", setEvents);
  //this.database.ref('meZoneEvents').orderByChild("uid").equalTo(this.auth.currentUser.uid).on("value", setEvents);
}

window.onload = function() {
  window.mezone = new MeZone();
};
