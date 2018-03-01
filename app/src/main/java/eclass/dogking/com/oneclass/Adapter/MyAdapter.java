package eclass.dogking.com.oneclass.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

 private List<LectureShow> lectureShows;
 private String tel;
 private Context mContext;
 private LayoutInflater inflater;

 public MyAdapter(Context context,List<LectureShow> lectureShows,String tel) {
  this.lectureShows = lectureShows;
 this.mContext=context;
  this.tel=tel;
  inflater=LayoutInflater. from(mContext);
 }
 //创建新View，被LayoutManager所调用
 @Override
 public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
 View view =inflater.inflate(R.layout.item,viewGroup,false);
 ViewHolder vh = new ViewHolder(view);
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

  viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
   public void  onClick(View v){
    int position =viewHolder.getAdapterPosition();
    LectureShow lectureShow1=lectureShows.get(position);
    Intent intent = new Intent(v.getContext(),LectureActivity.class);
    intent.putExtra("name",lectureShow1.getLectureName());//传递课程
    intent.putExtra("tel",tel);//传递登录的用户
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
 public ViewHolder(View view){
 super(view);
 t1 = (TextView) view.findViewById(R.id.name_lecture);
  t2=(TextView) view.findViewById(R.id.name_teacher);
  i1=(ImageView)view.findViewById(R.id.picture_lecture);
 }
 }
 }