package eclass.dogking.com.oneclass.utils;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by dog on 2018/2/26 0026.
 */

public interface GalleryfinalActionListener {

    public void success(List<PhotoInfo> list);
    public void failed(String msg);


}
