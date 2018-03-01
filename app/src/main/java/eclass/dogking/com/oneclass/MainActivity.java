package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.Adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.Fragments.*;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.CircleTransformation;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_title)
    TextView maintitle;
    @BindView(R.id.id_main)
    ImageButton mainImg;
    @BindView(R.id.id_class)
    ImageButton classImg;
    @BindView(R.id.id_cb)
    ImageButton cbImg;
    @BindView(R.id.id_me)
    ImageButton meImg;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.nav_view)
    NavigationView nav_view;


    private ImageView imageView;
    private TextView nav_name;
    private TextView nav_description;
    private String tel;
    private FragAdapter mAdapter;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        //取得登录用户的电话
        if (intent != null) {
            tel = intent.getStringExtra("tel");
        }

        //Toast.makeText(this,tel,Toast.LENGTH_SHORT).show();
        initToggle();
        initView();
        initEvents();
        receive(tel);
    }
    private void initToggle(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    private void initEvents(){
        //navigationview的menu点击事件
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_1:
                        Intent intent=new Intent(MainActivity.this,Tab4_1Activity.class);
                        intent.putExtra("tel",tel);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                    break;
                    case R.id.nav_2:
                        Intent intent2=new Intent(MainActivity.this,Tab4_5Activity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent2.putExtra("tel",tel);
                        startActivity(intent2);
                    break;
                    case R.id.nav_3:
                        Intent intent3=new Intent(MainActivity.this,Tab4_3Activity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent3.putExtra("tel",tel);
                        startActivity(intent3);
                     break;
                    case R.id.nav_4:
                        Intent intent4=new Intent(MainActivity.this,Tab4_4Activity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent4.putExtra("tel",tel);
                        startActivity(intent4);
                    break;
                    case R.id.nav_5:
                        Intent intent5=new Intent(MainActivity.this,Tab4_6Activity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent5.putExtra("tel",tel);
                        startActivity(intent5);
                    break;
                    case R.id.nav_6:
                        Intent intent6=new Intent(MainActivity.this,PasswordActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent6.putExtra("tel",tel);
                        startActivity(intent6);
                     break;
                    case R.id.nav_7:
                        Intent intent7=new Intent(MainActivity.this,LoginActivity.class);
                        MainActivity.this.finish();
                        startActivity(intent7);
                    break;
                    case R.id.nav_8:
                        MainActivity.this.finish();
                    break;
                }
                return true;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {//当viewpager滑动时，对应的底部导航按钮的图片要变化
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem) {
                    case 0:
                        maintitle.setText("主页");
                        mainImg.setImageResource(R.mipmap.main_on);
                        break;
                    case 1:
                        maintitle.setText("课程");
                        classImg.setImageResource(R.mipmap.class_on);
                        break;
                    case 2:
                        maintitle.setText("课表");
                        cbImg.setImageResource(R.mipmap.cb_on);
                        break;
                    case 3:
                        maintitle.setText("我的");
                        meImg.setImageResource(R.mipmap.me_on);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initView() {
        View headerView=nav_view.getHeaderView(0);
        nav_name=(TextView)headerView.findViewById(R.id.nav_head_name);
        nav_description=(TextView)headerView.findViewById(R.id.nav_head_description);
        imageView=(ImageView)headerView.findViewById(R.id.imageView);
        // 将要分页显示的View装入数组中
        List<Fragment> list = new ArrayList<Fragment>();

        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        list.add(new Fragment4());

        mAdapter = new FragAdapter(getSupportFragmentManager(), list);
        //为ViewPager设置adapter
        mViewPager.setAdapter(mAdapter);
        nav_view.setItemIconTintList(null);

    }

    private void resetImg() {
        mainImg.setImageResource(R.mipmap.main_off);
        classImg.setImageResource(R.mipmap.class_off);
        cbImg.setImageResource(R.mipmap.cb_off);
        meImg.setImageResource(R.mipmap.me_off);

    }
    @OnClick({R.id.id_main,R.id.id_class,R.id.id_cb,R.id.id_me})
    public void onClick(View view) {
            resetImg();//重置状态
            switch (view.getId()) {
                case R.id.id_main:
                    mViewPager.setCurrentItem(0);//如果点击的是微信，就将viewpager的当前item设置为0，及切换到微信聊天的viewpager界面
                    maintitle.setText("主页");
                    mainImg.setImageResource(R.mipmap.main_on);//并将按钮颜色点亮
                    break;
                case R.id.id_class:
                    mViewPager.setCurrentItem(1);
                    maintitle.setText("课程");
                    classImg.setImageResource(R.mipmap.class_on);
                    break;
                case R.id.id_cb:
                    mViewPager.setCurrentItem(2);
                    maintitle.setText("课表");
                    cbImg.setImageResource(R.mipmap.cb_on);
                    break;
                case R.id.id_me:
                    mViewPager.setCurrentItem(3);
                    maintitle.setText("我的");
                    meImg.setImageResource(R.mipmap.me_on);
                    break;

                default:
                    break;
        }
    }

    protected void onRestart(){
        receive(tel);
        super.onRestart();

    }
    public void onBackPressed()
    {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
      return  true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void receive(String tel){
        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).queryUserbyPhone(
                tel

        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<User> userHttpDefault) {

                        if (userHttpDefault.getError_code() == 0) {
                            nav_name.setText(userHttpDefault.getData().getName());
                            nav_description.setText(userHttpDefault.getData().getDescription());
                            Glide.with(MainActivity.this).load(OneclassUtils.getBaseURL()+userHttpDefault.getData().getHeadimg())
                                    .transform(new CircleTransformation(MainActivity.this )).centerCrop().centerCrop().error(R.mipmap.error).into(imageView);

                        } else {

                            Toast.makeText(MainActivity.this,userHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("tag:", e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//按两次返回退出程序
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    //传递给fragment数据
    public  String getTel(){
        return tel;
    }

}
