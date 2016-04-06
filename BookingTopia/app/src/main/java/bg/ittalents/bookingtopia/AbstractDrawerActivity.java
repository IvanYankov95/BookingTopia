package bg.ittalents.bookingtopia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import model.User;
import model.UserSessionManager;
import model.dao.CompanyDAO;
import model.dao.UserDAO;

public class AbstractDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UserSessionManager session;
    UserDAO udao = UserDAO.getInstance(this);
    CompanyDAO cdao = CompanyDAO.getInstance(this);

    protected void onCreateDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Goals Tracker");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new UserSessionManager(this);


        if(session.isUserLoggedIn()) {
            if (isUser()) {
                byte[] image = udao.getAvatar(getLoggedId());
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar)).setImageBitmap(bmp);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name)).setText(udao.getName(getLoggedId()));
                navigationView.getMenu().getItem(1).setVisible(false);
                navigationView.getMenu().getItem(2).setVisible(false);
            } else {
                byte[] image = cdao.getAvatar(getLoggedId());
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar)).setImageBitmap(bmp);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name)).setText(cdao.getName(getLoggedId()));
                navigationView.getMenu().getItem(3).setVisible(false);
                navigationView.getMenu().getItem(4).setVisible(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.abstract_drawer, menu);


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_hotel) {
            startActivity(new Intent(this, CreateHotelActivity.class));
        } else if (id == R.id.nav_home) {
            startActivity(new Intent(this, SearchResultActivity.class));
        } else if (id == R.id.nav_my_reservations) {

        } else if (id == R.id.nav_my_reviews) {

        } else if (id == R.id.nav_view_my_hotels) {
            startActivity(new Intent(this, SearchResultActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isUser(){
        HashMap<String, String> user = session.getUserDetails();
        String s =user.get(session.IS_USER);
       return  s.equals("true");
    }

    public long getLoggedId(){
        HashMap<String, String> user = session.getUserDetails();
        return Long.parseLong(user.get(session.KEY_ID));
    }


}
