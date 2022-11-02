# Trip Explorer

Hotel & Restaurant booking application. This is an Android application built with Java, Firebase and Android Studio as University "Android Application Development" course project. I did this project by myself.

## Screenshots
![Login/Signup](/screenshots/login_signup.png)
![Create Account](/screenshots/create_account.png)
![Initial Profile]((/screenshots/initial_profile.png)
![City List](/screenshots/city_list.png)
![Hotel List](/screenshots/hotel_list.png)
![Book Now](/screenshots/hotel_booking_now.png)
![Booking Payment](/screenshots/booking_payment.png)
![Explore](/screenshots/explore_tab.png)

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
