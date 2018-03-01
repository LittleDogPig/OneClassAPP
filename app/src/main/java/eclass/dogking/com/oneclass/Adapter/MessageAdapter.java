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

import de.hdodenhof.circleimageview.CircleImageView;
import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.MessageShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

/**
 * Created by dog on 2018/2/21 0021.
 */


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageShow> list;
    private Context mContext;
    private LayoutInflater inflater;

    public MessageAdapter(Context context,List<MessageShow> list) {
        this.list=list;
        this.mContext=context;
        inflater=LayoutInflater. from(mContext);
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =inflater.inflate(R.layout.message,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        MessageShow messageShow=list.get(position);
        viewHolder.name.setText(messageShow.getName());
        viewHolder.descrition.setText(messageShow.getDescription());
        viewHolder.message.setText(messageShow.getMessage());
        viewHolder.time.setText(messageShow.getTime());
        Glide.with(mContext).load(OneclassUtils.getBaseURL()+messageShow.getHeadimg()).centerCrop().error(R.mipmap.error).dontAnimate().into(viewHolder.head);
//.override(100, 100) .fitCenter()



    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView head;
        public TextView name;
        public TextView descrition;
        public TextView message;
        public TextView time;
        public ViewHolder(View view){
            super(view);
            head=(CircleImageView)view.findViewById(R.id.head);
            name = (TextView) view.findViewById(R.id.name);
            descrition=(TextView) view.findViewById(R.id.description);
            message = (TextView) view.findViewById(R.id.message);
            time=(TextView) view.findViewById(R.id.time);
        }
    }
}