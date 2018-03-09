package eclass.dogking.com.oneclass.Fragments;

/**
 * Created by dog on 2018/1/4 0004.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.LectureDataActivity;
import eclass.dogking.com.oneclass.MainActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.Tab4_4Activity;
import eclass.dogking.com.oneclass.entiry.Course;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//课表
public class Fragment3 extends Fragment {

    @BindView(R.id.test_empty)
    TextView empty;
    @BindView(R.id.test_monday_course)
    TextView monColum;
    @BindView(R.id.test_tuesday_course)
    TextView tueColum;
    @BindView(R.id.test_wednesday_course)
    TextView wedColum;
    @BindView(R.id.test_thursday_course)
    TextView thrusColum;
    @BindView(R.id.test_friday_course)
    TextView friColum;
    @BindView(R.id.test_saturday_course)
    TextView satColum;
    @BindView(R.id.test_sunday_course)
    TextView sunColum;
    @BindView(R.id.test_course_rl)
    RelativeLayout course_table_layout;
    Unbinder unbinder;
    private String tel;
    /** 屏幕宽度 **/
    protected int screenWidth;
    /** 课程格子平均宽度 **/
    protected int aveWidth;
    private Context context;
    private MainActivity activity;
    private List<Course> courseList;


    private int gridHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.tab3, container, false);

        //对View中控件的操作方法

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onResume() {
        super.onResume();
        initview();
        getRecore();
        //initlecure("演员的自我修养",1,3);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getRecore(){
        Observable<HttpDefault<List<Course>>> observable= OneclassUtils.createAPI(LecturecsAPI.class).getuserchose(
                tel);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Course>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Course>> courseHttpDefault) {

                        if (courseHttpDefault.getError_code() == -1) {
                            Toast.makeText(context,courseHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (courseHttpDefault.getError_code() == 0) {
                            courseList=courseHttpDefault.getData();
                            for (int i=0;i<courseList.size();i++){
                                Course course=courseList.get(i);
                               String name=course.getName();
                                String detail=course.getDetail();
                                String time=course.getTime();
                                initlecure(name,"@"+detail,week(time),time(time),bg());
                            }

                            //Toast.makeText(getContext(),"list为"+lectureShows.get(0).getLectureName(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //showToast("服务器貌似出问题了(throwable)...", ToastDuration.SHORT);
                        Log.e("tag:", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }

                });

    }

    protected  int week(String time){
        int a=0;
        int i=0;
        if (time.length()<3){

            try {
                a = Integer.parseInt(time);
                i=a/10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return i;}
        else {try {
            a = Integer.parseInt(time);
            i=a/100;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
            return i;}

    }

    protected int time(String time){
        int a=0;
        int i=0;
        if(time.length()<3){
            try {
                a = Integer.parseInt(time);
                i=a%10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return i;
        }
        else{
            try {
                a = Integer.parseInt(time);
                i=a%100;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return i;
        }
    }

    protected int bg(){
        int i=(int)(Math.random()*7);
            return i;

    }


    protected void  initview() {
        activity = (MainActivity) getActivity();
        context = getContext();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//获取屏幕信息
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //第一个空白格子设置为25宽
        empty.setWidth(aveWidth * 3 / 4);
        monColum.setWidth(aveWidth * 33 / 32 + 1);
        tueColum.setWidth(aveWidth * 33 / 32 + 1);
        wedColum.setWidth(aveWidth * 33 / 32 + 1);
        thrusColum.setWidth(aveWidth * 33 / 32 + 1);
        friColum.setWidth(aveWidth * 33 / 32 + 1);
        satColum.setWidth(aveWidth * 33 / 32 + 1);
        sunColum.setWidth(aveWidth * 33 / 32 + 1);
        this.screenWidth = width;
        this.aveWidth = aveWidth;
        int height = dm.heightPixels;
        gridHeight = height / 12;
        //设置课表界面
        //动态生成12 * maxCourseNum个textview
        for (int i = 1; i <= 12; i++) {

            for (int j = 1; j <= 8; j++) {

                TextView tx = new TextView(context);
                tx.setId((i - 1) * 8 + j);
                //除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
                if (j < 8)
                    tx.setBackgroundDrawable(context.
                            getResources().getDrawable(R.drawable.course_text_view_bg));
                else
                    tx.setBackgroundDrawable(context.
                            getResources().getDrawable(R.drawable.course_table_last_colum));
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 33 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //字体样式
                tx.setTextAppearance(context, R.style.courseTableText);
                //如果是第一列，需要设置课的序号（1 到 12）
                if (j == 1) {
                    tx.setText(String.valueOf(i));
                    rp.width = aveWidth * 3 / 4;
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                } else {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8 + j - 1);
                    tx.setText("");
                }

                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }


    }
        protected void initlecure(String name,String detail, int week, int time, int bg){
            //五种颜色的背景
            int[] background = {R.drawable.course_info_blue,R.drawable.course_info_blue2,
                    R.drawable.course_info_blue3, R.drawable.course_info_green,
                    R.drawable.course_info_red, R.drawable.course_info_red,
                    R.drawable.course_info_yellow,};
        // 添加课程信息
        TextView courseInfo = new TextView(context);
        courseInfo.setText(name+detail);
        //该textview的高度根据其节数的跨度来设置
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                aveWidth * 31 / 32,
                (gridHeight - 5) * 2 );//(gridHeight - 5)一节课的跨度
        //textview的位置由课程开始节数和上课的时间（day of week）确定
        rlp.topMargin = 5 + (time - 1) * gridHeight;
        rlp.leftMargin = 1;
        // 偏移由这节课是星期几决定
        rlp.addRule(RelativeLayout.RIGHT_OF, week);
        //字体剧中
        courseInfo.setGravity(Gravity.CENTER);
        // 设置一种背景
        courseInfo.setBackgroundResource(background[bg]);
        courseInfo.setTextSize(12);
        courseInfo.setLayoutParams(rlp);
        courseInfo.setTextColor(Color.WHITE);
        //设置不透明度
        courseInfo.getBackground().setAlpha(222);
        courseInfo.setOnClickListener(new Listener1(name,tel));
        course_table_layout.addView(courseInfo);


    }

    public class Listener1 implements View.OnClickListener{
        String name;
        String tel;
        Listener1(String name,String tel){
            this.name=name;
            this.tel=tel;
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,LectureDataActivity.class);
            intent.putExtra("tel",tel);
            intent.putExtra("name",name);
            startActivity(intent);

        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tel = ((MainActivity) activity).getTel();

    }

}