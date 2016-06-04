package matthieu.merrheim.android.client.asynchone;


public interface AsynchroneActivity {

    public void showLoadingProgressDialog();

    public void showProgressDialog(CharSequence message);

    public void dismissProgressDialog();

}
