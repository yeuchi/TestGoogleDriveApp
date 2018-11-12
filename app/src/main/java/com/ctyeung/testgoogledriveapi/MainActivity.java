package com.ctyeung.testgoogledriveapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;


import android.content.Context;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.common.GooglePlayServicesUtil;

/*
 * https://developers.google.com/drive/android/java-client#set_mime_types_in_the_app_manifest
*/
public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private Activity mActvity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getBaseContext();
        mActvity = this;

        /*
         * https://developers.google.com/drive/android/auth#connecting_and_authorizing_with_the_google_apis_java_client
         * Try signIn()
         * -> if failed, try 2nd method signIn2()
         */

        mGoogleSignInClient = buildGoogleSignInClient();

        signIn();

        /*
         * https://developers.google.com/drive/android/java-client#set_mime_types_in_the_app_manifest
         *  new method
         */

        /*
        Intent intent = new Intent(mContext, DriveOpen.class);
        intent.setAction("com.google.android.apps.drive.DRIVE_OPEN");
        startActivity(intent);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    protected void signIn()
    {
        Task<GoogleSignInAccount> task = mGoogleSignInClient.silentSignIn();

        if (task.isSuccessful()) {
            // There's immediate result available.
            GoogleSignInAccount signInAccount = task.getResult();
            //updateViewWithAccount(account);

            Toast.makeText(mActvity,
                    "signIn task.isSuccessful: ",
                    Toast.LENGTH_LONG).show();
        }
        else
            {
            // There's no immediate result ready, displays some progress indicator and waits for the
            // async callback.
            //showProgressIndicator();
            task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(Task task) {
                    try {
                      //  hideProgressIndicator();

                       // GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
                        Object object = task.getResult();
                        String msg = object.toString();

                        Toast.makeText(mActvity,
                                "signIn Success: " +msg,
                                Toast.LENGTH_LONG).show();

                        //updateViewWithAccount(account);
                    } catch (Exception ex)
                    {
                        String errMsg = ex.toString();

                        Toast.makeText(mActvity,
                                "signIn Failed: " +errMsg,
                                Toast.LENGTH_LONG).show();

                        signIn2();

                        // You can get from apiException.getStatusCode() the detailed error code
                        // e.g. GoogleSignInStatusCodes.SIGN_IN_REQUIRED means user needs to take
                        // explicit action to finish sign-in;
                        // Please refer to GoogleSignInStatusCodes Javadoc for details
                       // updateButtonsAndStatusFromErrorCode(apiException.getStatusCode());
                    }
                }
            });
        }

    }

    /**
     * Request code for Google Sign-in
     */
    protected static final int REQUEST_CODE_SIGN_IN = 0;

    /**
     * Request code for the Drive picker
     */
    protected static final int REQUEST_CODE_OPEN_ITEM = 1;

    /**
     * Handles high-level drive functions like sync
     */
    private DriveClient mDriveClient;

    /**
     * Handle access to Drive resources/files.
     */
    private DriveResourceClient mDriveResourceClient;

    /**
     * Tracks completion of the drive picker
     */
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;


    /*
     * Try this only if signIn failed
     */
    protected void signIn2()
    {

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        //.requestScopes(Drive.SCOPE_FILE)
                       // .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(), 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    // Sign-in may fail or be cancelled by the user. For this sample, sign-in is
                    // required and is fatal. For apps where sign-in is optional, handle
                    // appropriately
                    // Log.e(TAG, "Sign-in failed.");

                    Toast.makeText(mActvity,
                            "signIn2 Failed - resultCode: " +resultCode,
                            Toast.LENGTH_LONG).show();

                    finish();
                    return;
                }

                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);

                if (getAccountTask.isSuccessful()) {
                    initializeDriveClient(getAccountTask.getResult());

                    Toast.makeText(mActvity,
                            "signIn2 -> getSignedInAccountFromIntent Success ",
                            Toast.LENGTH_LONG).show();

                } else {
                    //Log.e(TAG, "Sign-in failed.");

                    Toast.makeText(mActvity,
                            "signIn2 -> getSignedInAccountFromIntent Failed ",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case REQUEST_CODE_OPEN_ITEM:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    mOpenItemTaskSource.setResult(driveId);

                    Toast.makeText(mActvity,
                            "Request code open item -> Success",
                            Toast.LENGTH_LONG).show();

                } else {
                    mOpenItemTaskSource.setException(new RuntimeException("Unable to open file"));

                    Toast.makeText(mActvity,
                            "Request code open item -> Unable to open file",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Continues the sign-in process, initializing the Drive clients with the current
     * user's account.
     */
    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        mDriveClient = Drive.getDriveClient(getApplicationContext(), signInAccount);
        mDriveResourceClient = Drive.getDriveResourceClient(getApplicationContext(), signInAccount);
        //onDriveClientReady();
    }


    protected DriveClient getDriveClient() {
        return mDriveClient;
    }

    protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }
}
