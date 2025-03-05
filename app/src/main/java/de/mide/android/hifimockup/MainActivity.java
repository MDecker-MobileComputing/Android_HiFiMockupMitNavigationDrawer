package de.mide.android.hifimockup;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.mide.android.hifimockup.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    /** URL zur "Homepage" der App. */
    final static String HOMEPAGE_URL =
            "https://github.com/MDecker-MobileComputing/Android_HiFiMockupMitNavigationDrawer?tab=readme-ov-file#hifi-mockup";

    public static final String TAG4LOGGING = "HiFiMockup";

    private AppBarConfiguration _appBarKonfiguration;
    private ActivityMainBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        _binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        setSupportActionBar(_binding.appBarMain.toolbar);

        DrawerLayout drawer = _binding.drawerLayout;

        _appBarKonfiguration =
                new AppBarConfiguration.Builder(
                        R.id.nav_home,
                        R.id.nav_balkendiagramm,
                        R.id.nav_kuchendiagramm
                )
                .setOpenableLayout(drawer)
                .build();

        NavigationView navigationView = _binding.navView;

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, _appBarKonfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        boolean navigateUpOk = NavigationUI.navigateUp(navController, _appBarKonfiguration);
        return navigateUpOk || super.onSupportNavigateUp();
    }

    /**
     * "Drei-Punkte-Menü" (Overflow-Menü) in der App-Leiste (rechts oben) erzeugen.
     *
     * Das Event-Handling für die Einträge wird in der Methode
     * {@link #onOptionsItemSelected(MenuItem)} implementiert.
     *
     * @param menu  Menü-Objekt, zu dem die Einträge hinzugefügt werden sollen.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Verarbeitung von Klicks in "Drei-Punkte-Menü" (Overflow-Menü).
     *
     * @param item  Menu-Item, welches gerade ein Event ausgelöst hat.
     *
     * @return Es wird {@code true} zurückgegeben, wenn wir in dieser
     *          Methode das Ereignis verarbeiten konnten, ansonsten
     *          der Wert der Super-Methode.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int selectedMenuId = item.getItemId();

        if (selectedMenuId == R.id.action_ueber) {

            zeigeAppVersionDialog();
            return true;

        } else if (selectedMenuId == R.id.action_webseite) {

            oeffneWebseite();
            return true;

        } else {

            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Event-Handler für Eintrag im Overflow-Menü:
     * Zeigt einen Dialog mit der aktuellen App-Version an.
     */
    private void zeigeAppVersionDialog() {

        String versionnameAusPackageInfo = "???";

        try {

            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionnameAusPackageInfo = packageInfo.versionName;

        } catch ( PackageManager.NameNotFoundException e ) {

            Log.e( TAG4LOGGING, "Fehler beim Ermitteln der App-Version: " + e.getMessage() );
        }

        String nachricht = "Version der App: " + versionnameAusPackageInfo;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Über diese App");
        builder.setMessage(nachricht);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    /**
     * Event-Handler für Eintrag im Overflow-Menü:
     * Öffnet Webseite der App in einer auf dem Gerät installierten Browser-App.
     */
    private void oeffneWebseite() {

        Uri uri = Uri.parse(HOMEPAGE_URL);

        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData( uri );

        startActivity( intent );
    }

}