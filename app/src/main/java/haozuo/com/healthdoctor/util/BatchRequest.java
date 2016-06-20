package haozuo.com.healthdoctor.util;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import haozuo.com.healthdoctor.dispatcher.BaseDispatcher;

/**
 * Created by xiongwei on 16/5/15.
 */
public class BatchRequest {
    OnBatchRequestListener listener;
    List<MessageSendEntity> requestList;
    Map<String,BaseDispatcher.RequestResult> requestMap;
    public BatchRequest(OnBatchRequestListener listener){
        requestList=new ArrayList<>();
        requestMap=new TreeMap<>();
        this.listener=listener;
    }

    public void pushRequest(BaseDispatcher baseDispatcher,int what){
        pushRequest(baseDispatcher,what,null,null);
    }

    public void pushRequest(BaseDispatcher baseDispatcher,int what,Map<String,Object>params){
        pushRequest(baseDispatcher,what,params,null);
    }

    public void pushRequest(BaseDispatcher baseDispatcher,int what,Object rebackObj){
        pushRequest(baseDispatcher, what, null, rebackObj);
    }

    public void pushRequest(BaseDispatcher baseDispatcher,int what,Map<String,Object>params,Object rebackObj){
        MessageSendEntity entity=new MessageSendEntity();
        entity.Dispatcher =baseDispatcher;
        entity.What=what;
        entity.Params=params;
        entity.RebackObj=rebackObj;
        requestList.add(entity);
    }

    public void pushResponse(Message message){
        BaseDispatcher.RequestResult requestResult= (BaseDispatcher.RequestResult)message.obj;
        String identity=requestResult.Identity;
        if(!requestMap.containsKey(identity)){
            return;
        }
        requestMap.put(identity, requestResult);
        if(listener==null){
            return;
        }
        listener.receiveNotify(requestResult);
        boolean isAllReceived=true;
        for (BaseDispatcher.RequestResult me:requestMap.values()) {
            if(me==null){
                isAllReceived=false;
                break;
            }
        }
        if(isAllReceived){
            listener.endRequest();
        }
    }

    public void request(){
        if(listener!=null){
            listener.beforeRequest();
        }
        for (MessageSendEntity entity: requestList) {
            String identity= entity.Dispatcher.sendMessage(entity.What,entity.Params,entity.RebackObj);
            requestMap.put(identity,null);
        }
    }

    public BaseDispatcher.RequestResult getMessageByWhat(int what){
        for (BaseDispatcher.RequestResult messageEntity:requestMap.values()) {
            if(messageEntity!=null && messageEntity.What==what){
                return messageEntity;
            }
        }
        return null;
    }

    static class MessageSendEntity{
        public BaseDispatcher Dispatcher;
        public int What;
        public Map<String,Object> Params;
        public Object RebackObj;
    }
}
