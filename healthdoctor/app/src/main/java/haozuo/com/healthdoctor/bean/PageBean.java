package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzguest3 on 2016/6/13.
 */
public class PageBean<T> implements Serializable {
    public int Count;

    public List<T>Data;
}
