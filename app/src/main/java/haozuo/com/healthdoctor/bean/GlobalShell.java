package haozuo.com.healthdoctor.bean;

/**
 * Created by xiongwei1 on 2016/6/2.
 */
public class GlobalShell<T> {
    public boolean RequestSuccess;

    public boolean LogicSuccess;

    public String Message;

    public T Data;

    public RequestErrorEnum RequestErrorType;

    public String OriginErrorMessage;

    //http请求成功,逻辑处理成功
    public GlobalShell( T data){
        RequestSuccess=true;
        LogicSuccess=true;
        Data=data;
    }

    //http请求成功,逻辑处理失败
    public GlobalShell(String logicErrorMessage) {
        Message = logicErrorMessage;
        RequestSuccess = true;
    }

    //http请求失败或者处理过程exception
    public GlobalShell(RequestErrorEnum errorType,String originErrorMessage){
        RequestErrorType=errorType;
        OriginErrorMessage=originErrorMessage;
        Message="系统异常！";
        if(errorType==RequestErrorEnum.HttpResponseError || errorType==RequestErrorEnum.HttpException){
            Message="网络请求不稳定，请稍后重试！";
        }
    }

}
