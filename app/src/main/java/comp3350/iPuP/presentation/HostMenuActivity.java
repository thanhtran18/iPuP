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
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_menu);
        name = getIntent().getStringExtra(getResources().getString(R.string.extra_name));
    }

    public void buttonCreateOnClick(View v)
    {
        Intent hostMenuIntent = new Intent(HostMenuActivity.this, HostActivity.class);
        hostMenuIntent.putExtra(getResources().getString(R.string.extra_name), name);
        HostMenuActivity.this.startActivity(hostMenuIntent);
    }

    public void buttonViewOnClick(View v)
    {
        Intent hostViewIntent = new Intent(HostMenuActivity.this, HostViewActivity.class);
        hostViewIntent.putExtra(getResources().getString(R.string.extra_name), name);
        HostMenuActivity.this.startActivity(hostViewIntent);
    }
}
