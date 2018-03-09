package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import eclass.dogking.com.oneclass.AdminUpdata1;
import eclass.dogking.com.oneclass.AdminUpdata3;
import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

/**
 * Created by dog on 2018/3/9 0009.
 */

public class Manager3Adapter extends RecyclerView.Adapter<Manager3Adapter.ViewHolder> {

    private List<LectureShow> lectureShows;
    private Context mContext;
    private LayoutInflater inflater;
    protected RvListener listener;

    public Manager3Adapter(Context context,List<LectureShow> lectureShows, RvListener listener) {
        this.lectureShows = lectureShows;
        this.mContext=context;
        this.listener = listener;
        inflater=LayoutInflater. from(mContext);
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =inflater.inflate(R.layout.admin_lecture,viewGroup,false);
        ViewHolder vh = new ViewHolder(view,listener);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        LectureShow lectureShow=lectureShows.get(position);
        viewHolder.t1.setText(lectureShow.getLectureName());
        viewHolder.t2.setText(lectureShow.getTeacherName());
        Glide.with(mContext).load(OneclassUtils.getBaseURL()+lectureShow.getPictureUrl()).centerCrop().placeholder(R.mipmap.loading).error(R.mipmap.error).dontAnimate().into(viewHolder.i1);
//.override(100, 100) .fitCenter()
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //ExerciseShow exerciseShow=ExerciseShows.get(position);
                Intent intent=new Intent(v.getContext(),AdminUpdata3.class);
                //intent.putExtra("name",exerciseShow.getName());//传递课程
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(),"你点击的课为："+lectureShow1.getLectureName(),Toast.LENGTH_SHORT).show();
            }

        });



    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return lectureShows == null ? 0 : lectureShows.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1;
        public TextView t2;
        public ImageView i1;
        public Button b1;
        protected RvListener mListener;
        public ViewHolder(View view,RvListener listener){
            super(view);
            this.mListener=listener;
            t1 = (TextView) view.findViewById(R.id.name_lecture);
            t2=(TextView) view.findViewById(R.id.name_teacher);
            i1=(ImageView)view.findViewById(R.id.picture_lecture);
            b1=(Button) view.findViewById(R.id.delete);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }
    }
}