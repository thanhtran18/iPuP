package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import comp3350.iPuP.business.AccessUsers;
import comp3350.iPuP.objects.DAOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends Activity
{

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
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

        try
        {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++)
            {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        }
        catch (IOException ioe)
        {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException
    {
        AssetManager assetManager = getAssets();

        for (String asset : assets)
        {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists())
            {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1)
                {
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
        if (!name.equals(""))
        {
            try
            {
                boolean userCreated = accessUsers.createUser(name);
                if (userCreated)
                {
                    toastNewUserCreated(name);
                }
                Intent parkerIntent = new Intent(HomeActivity.this, ParkerMenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                parkerIntent.putExtras(bundle);
                HomeActivity.this.startActivity(parkerIntent);
            }
            catch (DAOException daoe)
            {
                toastMessage(daoe.getMessage());
            }
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
        if (!name.equals(""))
        {
            try
            {
                boolean userCreated = accessUsers.createUser(name);
                if (userCreated)
                {
                    toastNewUserCreated(name);
                }

                Intent hostMenuIntent = new Intent(HomeActivity.this, HostMenuActivity.class);
                hostMenuIntent.putExtra(getResources().getString(R.string.extra_name), name);
                HomeActivity.this.startActivity(hostMenuIntent);
            }
            catch (DAOException daoe)
            {
                toastMessage(daoe.getMessage());
            }
        }
        else
        {
            ((EditText)findViewById(R.id.editTextName)).setHint(getResources().getString(R.string.home_enter_name));
            ((EditText)findViewById(R.id.editTextName)).setHintTextColor(getResources().getColor(R.color.colorWarning));
        }
    }

    public void toastNewUserCreated(String userID)
    {
        toastMessage("New User, '" + userID + "' successfully created!");
    }

    public void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
