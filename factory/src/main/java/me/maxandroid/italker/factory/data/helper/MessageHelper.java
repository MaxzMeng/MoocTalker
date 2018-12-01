package me.maxandroid.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.message.MsgCreateModel;
import me.maxandroid.italker.factory.model.card.MessageCard;
import me.maxandroid.italker.factory.model.db.Message;
import me.maxandroid.italker.factory.model.db.Message_Table;
import me.maxandroid.italker.factory.net.Network;
import me.maxandroid.italker.factory.net.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageHelper {
    public static Message findFromLocal(String id) {
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();
    }

    // 发送是异步进行的
    public static void push(final MsgCreateModel model) {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                // 成功状态：如果是一个已经发送过的消息，则不能重新发送
                // 正在发送状态：如果是一个消息正在发送，则不能重新发送
                Message message = findFromLocal(model.getId());
                if (message != null && message.getStatus() != Message.STATUS_FAILED)
                    return;


                // TODO 如果是文件类型的（语音，图片，文件），需要先上传后才发送

                // 我们在发送的时候需要通知界面更新状态，Card;
                final MessageCard card = model.buildCard();
                Factory.getMessageCenter().dispatch(card);

                // 直接发送, 进行网络调度
                RemoteService service = Network.remote();
                service.msgPush(model).enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                        RspModel<MessageCard> rspModel = response.body();
                        if (rspModel != null && rspModel.success()) {
                            MessageCard rspCard = rspModel.getResult();
                            if (rspCard != null) {
                                // 成功的调度
                                Factory.getMessageCenter().dispatch(rspCard);
                            }
                        } else {
                            // 检查是否是账户异常
                            Factory.decodeRspCode(rspModel, null);
                            // 走失败流程
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                        // 通知失败
                        card.setStatus(Message.STATUS_FAILED);
                        Factory.getMessageCenter().dispatch(card);
                    }
                });
            }
        });
    }
}
