# Trip Explorer

Hotel & Restaurant booking application. This is an Android application built with Java, Firebase and Android Studio as University "Android Application Development" course project. I did this project by myself.

## Screenshots
<p align="middle" float="left">
  <img src="/screenshots/login_signup.png" width="32%" />
  <img src="/screenshots/create_account.png" width="32%" /> 
  <img src="/screenshots/initial_profile.png" width="32%" />
</p>
<p align="middle" float="left">
  <img src="/screenshots/city_list.png" width="32%" />
  <img src="/screenshots/hotel_list.png" width="32%" /> 
  <img src="/screenshots/hotel_book_now.png" width="32%" />
</p>
<p align="middle" float="left">
  <img src="/screenshots/booking_info.png" width="32%" />
  <img src="/screenshots/booking_payment.png" width="32%" />
  <img src="/screenshots/profile_later.png" width="32%" /> 
</p>
<p align="middle" float="left">
  <img src="/screenshots/exploree_tab.png" width="32%" />
  <img src="/screenshots/credits.png" width="32%" />
</p>

## Features
- Account creation with email address with options to upload profile picture and cover photo (facebook inspired). Firebase Authentication was used to create and authenticate users.
- I made by own API using Firebase Realtime Database and Firebase Storage for Hotel & Restaunt information because at that time I was not able to find any free-to-use Hotel APIs that had local Bangladeshi Hotels and Restaurants.
- Users first select a city and it loads all the hotels and restaurants of that city in a card form consisting of image, price and ratings. Every Hotel has its own description and other information based on Wikipedia
- Detailed mock booking phase starts upon clicking "book now" button with inqueries about room type, stay duration, etc. and ability to add card information for payment. Users can save card information in their profile for faster booking. Saved cards and Booked hotels appear in user's profile.
- Ability to mark hotels as favoruites which will appear in the Favoruites tab in navigation bar.
- Everything in the profile can be modified.
- Explore tab in navigation bar shows user all the historical places of selected city along with their history.

## Technologies Used
- Firebase Authentiation
- Firebase Realtime Database
- Firebase Storage
- Java
- Android Studio
