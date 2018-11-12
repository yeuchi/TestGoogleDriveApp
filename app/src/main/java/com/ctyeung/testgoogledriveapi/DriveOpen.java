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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class DriveOpen extends AppCompatActivity {

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

        //TestDriveQuery();

    }



    /**
     * Prompt the user to choose the account to use and process the file using the
     * Drive file ID stored in mFileId.
     */
    private void getUserAccountAndProcessFile() {
        // Implement the method.
/*
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);
        credential.setSelectedAccountName(accountName);
        Drive service = new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
*/
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




}
