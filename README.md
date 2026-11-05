Vhuphara and Vhudzivha Tech Support Android Application
1. Project Overview
The Vhuphara and Vhudzivha Tech Support Android application is a Kotlin-based mobile application developed to help users access IT support services. It provides a central place for users to explore services, submit service bookings, and track the status of their requests.

The project was developed as part of the Programming of Excellence (PoE) assessment. The application connects to a backend API for supported service operations and includes media upload functionality for images and videos.
2. Project Objectives
The main objectives are to:
- Make IT support services easier to access from an Android device.
- Allow users to book a service and view service request details.
- Allow users to check the status of a service request.
- Connect the Android application to a backend API.
- Support image and video uploads where required.
- Provide a clear, simple, and user-friendly mobile interface.
3. Main Features
The application includes the following screens and functions. Only features implemented and tested in the submitted version should be described as complete.
- Book Service: allows a user to submit a service booking.
- IT Services: provides access to available IT service information.
- Track My Service: retrieves and displays service request details and status using a booking ID.
- Notifications: provides a place for application notifications.
- Messages: provides a messaging area.
- Rewards and Reviews: provides a section for rewards and reviews.
- Settings: provides application settings.
- Media Uploads: supports uploading images and videos to Cloudinary.
- Backend API Integration: uses Retrofit to communicate with the backend API.
Authentication, offline access, real-time notifications, multilingual support, and other advanced features should only be claimed if they are present and have been tested in the final version.
4. Technologies Used
- Kotlin: Android application programming language.
- Android Studio: development environment.
- Gradle: build automation and dependency management.
- Retrofit: communication between the Android application and the backend API.
- OkHttp: HTTP client support and request logging.
- Node.js and Express: backend API technology.
- PostgreSQL: relational database used by the backend.
- Cloudinary: image and video upload hosting.
- Firebase: Firebase services and dependencies configured for the project, where used by the application.
- Git and GitHub: source control and project hosting.
- GitHub Actions: automated build workflow.
5. System Design and Design Considerations
The application uses an Android front end and a backend API. The Android application sends supported requests to the API, and the API communicates with the database when required.

The main design considerations were:
- Usability: keep navigation and service-related tasks understandable.
- Maintainability: organise the application into separate activities and supporting classes.
- Connectivity: use an API client to communicate with the backend.
- Media handling: use Cloudinary for supported image and video uploads.
- Error handling: provide appropriate feedback when requests fail or data cannot be retrieved.
- Security: do not publish passwords, API secrets, signing keys, or private configuration files in the repository.
- Testing: verify important application flows before submitting the final build.
6. How to Run the Application
1. Install Android Studio.
2. Clone this repository or open the repository URL in Android Studio.
3. Allow Android Studio to synchronise the Gradle project.
4. Configure any required API endpoint and service credentials locally. Do not commit private credentials.
5. Ensure the backend API and database are available if testing features that depend on them.
6. Run the application on a compatible Android emulator or physical device.
7. Test the implemented features and confirm that backend-dependent functions can connect successfully.
The application uses the package name com.vhuphara.techsupport.
7. Backend and Data Storage
The Android application communicates with a backend API for supported service operations. The backend uses Node.js and Express, with PostgreSQL for relational data storage.

Media uploads are configured to use Cloudinary. Uploaded media is hosted by Cloudinary, while the application can use the returned secure URL where the implementation requires it.

For the PoE demonstration, show the actual records and data stored by the running application. Do not display passwords, private tokens, database credentials, or other secrets.
8. Testing
Testing should cover the main features available in the submitted version, including:
- Launching the application.
- Navigating between the main screens.
- Submitting a service booking.
- Retrieving service details and status using a valid booking ID.
- Checking the response to invalid input or unavailable API connections.
- Uploading an image or video, if enabled in the submitted build.
- Checking the backend response and corresponding database records.
- Installing and opening the signed release APK on a compatible Android device.
Record the actual results of each test. Do not report a test as passed unless it has been performed.
9. GitHub and Version Control
GitHub is used to host the project's source code and maintain a history of changes. Changes should be committed regularly with clear commit messages so that development progress can be reviewed.

The final submission version is identified by the Git tag final-PoE and the GitHub release Final PoE Submission, where available.

Repository: https://github.com/vhudzivha/VhupharaVhudzivhaTechSupport
10. GitHub Actions
The repository includes a GitHub Actions workflow at .github/workflows/android-build.yml. The workflow is intended to run an automated Android build when changes are pushed to the main branch or a pull request targets that branch.

The workflow is still being checked. Confirm a successful workflow run in the Actions tab before reporting that the automated build passes. The locally built signed APK is separate from the GitHub Actions build.
11. Screenshots and Publication Preparation
The repository contains application evidence, including:
- APK RELEASE .png
- SCREEN SHOTS .pdf
- Additional screenshot evidence uploaded to the repository.
The signed release APK was prepared for installation and testing. The app is not described as published on the Google Play Store. Add Google Play Console upload or publication evidence only if the app is actually uploaded there.

If additional screenshots are required, include clear images of the main screen, booking flow, service tracking, media upload, and other implemented features. Remove personal information and secrets from screenshots.
12. Release Notes
Final PoE Submission
Updates and features included in the final submission version:
- Kotlin-based Android application source code.
- Service booking and service status tracking functionality.
- Backend API integration.
- Image and video upload functionality.
- Application screenshots and project documentation.
- Signed release APK prepared for installation and testing.
- GitHub release identifying the final PoE version.
- GitHub Actions workflow added for automated Android build checks. The workflow result must be verified separately.
13. Demonstration Video
Video link: ADD YOUR VIDEO LINK HERE

The demonstration video will show the implemented application features, explain the user journey, and demonstrate relevant backend API and database data. It should include voice-over narration and avoid showing passwords, access tokens, or other confidential information.

Replace the placeholder above with the shareable video URL after recording and uploading the final demonstration.
14. AI Use Statement
AI tools were used as a support resource during parts of the project, such as planning, troubleshooting, and improving documentation. The developer is responsible for reviewing the suggestions, understanding the submitted work, testing the application, and ensuring that the final submission accurately represents the implemented solution.

Add the specific AI tools used and any required citations according to the institution's AI-use and referencing rules. Update this statement to match the actual assistance used during development.
15. Author and Project
Project: Vhuphara and Vhudzivha Tech Support Android Application
Company: Vhuphara and Vhudzivha Innovations Tech (PTY LTD)
Platform: Android
Language: Kotlin
Package name: com.vhuphara.techsupport

you tube video demostastration 
https://youtu.be/dA3suOXca3M

