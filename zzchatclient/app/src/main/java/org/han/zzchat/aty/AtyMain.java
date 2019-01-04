package org.han.zzchat.aty;

import android.os.Bundle;
import android.os.PersistableBundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.WindowManager;

import org.han.zzchat.view.LayoutChats;
import org.han.zzchat.view.LayoutContacts;
import org.han.zzchat.view.LayoutMoments;

import org.han.zzchat.R;
import org.han.zzchat.adapter.AdapterMainViewPager;

import java.util.ArrayList;
import java.util.List;

public class AtyMain extends AppCompatActivity{
    private DrawerLayout drawable;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.i("atymain","??");
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.aty_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
    }

    private void initViews() {
        drawable = (DrawerLayout) findViewById(R.id.dl_main);
        viewPager= (ViewPager) findViewById(R.id.vp_main);
        tabLayout= (TabLayout) findViewById(R.id.tl_main);

        tabList=new ArrayList<>();

        AdapterMainViewPager adapter=new AdapterMainViewPager(getSupportFragmentManager());
        adapter.addFragment(new LayoutChats());
        adapter.addFragment(new LayoutContacts());
        adapter.addFragment(new LayoutMoments());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.get(0).setIcon(R.drawable.icon).setText("Chats");
        tabList.get(1).setIcon(R.drawable.icon).setText("Contacts");
        tabList.get(2).setIcon(R.drawable.icon).setText("Moments");
    }
}
