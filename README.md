# Casptone-AAD App

Casptone-AAD is an Android app that allows users to sign up and sign in to access Covid-19 statistics of all countries, and display the cases for a selected country. It also features a settings screen where users can select the country whose data they want to view. The app utilizes various Android components such as activities, fragments, services, content providers, and broadcast receivers.

## Key Features

- Sign up and sign in to access Covid-19 statistics.
- Bottom navigation with three menu items: Statistics, History, and Settings.
- Statistics UI displays a list of all Covid cases in all countries.
- History UI displays the cases regarding a country selected from the Settings UI.
- Settings UI displays the details of the logged-in user and a dropdown to select the country's data the user wants to see.

## Components

### Activities

- MainActivity: The main activity that contains the sign in/up fragments and the bottom navigation view and switches between the three fragments (Statistics, History, and Settings).

### Fragments

- Sign In: The fragment that displays the form to help the user create an account within the app.
- Sign Up: The fragment that displays the form that helps the user connect to his account.
- StatisticsFragment: The fragment that displays the list of all Covid cases in all countries.
- HistoryFragment: The fragment that displays the cases regarding a country selected from the Settings UI.
- SettingsFragment: The fragment that displays the details of the logged-in user and a dropdown to select the country's data the user wants to see.

### Service

- CountriesIntentService: A service that handles the API call to retrieve Covid data from the web API.
- HistoryIntentService: A service that handles the API call to retrieve Covid data from the web API.
- StatisticsIntentService: A service that handles the API call to retrieve Covid data from the web API.

### Content Provider

- CountryDBContentProvider: A content provider that stores and retrieves the list of countries from the web API.

### Broadcast Receiver

- CountriesBroadCastReceiver: A broadcast receiver that listens for changes in network connectivity and displays a toast message to the user.

## API

The app uses the [Covid-19 Smartable API](https://coronavirus-smartable.p.rapidapi.com/stats/v1/) to retrieve Covid data. The API returns data in JSON format.

## Dependencies

The app uses the following dependencies:

- Retrofit: A type-safe HTTP client for Android and Java.
- Gson: A Java serialization/deserialization library to convert Java Objects into JSON and back.
- Glide: An image loading and caching library.
- Material Components: A library that provides Material Design components and styles for Android apps.

## Installation

To install the app, clone the repository and open it in Android Studio. Build and run the app on an Android device or emulator.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://github.com/coursera-capstones/Capstone-AAD/blob/master/LICENSE)