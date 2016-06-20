package haozuo.com.healthdoctor.bean;

/**
 * Created by xiongwei1 on 2016/6/2.
 */
public class GlobalShell<T> {
    public int Code;

    public String Message;

    public T Data;

    public GlobalShell(int code, String message){
        Code=code;
        Message=message;
    }

    public GlobalShell(int code, String message , T data){
        Code=code;
        Message=message;
        Data=data;
    }
}
