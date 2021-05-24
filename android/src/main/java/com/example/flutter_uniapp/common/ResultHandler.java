package com.example.flutter_uniapp.common;


import android.os.Handler;
import android.os.Looper;

import io.flutter.plugin.common.MethodChannel;

public class ResultHandler {

    private Handler handler = new Handler(Looper.getMainLooper());

    private MethodChannel.Result result;

    private boolean isReply = false;

    public ResultHandler(MethodChannel.Result result) {
        this.result = result;
    }

    public boolean isReply() {
        return isReply;
    }

    public void reply(final Object object) {
        if (isReply) {
            return;
        }
        isReply = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                result.success(object);
            }
        });
    }

    public void replyError(final String code, final String msg, final Throwable err) {
        if (isReply) {
            return;
        }
        isReply = true;

        handler.post(new Runnable() {
            @Override
            public void run() {
                result.error(code, msg, err);
            }
        });
    }

    public void notImplemented() {
        if (isReply) {
            return;
        }
        isReply = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                result.notImplemented();
            }
        });
    }

    public void replyEmpty() {
        reply(1);
    }
}
