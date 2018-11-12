package com.ctyeung.testgoogledriveapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class DriveOpen extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;

    /** DRIVE_OPEN Intent action. */
    private static final String ACTION_DRIVE_OPEN = "com.google.android.apps.drive.DRIVE_OPEN";
    /** Drive file ID key. */
    private static final String EXTRA_FILE_ID = "resourceId";

    /** Drive file ID. */
    private String mFileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_open);
        Context context = this.getBaseContext();

        // SUCCESS when result=0
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        final Intent intent = getIntent();
        final String action = intent.getAction();

        // Make sure the Action is DRIVE_OPEN.
        if (ACTION_DRIVE_OPEN.equals(action)) {
            // Get the Drive file ID.
            mFileId = intent.getStringExtra(EXTRA_FILE_ID);
            getUserAccountAndProcessFile();
        } else {
            // Unknown action.
            finish();
        }


        //mGoogleSignInClient = buildGoogleSignInClient();
        //signIn();
        //TestDriveQuery();
    }



    /**
     * Prompt the user to choose the account to use and process the file using the
     * Drive file ID stored in mFileId.
     */
    private void getUserAccountAndProcessFile() {
        // Implement the method.
        throw new UnsupportedOperationException(
                "The getUserAccountAndProcessFile method has not been implemented");
    }
    /*
        private void TestDriveQuery()
        {
            URL url = NetworkUtils.buildDriveQueryUrl();
            GithubQueryTask task = new GithubQueryTask();
            task.execute(url);
        }

        public class GithubQueryTask extends AsyncTask<URL, Void, String>
        {
            public GithubQueryTask()
            {

            }

            @Override
            protected String doInBackground(URL... urls) {
                URL searchUrl = urls[0];
                String githubSearchResults = null;
                try
                {
                    githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                return githubSearchResults;
            }

            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            protected void onPostExecute(String str)
            {
            }
        }
    */
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    protected void signIn()
    {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(), 0);
        /*

        Task<GoogleSignInAccount> task = mGoogleSignInClient.silentSignIn();

        if (task.isSuccessful()) {
            // There's immediate result available.
            GoogleSignInAccount signInAccount = task.getResult();
            //updateViewWithAccount(account);
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
                        //updateViewWithAccount(account);
                    } catch (Exception ex)
                    {
                        String errMsg = ex.toString();

                        // You can get from apiException.getStatusCode() the detailed error code
                        // e.g. GoogleSignInStatusCodes.SIGN_IN_REQUIRED means user needs to take
                        // explicit action to finish sign-in;
                        // Please refer to GoogleSignInStatusCodes Javadoc for details
                       // updateButtonsAndStatusFromErrorCode(apiException.getStatusCode());
                    }
                }
            });
        }*/

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
                    finish();
                    return;
                }

                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);

                if (getAccountTask.isSuccessful()) {
                    initializeDriveClient(getAccountTask.getResult());
                } else {
                    //Log.e(TAG, "Sign-in failed.");
                    finish();
                }
                break;
            case REQUEST_CODE_OPEN_ITEM:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    mOpenItemTaskSource.setResult(driveId);
                } else {
                    mOpenItemTaskSource.setException(new RuntimeException("Unable to open file"));
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
