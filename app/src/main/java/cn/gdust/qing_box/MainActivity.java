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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.fragment.AccountFragment;
import cn.gdust.qing_box.fragment.ClassifyFragment;
import cn.gdust.qing_box.fragment.TClassifyFragment;
import cn.gdust.qing_box.fragment.FavorFragment;
import cn.gdust.qing_box.fragment.MeFragment;
import cn.gdust.qing_box.utils.DarkModeUtil;
import cn.gdust.qing_box.utils.SwitchClickListener;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.imageMenu) ImageView imageView;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.bottomNavigationView) BottomNavigationView bottomNavigation;
    private LinearLayout linearLayout;
    private SwitchMaterial sw;

    //外部Fragment对象
    private FavorFragment favor;
    private TClassifyFragment Tclassify;
    private MeFragment me;
    private AccountFragment account;
    private ClassifyFragment classify;

    DarkModeUtil darkModeUtil;

    private Fragment[] fragments;
    //默认选择第一个fragment
    private int lastSelectedPosition = 0;

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


        //动态获取存储权限，保存二维码图片
        verifyStoragePermissions(this);

        ButterKnife.bind(this);
        linearLayout = (LinearLayout) navigationView.getMenu().findItem(R.id.menuMode).getActionView();
        sw = linearLayout.findViewById(R.id.darkModeSwitch);

        View headerLayout = navigationView.inflateHeaderView(R.layout.layout_navigation_header);
        headerLayout.setOnClickListener(view ->  {
            drawerLayout.closeDrawer(GravityCompat.START);
            bottomNavigation.setSelectedItemId(R.id.bnv_me);
        });

        favor = new FavorFragment();
        Tclassify = new TClassifyFragment();
        me = new MeFragment();
        account = new AccountFragment();
        classify = new ClassifyFragment();

        darkModeUtil = new DarkModeUtil();
        darkModeUtil.init(this.getApplication());
//        darkModeUtil.applySystemMode(MainActivity.this);  //和init冲突bug，要解决更换DarkMode后不重新加载Activity
        if (darkModeUtil.isDarkMode(this)){
            sw.setChecked(true);
        }

        fragments = new Fragment[]{favor,classify,me,account}; //将Fragment存进数组

        //点击ToolBar的菜单按钮也可将侧滑抽屉拉出
        imageView.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        //侧滑导航当图标是多色系时传参为空
        navigationView.setItemIconTintList(null);

        //应用开启显示的fragment
        initFragment(lastSelectedPosition,0);

        //BottomNavigationView点击监听器
        bottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()){
                case R.id.bnv_favor:
                    if (0 != lastSelectedPosition) {
                        initFragment(lastSelectedPosition, 0);
                        lastSelectedPosition = 0;
                    }
                    return true;
                case R.id.bnv_classify:
                    if (1 != lastSelectedPosition) {
                        initFragment(lastSelectedPosition, 1);
                        lastSelectedPosition = 1;
                    }
                    return true;
                case R.id.bnv_me:
                    if (2 != lastSelectedPosition) {
                        initFragment(lastSelectedPosition, 2);
                        lastSelectedPosition = 2;
                    }
                    return true;
            }
            return false;
        });

        //侧边栏NavigationView的item点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuAll:
                        Toast.makeText(MainActivity.this, "全部应用", Toast.LENGTH_SHORT).show();
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
                        bottomNavigation.setSelectedItemId(R.id.bnv_favor);
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


    /**
     * 加载（切换）Fragment
     * @param lastIndex 上个显示Fragment的索引
     * @param index 需要显示的Fragment的索引
     */
    private void initFragment(int lastIndex,int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        //判断该fragment是否已在栈内，如果无，则进栈
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.nav_container, fragments[index]);
        }
        //否则就从栈内取出展示并进行提交
        // commit：安排该事务的提交。这一承诺不会立即发生;它将被安排在主线程上，以便在线程准备好的时候完成。
        // commitAllowingStateLoss：与 commit类似，但允许在活动状态保存后执行提交。这是危险的，因为如果Activity需要从其状态恢复，那么提交就会丢失。
        // 因此，只有在用户可以意外地更改UI状态的情况下，才可以使用该commitAllowingStateLoss提交。
        transaction.show(fragments[index]).commit();
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
                    Thread.sleep(2000);
                    isExit = false;
                }
            }.start();
        } else {
            finish();
            System.exit(0);
        }
    }



}