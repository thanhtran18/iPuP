package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import comp3350.iPuP.business.AccessUsers;
import comp3350.iPuP.objects.DAOException;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.hsqldb.lib.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeActivity extends Activity
{

    AccessUsers accessUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        copyMapToDevice();
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

    private void copyMapToDevice()
    {
        final String MAP_PATH = "tiles";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(MAP_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try
        {

            assetNames = assetManager.list(MAP_PATH);
            for (int i = 0; i < assetNames.length; i++)
            {
                assetNames[i] = MAP_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory.toString());
        }
        catch (IOException ioe)
        {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
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

            copyAssetsToDirectory(assetNames, dataDirectory.toString());

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        }
        catch (IOException ioe)
        {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, String directory) throws IOException
    {
        AssetManager assetManager = getAssets();

        for (String asset : assets)
        {
            String[] components = asset.split("/");
            String copyPath = directory + "/" + components[components.length - 1];

            File outFile = new File(copyPath);

            if (!outFile.exists())
            {
                InputStream in = assetManager.open(asset);
                FileUtils.copyInputStreamToFile(in, outFile);
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

    public ContextWrapper getHomeContext()
    {
        return new ContextWrapper(this);
    }
}
