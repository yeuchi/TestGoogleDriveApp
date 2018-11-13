# TestGoogleDriveApp
- learn to access google drive api.
- 4 attempts below

2 methods in MainActivity from below documentation + quickstart + demo code on Github
https://developers.google.com/drive/android/auth#connecting_and_authorizing_with_the_google_apis_java_client

## Method #1: Silent login (if ID token available)
https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignInClient#getGoogleSignInAccountFromIntent(android.content.Intent)
<img width="1220" alt="silent failed" src="https://user-images.githubusercontent.com/1282659/48357677-c6b70e80-e65e-11e8-98eb-9cbb3f81989d.png">

## Method #2: Prompt user to signOn OAuth2 instead of using API_KEY

<img width="200" src="https://user-images.githubusercontent.com/1282659/48357659-bc951000-e65e-11e8-9713-f5b60a76b7c8.jpg"><img width="1150" src="https://user-images.githubusercontent.com/1282659/48364463-19e48d80-e66e-11e8-9624-df6f6a3257f8.png">

## Method#3: Example code from Nobuoka
https://github.com/nobuoka/android-GoogleDriveSample/blob/master/GoogleDriveSample/src/main/java/info/vividcode/android/app/googledrivesample/MainActivity.java
Create service object ok and seeing authentication error.  Probably a configuration issue in my service ?
 <img width="987" alt="screen shot 2018-11-12 at 2 18 01 pm" src="https://user-images.githubusercontent.com/1282659/48374048-3fcb5b80-e689-11e8-8ab7-65328bb6b317.png">

## Method#4: Direct RESTful GET request with MY_API_KEY

Successful setting up Google Drive API service, below API Explorer demonstrate a GET request of files OK.
<img width="1412" alt="explorerok" src="https://user-images.githubusercontent.com/1282659/48358469-73de5680-e660-11e8-9d4f-5f2ca4cde44d.png">

Successful setup of API key as work around from failed OAuth2 (above method #2).
<img width="661" alt="api_key" src="https://user-images.githubusercontent.com/1282659/48358495-848ecc80-e660-11e8-98b9-a1ab66653412.png">

drive.files.list executed 4 minutes ago time to execute: 1435 ms
GET https://www.googleapis.com/drive/v3/files?key={YOUR_API_KEY} // having replaced with my actual key
<img width="668" alt="screen shot 2018-11-13 at 9 33 06 am" src="https://user-images.githubusercontent.com/1282659/48424042-68a22e00-e727-11e8-9423-8af01e93ffa7.png">
