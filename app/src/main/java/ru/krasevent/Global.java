package ru.krasevent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


/**
 * Created by Михаил on 03.08.2015.
 */
public class Global {

    /** URL сервера с API */
    public static String host = "http://10.0.2.2/KrasEvent/api/";
    /** уникальный ключ с помощью которого выполняются API */
    public static String APIkey = "29c3d379de43e28c5f2657db3b96eaf11f5d308a0ae3a2083913313ecaf630e8";
    public static Boolean isLogin = true;




    /** LOGS */
    public static String logD = "KrasEventD";


    /**
     * Отрисовка бокового меню
     *
     * @param activity активность на котрую надо нарисовать меню
     */
    public static Drawer.Result setUpNavigationDrawer(final ActionBarActivity activity, final Drawer.Result drawerResult)
    {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawer.Result resDrawer = null;
        resDrawer = new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                //.withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {


                                                       switch (drawerItem.getIdentifier()) {
                                                           /** Map */
                                                           case 1:
                                                               Intent intent = new Intent(activity, MapActivity.class);
                                                               activity.startActivity(intent);

                                                               break;

                                                           /** Login */
                                                           case 3:
                                                               activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new LoginFragment()).commit();
                                                               break;
                                                       }





                                   /*if (drawerItem instanceof Nameable) {
                                       Toast.makeText(activity, activity.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                                   }
                                   if (drawerItem instanceof Badgeable) {
                                       Badgeable badgeable = (Badgeable) drawerItem;
                                       if (badgeable.getBadge() != null) {
                                           // учтите, не делайте так, если ваш бейдж содержит символ "+"
                                           try {
                                               int badge = Integer.valueOf(badgeable.getBadge());
                                               if (badge > 0) {
                                                   drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                               }
                                           } catch (Exception e) {
                                               Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                           }
                                       }
                                   }*/
                                                   }
                                               }
                )
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_map).withIcon(FontAwesome.Icon.faw_globe).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_login).withIcon(FontAwesome.Icon.faw_sign_in).withIdentifier(3)
                ).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .build();
        return resDrawer;
    }

}

