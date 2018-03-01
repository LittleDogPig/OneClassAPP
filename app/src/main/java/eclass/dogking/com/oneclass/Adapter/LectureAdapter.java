package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.TextLecture;
import eclass.dogking.com.oneclass.VideoLecture;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.Lectureson;

/**
 * Created by dog on 2018/2/15 0015.
 */

public class LectureAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Lectureson> list;
    private String lecturename;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;

    public LectureAdapter(Context context, List<Lectureson> list,String lecturename) {
        this.list = list;
        this.mContext = context;
        this.lecturename=lecturename;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemViewType(int position) {

        Lectureson lectureson = list.get(position);
        if (lectureson.getHead() ==1 ) {
            return TYPE_TITLE;
        } else
            return TYPE_CONTENT;

    }

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;

        if (viewType == TYPE_TITLE) {
            view = inflater.inflate(R.layout.lecturetitle_layout, viewGroup, false);
            return new LectureAdapter.TitleHolder(view);
        } else {
            view = inflater.inflate(R.layout.lecturecontent_layout, viewGroup, false);
            return new LectureAdapter.ContentHolder(view);}

    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Lectureson lectureson=list.get(position);
        if (holder instanceof LectureAdapter.TitleHolder) {((TitleHolder) holder).t1.setText(lectureson.getName());

        }
        else ((ContentHolder) holder).t1.setText(lectureson.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getHead()==0&&list.get(position).getType()==1){
                    Intent intent = new Intent(mContext,VideoLecture.class);
                    intent.putExtra("lecturename",lecturename);//传递课程
                    intent.putExtra("id",list.get(position).getId()+"");//传递子课id
                    mContext.startActivity(intent);
                }
                else if (list.get(position).getHead()==0&&list.get(position).getType()==2){
                    Intent intent = new Intent(mContext,TextLecture.class);
                    intent.putExtra("lecturename",lecturename);//传递课程
                    intent.putExtra("id",list.get(position).getId()+"");//传递子课
                    mContext.startActivity(intent);
                }
            }
        });

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }



    public  class TitleHolder extends RecyclerView.ViewHolder {
        private TextView t1;

        protected RvListener mListener;

        public TitleHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.t1);

        }
    }

    public  class ContentHolder extends RecyclerView.ViewHolder {
        private TextView t1;

        protected RvListener mListener;

        public ContentHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.t1);

        }
    }
    }
