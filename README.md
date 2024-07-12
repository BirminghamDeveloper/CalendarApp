# CalendarApp

CalendarApp is an Android application that helps users manage their appointments and events. The app provides an easy-to-use interface to add, update, delete, and view appointments and events. It uses modern Android development practices, including MVVM architecture, Room database, Coroutines, and Navigation components.

## Features

**Modern Architecture:** Built using the MVVM (Model-View-ViewModel) architecture for better separation of concerns and easier maintenance.
- **Manage Appointments:** Add, update, delete, and view your appointments with ease.
- **Event Management:** Keep track of all your events, separate from appointments.
- **Search Functionality:** Quickly find appointments and events by title or name.
- **User-Friendly Navigation:** Seamlessly switch between appointments and events using the bottom navigation bar.
- **Persistent Storage:** All data is stored locally using Room database, ensuring your appointments and events are always available.
- 
- **Asynchronous Operations:** Leverages Kotlin Coroutines for smooth and responsive UI interactions.
- **View Appointments and Events:** Users can view all their appointments and events in separate screens.
- **Add Appointments and Events:** Users can add new appointments and events.
- **Update Appointments and Events:** Users can edit existing appointments and events.
- **Delete Appointments and Events:** Users can delete unwanted appointments and events.
- **Search Functionality:** Users can search for appointments and events by title or name.
- **Navigation:** Bottom navigation bar to switch between appointments and events.
- **MVVM Architecture:** The app follows the MVVM architecture to separate concerns and improve testability.
- **Room Database:** Local database storage using Room to persist data.
- **Coroutines:** Asynchronous operations handled using Kotlin Coroutines.

## Screenshots

![Calendar1](https://github.com/user-attachments/assets/b3f47d3f-60b2-45e3-a5a9-5d149d3ef7b5)

![Calendar2](https://github.com/user-attachments/assets/a7f3b0fc-f681-44f3-8a88-8e6f37547860)


## Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/yourusername/CalendarApp.git
    ```
2. **Open the project in Android Studio:**
    - File -> Open -> Select the project directory

3. **Build the project:**
    - Click on the `Build` menu and select `Make Project` or use the shortcut `Ctrl+F9`.

4. **Run the app:**
    - Click on the `Run` button or use the shortcut `Shift+F10`.

## Architecture

The app follows the MVVM architecture pattern:

- **Model:** Represents the data layer of the application. It includes the Room database entities and DAOs.
- **View:** Represents the UI layer. It includes Activities, Fragments, and XML layouts.
- **ViewModel:** Acts as a bridge between the Model and the View. It holds UI-related data and handles business logic.

## Project Structure

com.birminghamdeveloper.calendarapp
├── db
│ ├── AppointmentDao.kt
│ ├── EventDao.kt
│ ├── AppDatabase.kt
├── model
│ ├── Appointment.kt
│ ├── Event.kt
├── repo
│ ├── AppointmentRepository.kt
│ ├── EventRepository.kt
├── ui
│ ├── AppointmentsFragment.kt
│ ├── EventsFragment.kt
│ ├── MainActivity.kt
├── viewmodel
│ ├── CalendarViewModel.kt
├── res
│ ├── layout
│ │ ├── activity_main.xml
│ │ ├── fragment_appointments.xml
│ │ ├── fragment_events.xml
│ ├── menu
│ │ ├── bottom_nav_menu.xml
│ ├── navigation
│ ├── nav_graph.xml

## Dependencies

- **Kotlin:** [kotlin-stdlib](https://github.com/JetBrains/kotlin)
- **AndroidX:** [core-ktx](https://developer.android.com/jetpack/androidx/releases/core), [appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
- **Material Components:** [material](https://github.com/material-components/material-components-android)
- **ConstraintLayout:** [constraintlayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout)
- **Lifecycle:** [lifecycle-livedata-ktx](https://developer.android.com/jetpack/androidx/releases/lifecycle-livedata-ktx), [lifecycle-viewmodel-ktx](https://developer.android.com/jetpack/androidx/releases/lifecycle-viewmodel-ktx)
- **Navigation:** [navigation-fragment-ktx](https://developer.android.com/jetpack/androidx/releases/navigation-fragment-ktx), [navigation-ui-ktx](https://developer.android.com/jetpack/androidx/releases/navigation-ui-ktx)
- **Room:** [room-runtime](https://developer.android.com/jetpack/androidx/releases/room-runtime), [room-ktx](https://developer.android.com/jetpack/androidx/releases/room-ktx)
- **Coroutines:** [kotlinx-coroutines-core](https://github.com/Kotlin/kotlinx.coroutines), [kotlinx-coroutines-android](https://github.com/Kotlin/kotlinx.coroutines)

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for new features, please open an issue or create a pull request.

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

If you have any questions or feedback, feel free to contact me:

- GitHub: [Birmingham Developer](https://github.com/birminghamdeveloepr)
- Email: m.sharif.uk@gmail.com



