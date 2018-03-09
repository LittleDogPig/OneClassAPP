package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import eclass.dogking.com.oneclass.AdminUpdata1;
import eclass.dogking.com.oneclass.AdminUpdata2;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.MessageShow;
import eclass.dogking.com.oneclass.entiry.Teacher;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

/**
 * Created by dog on 2018/3/9 0009.
 */


    public class Manager2Adapter  extends RecyclerView.Adapter<Manager2Adapter.ViewHolder> {
        private Context mContext;
        private LayoutInflater inflater;
        private List<Teacher> list;
        protected RvListener listener;


        public Manager2Adapter(Context context, List<Teacher> list, RvListener listener) {
            this.list = list;
            this.mContext = context;
            this.listener = listener;
            inflater = LayoutInflater.from(mContext);
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = inflater.inflate(R.layout.usermanager, viewGroup, false);
            ViewHolder vh = new ViewHolder(view,listener);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Glide.with(mContext).load(OneclassUtils.getBaseURL()+list.get(position).getPictureurl())
                    .centerCrop().fitCenter().error(R.mipmap.error).into(viewHolder.minehead);
            viewHolder.t1.setText(list.get(position).getName());
            viewHolder.t2.setText(list.get(position).getSex());
            viewHolder.t3.setText(list.get(position).getEmail());
            viewHolder.t4.setText(list.get(position).getSchool());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    //ExerciseShow exerciseShow=ExerciseShows.get(position);
                    Intent intent=new Intent(v.getContext(),AdminUpdata2.class);
                    //intent.putExtra("name",exerciseShow.getName());//传递课程
                    v.getContext().startActivity(intent);

                    //Toast.makeText(v.getContext(),"你点击的课为："+lectureShow1.getLectureName(),Toast.LENGTH_SHORT).show();
                }

            });


        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public  class ViewHolder extends RecyclerView.ViewHolder {
            private CircleImageView minehead;
            private TextView t1;
            private TextView t2;
            private TextView t3;
            private TextView t4;
            private Button b1;
            private View mview;
            protected RvListener mListener;

            public ViewHolder(View view,RvListener listener) {
                super(view);
                this.mListener=listener;
                this.mview=view;
                minehead=(CircleImageView)view.findViewById(R.id.minehead);
                t1 = (TextView) view.findViewById(R.id.name);
                t2 = (TextView) view.findViewById(R.id.sex);
                t3 = (TextView) view.findViewById(R.id.tel);
                t4 = (TextView) view.findViewById(R.id.time);
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