package de.mide.android.hifimockup;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.mide.android.hifimockup.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        boolean navigateUpOk = NavigationUI.navigateUp(navController, _appBarKonfiguration);
        return navigateUpOk || super.onSupportNavigateUp();
    }

}