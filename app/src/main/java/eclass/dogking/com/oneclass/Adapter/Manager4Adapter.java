package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eclass.dogking.com.oneclass.AdminExercise;
import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.UserExerciseActivity;
import eclass.dogking.com.oneclass.UserExercisefinishActivity;
import eclass.dogking.com.oneclass.entiry.ExerciseShow;
import eclass.dogking.com.oneclass.entiry.UserExerciseShow;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class Manager4Adapter extends RecyclerView.Adapter<Manager4Adapter.ViewHolder> {

    private List<ExerciseShow> ExerciseShows;
    private Context mContext;
    private LayoutInflater inflater;

    public Manager4Adapter(Context context, List<ExerciseShow> ExerciseShows) {
        this.ExerciseShows = ExerciseShows;
        this.mContext = context;

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
        final ExerciseShow ExerciseShow = ExerciseShows.get(position);
        viewHolder.t1.setText(ExerciseShow.getName());
        viewHolder.t2.setText(ExerciseShow.getNumber());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
               ExerciseShow exerciseShow=ExerciseShows.get(position);
                Intent intent=new Intent(v.getContext(),AdminExercise.class);
                intent.putExtra("name",exerciseShow.getName());//传递课程
                intent.putExtra("Lid",exerciseShow.getId()+"");
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(),"你点击的课为："+lectureShow1.getLectureName(),Toast.LENGTH_SHORT).show();
            }

        });


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return ExerciseShows == null ? 0 : ExerciseShows.size();
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