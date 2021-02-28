# Aurora-Hackathon

The app focuses on utilizing several smartphone sensors to provide the data required for the goal of the app.

The app firstly introduces the user to the sign-in page.
Logging in introduces the welcome page of the app.
Tapping on the Report button initializes the next layout, where several permissions are prompted.
Namely these permissions are:
-Camera permission
-Location permission

The camera is accessed and a picture will be taken. Simultaneously the device extracts the current location of the user.
This is important for the report, to make it as easy as possible.

The data is then sent.
We have demonstrated that the data can be delievered successfully.
Our medium of choice was the JavaMail API, but it can be easily integrated for a database too.
