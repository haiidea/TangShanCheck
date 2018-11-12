package haiidea.com.tangshancheck.callback;

import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import haiidea.com.tangshancheck.Constant;

public class MyAuthCallback extends FingerprintManagerCompat.AuthenticationCallback {
    private Handler handler = null;
    public MyAuthCallback(Handler handler) {
        super(); this.handler = handler;
    }
    /**
 * 验证错误信息
 */
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_ERROR, errMsgId, 0).sendToTarget();
        }
    }
    /**
 * 身份验证帮助
 */
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_HELP, helpMsgId, 0).sendToTarget();
        }
     }
 /**
 * 验证成功
 */
 @Override
 public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        if (handler != null) {
            Message message = Message.obtain();
            message.what = Constant.MSG_AUTH_SUCCESS;
            handler.sendMessage(message);
        }
    }
    /**
 * 验证失败
 */
    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_FAILED).sendToTarget();
        }
    }
}