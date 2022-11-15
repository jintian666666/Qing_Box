package cn.gdust.qing_box.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.Fragment.ClassifyFragment;
import cn.gdust.qing_box.Fragment.FavorFragment;
import cn.gdust.qing_box.Fragment.MeFragment;
import cn.gdust.qing_box.R;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.imageMenu)
    ImageView imageView;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigation;

    private ClassifyFragment classify;
    private MeFragment me;

    private Fragment[] fragments;
    //默认选择第一个fragment
    private int lastSelectedPosition = 0;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FavorFragment favor = new FavorFragment();
        classify = new ClassifyFragment();
        me = new MeFragment();
        fragments = new Fragment[]{favor,classify,me}; //将Fragment存进数组

        //点击ToolBar的菜单按钮也可将侧滑抽屉拉出
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //侧滑导航当图标是多色系时传参为空
        navigationView.setItemIconTintList(null);

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