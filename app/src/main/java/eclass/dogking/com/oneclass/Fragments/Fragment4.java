package eclass.dogking.com.oneclass.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.*;

//考试
public class Fragment4 extends Fragment {

    @BindView(R.id.p4_1)
    ImageButton p41;
    @BindView(R.id.p4_2)
    ImageButton p42;
    @BindView(R.id.p4_3)
    ImageButton p43;
    @BindView(R.id.p4_4)
    ImageButton p44;
    @BindView(R.id.p4_5)
    ImageButton p45;
    @BindView(R.id.p4_6)
    ImageButton p46;
    @BindView(R.id.p4_7)
    ImageButton p47;
    @BindView(R.id.p4_8)
    ImageButton p48;
    Unbinder unbinder;
    String tel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.tab4, container, false);

        //对View中控件的操作方法

        unbinder = ButterKnife.bind(this, view);
        return view;
    }
@OnClick({R.id.p4_1,R.id.p4_2,R.id.p4_3,R.id.p4_4,R.id.p4_5,R.id.p4_6,R.id.p4_7,R.id.p4_8})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.p4_1:

                Intent intent=new Intent(getActivity(),Tab4_1Activity.class);
                intent.putExtra("tel",tel);
                startActivity(intent);
            break;
            case R.id.p4_2:
                Intent intent2=new Intent(getActivity(),Tab4_2Activity.class);
                intent2.putExtra("tel",tel);
                startActivity(intent2);
            break;
            case R.id.p4_3:
                Intent intent3=new Intent(getActivity(),Tab4_3Activity.class);
                intent3.putExtra("tel",tel);
                startActivity(intent3);

            break;
            case R.id.p4_4:
                Intent intent4=new Intent(getActivity(),Tab4_4Activity.class);
                intent4.putExtra("tel",tel);
                startActivity(intent4);

            break;
            case R.id.p4_5:
                Intent intent5=new Intent(getActivity(),Tab4_5Activity.class);
                intent5.putExtra("tel",tel);
                startActivity(intent5);

            break;
            case R.id.p4_6:
                Intent intent6=new Intent(getActivity(),Tab4_6Activity.class);
                intent6.putExtra("tel",tel);
                startActivity(intent6);

            break;
            case R.id.p4_7:
                Intent intent7=new Intent(getActivity(),LoginActivity.class);
                getActivity().finish();
                startActivity(intent7);

            break;
            case R.id.p4_8:
                getActivity().finish();

            break;
    }

}
    public  void  onAttach(Activity activity){
        super.onAttach(activity);
        tel=((MainActivity) activity).getTel();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}