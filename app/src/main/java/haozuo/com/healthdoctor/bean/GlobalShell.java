package haozuo.com.healthdoctor.bean;

/**
 * Created by xiongwei1 on 2016/6/2.
 */
public class GlobalShell<T> {
    public int Code;

    public String OriginMessage;

    public T Data;

    public String getMessage(){
        if(OriginMessage!=null && OriginMessage!=""){
            return OriginMessage;
        }
        String msg="系统网络不稳定,请稍后重试!";
        if(Code==-2){
            msg="系统异常";
        }
        return msg;
    }

    public GlobalShell(int code, String msg){
        Code=code;
        OriginMessage =msg;
    }

    public GlobalShell(int code, String msg , T data){
        Code=code;
        OriginMessage =msg;
        Data=data;
    }
}
