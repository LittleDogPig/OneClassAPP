package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import eclass.dogking.com.oneclass.ExerciseActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.UserExerciseActivity;
import eclass.dogking.com.oneclass.UserExercisefinishActivity;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.UserExerciseShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

/**
 * Created by dog on 2018/2/10 0010.
 */


public class UserExerciseAdapter extends RecyclerView.Adapter<UserExerciseAdapter.ViewHolder> {

    private List<UserExerciseShow> userExerciseShows;
    private String tel;
    private String lecturename;//课程名
    private Context mContext;
    private LayoutInflater inflater;

    public UserExerciseAdapter(Context context, List<UserExerciseShow> userExerciseShows, String tel,String lecutrename) {
        this.userExerciseShows = userExerciseShows;
        this.mContext = context;
        this.tel = tel;
        this.lecturename=lecutrename;
        inflater = LayoutInflater.from(mContext);
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.exerciseitem, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final UserExerciseShow userExerciseShow = userExerciseShows.get(position);
        viewHolder.t1.setText(userExerciseShow.getName());
        viewHolder.t2.setText(userExerciseShow.getFinish());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                UserExerciseShow userExerciseShow1 = userExerciseShows.get(position);
                if(userExerciseShow1.getFinish().equals("未完成")){
                Intent intent = new Intent(v.getContext(), UserExerciseActivity.class);
                intent.putExtra("lecturename", lecturename);//课程名
                intent.putExtra("name", userExerciseShow1.getName());//练习名
                intent.putExtra("tel", tel);//传递登录的用户
                v.getContext().startActivity(intent);}
                else {
                    Intent intent = new Intent(v.getContext(), UserExercisefinishActivity.class);
                    intent.putExtra("lecturename", lecturename);//课程名
                    intent.putExtra("name", userExerciseShow1.getName());//练习名
                    intent.putExtra("tel", tel);//传递登录的用户
                    v.getContext().startActivity(intent);

                }
                //Toast.makeText(v.getContext(),"你点击的课为："+lectureShow1.getLectureName(),Toast.LENGTH_SHORT).show();
            }

        });


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return userExerciseShows == null ? 0 : userExerciseShows.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1;
        public TextView t2;


        public ViewHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.userexercise_name);
            t2 = (TextView) view.findViewById(R.id.userexercise_finish);
        }
    }
}