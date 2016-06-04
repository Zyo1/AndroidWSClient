package matthieu.merrheim.android.client.asynchone;

import android.app.Activity;
import android.app.ProgressDialog;


public abstract class AbstractAsynchroneActivity extends Activity implements AsynchroneActivity {

    protected static final String TAG = AbstractAsynchroneActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private boolean destroyed = false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
    }


    public void showLoadingProgressDialog() {
        this.showProgressDialog("Loading. Please wait...");
    }

    public void showProgressDialog(CharSequence message) {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setIndeterminate(true);
        }

        this.progressDialog.setMessage(message);
        this.progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.progressDialog != null && !this.destroyed) {
            this.progressDialog.dismiss();
        }
    }

}
