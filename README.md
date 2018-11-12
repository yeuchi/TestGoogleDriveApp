# TestGoogleDriveApp
- learn to access google drive api.
- 3 attempts below

2 methods in MainActivity from below documentation + quickstart + demo code on Github
https://developers.google.com/drive/android/auth#connecting_and_authorizing_with_the_google_apis_java_client

Method #1: Silent login 
<img width="1220" alt="silent failed" src="https://user-images.githubusercontent.com/1282659/48357677-c6b70e80-e65e-11e8-98eb-9cbb3f81989d.png">

Method #2: Prompt user to signOn

<img width="200" src="https://user-images.githubusercontent.com/1282659/48357659-bc951000-e65e-11e8-9713-f5b60a76b7c8.jpg"><img src="https://user-images.githubusercontent.com/1282659/48357670-c3238780-e65e-11e8-8d1b-446495312790.png">


Method#3: 'new' documentation which requires metadata + app_id defined in manifest file.
https://developers.google.com/drive/android/java-client#set_mime_types_in_the_app_manifest

Please ping me if you want my app_id to test with.

Problem below ... action is NOT DRIVE_OPEN (specified in manifest)
<img width="990" alt="screen shot 2018-11-12 at 8 59 45 am" src="https://user-images.githubusercontent.com/1282659/48355611-07f8ef80-e65a-11e8-9568-be84ee41a09f.png">
