package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import comp3350.iPuP.business.AccessUsers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends Activity {

    AccessUsers accessUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        copyDatabaseToDevice();

        Main.startUp();

        setContentView(R.layout.activity_home);

        accessUsers = new AccessUsers();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void copyDatabaseToDevice()
    {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

//            System.out.println("First Asset Name: "+assetNames[0]);

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException
    {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    public void buttonParkerOnClick(View v)
    {
        String name = ((EditText)findViewById(R.id.editTextName)).getText().toString();
        if (name != null && !name.equals(""))
        {
            boolean userCreated = accessUsers.createUser(name);
            if (userCreated) { toastNewUserCreated(name); }
            Intent parkerIntent = new Intent(HomeActivity.this, AvailableParkingSpots.class);
            parkerIntent.putExtra("name", name);
            HomeActivity.this.startActivity(parkerIntent);
        }
        else
        {
            ((EditText)findViewById(R.id.editTextName)).setHint("Enter a name");
            ((EditText)findViewById(R.id.editTextName)).setHintTextColor(getResources().getColor(R.color.colorWarning));
        }
    }

    public void buttonHostOnClick(View v)
    {
        String name = ((EditText)findViewById(R.id.editTextName)).getText().toString();
        if (name != null && !name.equals(""))
        {
            boolean userCreated = accessUsers.createUser(name);
            if (userCreated) { toastNewUserCreated(name); }
            Intent hostMenuIntent = new Intent(HomeActivity.this, HostMenuActivity.class);
            hostMenuIntent.putExtra("name", name);
            HomeActivity.this.startActivity(hostMenuIntent);
        }
        else
        {
            ((EditText)findViewById(R.id.editTextName)).setHint("Enter a name");
            ((EditText)findViewById(R.id.editTextName)).setHintTextColor(getResources().getColor(R.color.colorWarning));
        }
    }

    public void toastNewUserCreated(String userID)
    {
        Toast.makeText(this, "New User, '" + userID + "' successfully created!", Toast.LENGTH_LONG).show();
    }
}
