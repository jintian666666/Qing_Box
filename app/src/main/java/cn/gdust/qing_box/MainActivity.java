package cn.gdust.qing_box;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.fragment.ClassifyFragment;
import cn.gdust.qing_box.fragment.FavorFragment;
import cn.gdust.qing_box.fragment.MeFragment;
import cn.gdust.qing_box.utils.DarkModeUtil;
import cn.gdust.qing_box.utils.SwitchClickListener;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.imageMenu) ImageView imageView;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.bottomNavigationView) BottomNavigationView bottomNavigation;
    @BindView(R.id.view_pager) ViewPager2 viewPager;
    private LinearLayout linearLayout;
    private SwitchMaterial sw;


    DarkModeUtil darkModeUtil;



    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private static boolean isLogin = false; //是否登录

    /**
     * 动态获取存储权限
     * ---------------------------------------------
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
    //------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态获取存储权限，保存图片权限
        verifyStoragePermissions(this);

        ButterKnife.bind(this);
        linearLayout = (LinearLayout) navigationView.getMenu().findItem(R.id.menuMode).getActionView();
        sw = linearLayout.findViewById(R.id.darkModeSwitch);

        View headerLayout = navigationView.inflateHeaderView(R.layout.layout_navigation_header);
        //header点击事件
        headerLayout.setOnClickListener(view ->  {
            drawerLayout.closeDrawer(GravityCompat.START);
            bottomNavigation.setSelectedItemId(R.id.bnv_me);
        });


        darkModeUtil = new DarkModeUtil();
        darkModeUtil.init(this.getApplication());
//        darkModeUtil.applySystemMode(MainActivity.this);  //TODO 和init冲突bug，要解决更换DarkMode后不重新加载Activity
        if (darkModeUtil.isDarkMode(this)){
            sw.setChecked(true);
        }


        //--------------------------------------------------viewPager2-----------------------------------------------
        // 设置ViewPager适配器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        // 设置底部导航栏选项选择监听器
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_favor:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.bnv_classify:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.bnv_me:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // 设置ViewPager页面切换监听器
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.bnv_favor);
                        break;
                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.bnv_classify);
                        break;
                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.bnv_me);
                        break;
                }
            }
        });
        //--------------------------------------------------viewPager2-----------------------------------------------


        //点击ToolBar的菜单按钮也可将侧滑抽屉拉出
        imageView.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        //侧滑导航当图标是多色系时传参为空
        navigationView.setItemIconTintList(null);



        //侧边栏NavigationView的item点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuSearch:
                        startActivity(new Intent(MainActivity.this,SearchActivity.class));
                        return true;
                    case R.id.menuSync:
                        Toast.makeText(MainActivity.this, "收藏同步", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menuTheme:
                        Toast.makeText(MainActivity.this, "主题管理", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,ThemeActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menuMode:
                        new SwitchClickListener(MainActivity.this,item).darkMode(darkModeUtil,MainActivity.this);
                        return true;
                    case R.id.menuAbout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setTitle("关于轻Box")
                                .setMessage("版本：v1.1.0\n" +
                                        "开发者：LJT\n" +
                                        "开发时间：2022-10-20\n" +
                                        "App用途：GDUST毕业设计\n" +
                                        "联系作者QQ：847860221")
                                .setPositiveButton("GOT IT！",null)
                                .show();
                        return true;
                    case R.id.menuExit:
                        finish();
                        System.exit(0);
                }

                return false;
            }
        });

        //夜间模式Switch开关监听
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    darkModeUtil.applyNightMode(MainActivity.this);
                    bottomNavigation.setSelectedItemId(R.id.bnv_favor);
                }else {
                    darkModeUtil.applyDayMode(MainActivity.this);
                    bottomNavigation.setSelectedItemId(R.id.bnv_favor);
                }
            }
        });

    }




    //返回键监听：双击返回退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //双击返回退出程序
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次返回键退出",
                    Toast.LENGTH_SHORT).show();
            // 如果2s内不点击第二次返回则恢复false（子线程延时操作）
            new Thread(){
                @SneakyThrows
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isExit = false;
                }
            }.start();
        } else {
            finish();
            System.exit(0);
        }
    }


    // 自定义ViewPager适配器
    class MyAdapter extends FragmentStateAdapter {

        public MyAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FavorFragment();
                case 1:
                    return new ClassifyFragment();
                case 2:
                    return new MeFragment();
                default:
                    throw new IllegalArgumentException("Invalid position: " + position);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}