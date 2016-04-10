package bg.ittalents.bookingtopia.controller.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.fragments.ReservationFragment;
import bg.ittalents.bookingtopia.controller.fragments.ShowReservationFragment;
import model.UserSessionManager;
import model.CalendarHelper;
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new UserSessionManager(this);

        if(session.isUserLoggedIn()) {
            if (isUser()) {
                byte[] image = udao.getAvatar(getLoggedId());
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar)).setImageBitmap(getCroppedBitmap(bmp));
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name)).setText(udao.getName(getLoggedId()));
                navigationView.getMenu().getItem(1).setVisible(false);
                navigationView.getMenu().getItem(2).setVisible(false);
            } else {
                byte[] image = cdao.getAvatar(getLoggedId());
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar)).setImageBitmap(getCroppedBitmap(bmp));
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name)).setText(cdao.getName(getLoggedId()));
                navigationView.getMenu().getItem(3).setVisible(false);
                navigationView.getMenu().getItem(4).setVisible(false);
            }
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.abstract_drawer, menu);

        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem updateProfile = menu.findItem(R.id.action_update_profile);

        if(this.getClass().equals(LogInActivity.class) || this.getClass().equals(RegisterUserActivity.class) || this.getClass().equals(RegisterCompanyActivity.class) ){
            logout.setVisible(false);
            updateProfile.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update_profile) {
            Intent intent;
            if(this.isUser()) {
                intent = new Intent(this, RegisterUserActivity.class);
                intent.putExtra("edit_mode", true);
            }
            else {
                intent = new Intent(this, RegisterCompanyActivity.class);
                intent.putExtra("edit_mode", true);
            }
            startActivity(intent);
        } else if (id == R.id.action_logout) {
            session.logoutUser();
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
            startActivity(new Intent(this, HomeActivity.class));
        } else if (id == R.id.nav_my_reservations) {
            ShowReservationFragment dialog = new ShowReservationFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", getLoggedId());
            dialog.setArguments(bundle);
            dialog.show(AbstractDrawerActivity.this.getFragmentManager(), "MyDialogFragment");

        } else if (id == R.id.nav_my_reviews) {

        } else if (id == R.id.nav_view_my_hotels) {
            Intent intent = new Intent(this, HotelListActivity.class);

            intent.putExtra("search", false);

            startActivity(intent);
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
        //HashMap<String, String> user = session.getUserDetails();
        session = new UserSessionManager(this);
        return Long.parseLong(session.getUserDetails().get(session.KEY_ID));
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zashto", "ne vliza");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zashto", "ne resumee");

    }
}
