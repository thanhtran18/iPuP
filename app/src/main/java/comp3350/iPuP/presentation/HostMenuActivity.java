package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HostMenuActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_menu);
    }

    public void buttonCreateOnClick(View v)
    {
        Intent hostMenuIntent = new Intent(HostMenuActivity.this, HostActivity.class);
        HostMenuActivity.this.startActivity(hostMenuIntent);
    }

    public void buttonViewOnClick(View v)
    {
        Intent hostViewIntent = new Intent(HostMenuActivity.this, HostViewActivity.class);
        HostMenuActivity.this.startActivity(hostViewIntent);
    }
}
