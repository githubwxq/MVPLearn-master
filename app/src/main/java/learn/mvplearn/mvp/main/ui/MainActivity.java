package learn.mvplearn.mvp.main.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.OnClick;
import learn.mvplearn.R;
import learn.mvplearn.base.MyBaseActivity;
import learn.mvplearn.mvp.main.presenter.MainParsenterImpl;
import learn.mvplearn.mvp.main.view.MainView;
import learn.mvplearn.mvp.news.ui.NewsFragment;

public class MainActivity extends MyBaseActivity<MainParsenterImpl> implements MainView {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.frame_content)
    FrameLayout frameContent;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwitchItem(R.id.nav_news);
        init();

    }

    private void init() {
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        navView.setCheckedItem(R.id.nav_news);
        setupDrawerContent(navView);



    }

    @Override
    public MainParsenterImpl createPresenter() {
        return new MainParsenterImpl(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    public void SwitchItem(int itemId) {
        switch (itemId) {
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsFragment()).commit();// 重新初始化了所以回到了1
                toolbar.setTitle(R.string.navigation_news);
                break;

        }




    }

    @OnClick({R.id.toolbar, R.id.frame_content, R.id.nav_view, R.id.drawer_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.frame_content:
                break;
            case R.id.nav_view:
                break;
            case R.id.drawer_layout:
                break;
        }
    }

    public void setupDrawerContent(NavigationView navigationView) {  //点击食人族
     navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             Presente.switchNavigation(item.getItemId());

//             if (item.getItemId() == R.id.nav_setting || item.getItemId() == R.id.nav_share) {
//
//             } else {
//                 item.setChecked(true);
//             }
             drawerLayout.closeDrawers();
             return true;
         }
     });




    }


//    @Override
//    public void widgetClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.tv_goto:
//                showToast("跳转");
//                MainActivity.this.startActivity(new Intent(MainActivity.this, MyBaseActivity.class));
//                break;
//        }
//    }
}
