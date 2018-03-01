package eclass.dogking.com.oneclass.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragAdapter extends FragmentPagerAdapter {
	  
    private List<Fragment> mFragments;
      
    public FragAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mFragments= list;
    }
   
	@Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub  
        return mFragments.get(arg0);  
    }  
  
    @Override
    public int getCount() {  
        // TODO Auto-generated method stub  
        return mFragments.size();  
    }  
  
}  