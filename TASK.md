## Android Home Test Assignment

Create an Android application that fetches data from the weather API and shows weather forecast. The
purpose of the test assignment is to show skills in:

- choosing suitable architecture to follow the separation of concerns principle
- following the best practices of modern Android development
- creating maintainable and reusable code base
- testing your application
- creating UI/UX

### API

- The app must be based on the Estonian four days
  forecast [endpoint](https://weather.aw.ee/api/estonia/forecast)
- [Api documentation](https://weather.aw.ee/swagger/index.html)

### Main tasks

1. On the main screen, show the weather forecast for the selected day: use all the available fields
   the API provides (day and night data).
2. Allow selecting the forecast date, or show the forecast for the next days below today's forecast.
3. Only for the current day, the API also returns more detailed data for different places in
   Estonia. Show the list of cities with current weather info (temperature range).
4. When a city in the list is clicked, show today's forecast info for that city. You can create the
   UX for that yourself: it can be on a separate screen, or replace the today's forecast on the main
   screen, etc.
5. App must work in portrait and landscape modes.

### Additional tasks (choose at least 3)

1. Cache the latest weather info and display it when the user opens the app with no internet
   connection.
2. Use animations in the UI.
3. Show icons for different weathers (Cloud icon for cloudy weather, etc..).
4. Display temperature info as words (23°C -> twenty three degrees).

### Submit information

1. Share your git repository.
2. Write what additional tasks were done and why.
