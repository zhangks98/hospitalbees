package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tomorrow.ntu.edu.sg.hospitalbees.utilities.TIDParser;

public class MyQueue extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener{

    private TextView queueNumberLabelText, queueNumberValueText, lengthBeforeLabelText, lengthBeforeValueText, queueBottomReminderText, qrTopReminderText, qrBottomReminderText;
    private ImageView mQRCodeImage;
    private ProgressBar mLoadingInicator;
    private FloatingActionButton mCreateBookingFAB;
    private SharedPreferences mUserPreferences;
    private SharedPreferences mBookingPreferences;
    private com.google.zxing.Writer mQRCodeWriter;

    private OkHttpClient mHttpClient;

    private final String TAG = this.getClass().getSimpleName();
    private final int MISS_TIME_ALLOWED_IN_MINUTES = 5;
    private final String serverUrl = BuildConfig.SERVER_URL;

    // TODO cache QR code BitMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_queue);

        queueNumberLabelText = (TextView) findViewById(R.id.tv_queue_number_label);
        queueNumberValueText = (TextView) findViewById(R.id.tv_queue_number_value);
        lengthBeforeLabelText = (TextView) findViewById(R.id.tv_queue_length_before_label);
        lengthBeforeValueText = (TextView) findViewById(R.id.tv_queue_length_before_value);
        queueBottomReminderText = (TextView) findViewById(R.id.tv_queue_bottom_reminder);
        qrTopReminderText = (TextView) findViewById(R.id.tv_qr_top_reminder);
        qrBottomReminderText = (TextView) findViewById(R.id.tv_qr_bottom_reminder);
        mQRCodeImage = (ImageView) findViewById(R.id.QRImage);
        mLoadingInicator = findViewById(R.id.pb_load_queue_view);
        mCreateBookingFAB = findViewById(R.id.fab_create_booking);
        mCreateBookingFAB.setOnClickListener(this);

        mUserPreferences = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        mBookingPreferences = getSharedPreferences(getString(R.string.pref_booking), Context.MODE_PRIVATE);
        mBookingPreferences.registerOnSharedPreferenceChangeListener(this);

        mQRCodeWriter = new QRCodeWriter();
        mHttpClient = new OkHttpClient();

/*        SharedPreferences.Editor editor = mBookingPreferences.edit();
        editor.putString(getString(R.string.pref_booking_tid_key), "00012018-04-15T14:28:07Z0002");
        editor.putString(getString(R.string.pref_booking_status_key), getString(R.string.pref_booking_status_inactive_value));
        editor.commit();*/
        updateQueueActivity();
        updateBookingStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookingPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    protected void updateQueueActivity() {
        showLoadingIndicator();
        String bookingStatus = mBookingPreferences.getString(getString(R.string.pref_booking_status_key), getString(R.string.pref_booking_status_completed_value));
        String tid = mBookingPreferences.getString(getString(R.string.pref_booking_tid_key), null);

        if (tid != null) {
            if (bookingStatus.equals(getString(R.string.pref_booking_status_inactive_value))) {
                setInactive(tid);
            } else if (bookingStatus.equals(getString(R.string.pref_booking_status_active_value))) {
                setActive(tid);
            } else if (bookingStatus.equals(getString(R.string.pref_booking_status_missed_value))) {
                long missTime = mBookingPreferences.getLong(getString(R.string.pref_booking_miss_time_key), -1);
                if (missTime > 0) {
                    Calendar missTimeCal = Calendar.getInstance();
                    missTimeCal.setTimeInMillis(missTime);
                    missTimeCal.add(Calendar.MINUTE, MISS_TIME_ALLOWED_IN_MINUTES);
                    setMissed(tid,missTimeCal);
                } else {
                    Log.e(TAG, "error fetching miss time");
                }
            } else if (bookingStatus.equals(getString(R.string.pref_booking_status_active_value))) {
                setReactivated(tid);
            }
        } else if (bookingStatus.equals(getString(R.string.pref_booking_status_completed_value))) {
            setCompleted();
        } else if (bookingStatus.equals(getString(R.string.pref_booking_status_absent_value))) {
            setAbsent();
        } else {
            Log.e(TAG, "error fetching tid");
        }

    }

    private void updateBookingStatus() {
        if (mBookingPreferences != null) {
            String tid = mBookingPreferences.getString(getString(R.string.pref_booking_tid_key), null);
            if (tid != null) {
                Uri queryUri = Uri.parse(serverUrl).buildUpon().appendPath("api").appendPath("booking").appendPath(tid).build();
                Request request = new Request.Builder().url(queryUri.toString()).get().build();
                mHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String body = response.body().string();
                            try {
                                JSONObject obj = new JSONObject(body);
                                final String bookingStatus = obj.getString("Booking_BookingStatus");
                                final String queueStatus = obj.getString("Booking_QueueStatus");
                                if (bookingStatus.equals(getString(R.string.pref_booking_status_absent_value)) ||
                                        bookingStatus.equals(getString(R.string.pref_booking_status_completed_value))) {
                                    mBookingPreferences.edit().putString(getString(R.string.pref_booking_status_key), bookingStatus).apply();
                                } else if (!queueStatus.equals("FINISHED") && !queueStatus.equals("MISSED")) {
                                    mBookingPreferences.edit().putString(getString(R.string.pref_booking_status_key), queueStatus).apply();
                                } else if (queueStatus.equals(getString(R.string.pref_booking_status_missed_value))) {
                                    // TODO do multiple http request
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
            }
        }
    }

    private Bitmap generateQRCode(String value) {
        try {
            BitMatrix matrix = mQRCodeWriter.encode(value, BarcodeFormat.QR_CODE, 300, 300);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
            Log.e(TAG,"mQRCodeImage writer error");
            return null;
        }
    }

    private void showQRCode() {
        qrTopReminderText.setVisibility(View.VISIBLE);
        mQRCodeImage.setVisibility(View.VISIBLE);
        qrBottomReminderText.setVisibility(View.VISIBLE);
        queueNumberLabelText.setVisibility(View.GONE);
        queueNumberValueText.setVisibility(View.GONE);
        lengthBeforeLabelText.setVisibility(View.GONE);
        lengthBeforeValueText.setVisibility(View.GONE);
        queueBottomReminderText.setVisibility(View.GONE);
        mCreateBookingFAB.setVisibility(View.GONE);
        mLoadingInicator.setVisibility(View.GONE);

    }

    private void showQueueNumber() {
        qrTopReminderText.setVisibility(View.GONE);
        mQRCodeImage.setVisibility(View.GONE);
        qrBottomReminderText.setVisibility(View.GONE);
        queueNumberLabelText.setVisibility(View.VISIBLE);
        queueNumberValueText.setVisibility(View.VISIBLE);
        lengthBeforeLabelText.setVisibility(View.VISIBLE);
        lengthBeforeValueText.setVisibility(View.VISIBLE);
        queueBottomReminderText.setVisibility(View.VISIBLE);
        mCreateBookingFAB.setVisibility(View.GONE);
        mLoadingInicator.setVisibility(View.GONE);

    }

    private void showNoPendingBooking() {
        qrTopReminderText.setVisibility(View.VISIBLE);
        mQRCodeImage.setVisibility(View.GONE);
        mCreateBookingFAB.setVisibility(View.VISIBLE);
        qrBottomReminderText.setVisibility(View.VISIBLE);
        queueNumberLabelText.setVisibility(View.GONE);
        queueNumberValueText.setVisibility(View.GONE);
        lengthBeforeLabelText.setVisibility(View.GONE);
        lengthBeforeValueText.setVisibility(View.GONE);
        queueBottomReminderText.setVisibility(View.GONE);
        mLoadingInicator.setVisibility(View.GONE);
    }

    private void showLoadingIndicator() {
        qrTopReminderText.setVisibility(View.GONE);
        mQRCodeImage.setVisibility(View.GONE);
        mCreateBookingFAB.setVisibility(View.GONE);
        qrBottomReminderText.setVisibility(View.GONE);
        queueNumberLabelText.setVisibility(View.GONE);
        queueNumberValueText.setVisibility(View.GONE);
        lengthBeforeLabelText.setVisibility(View.GONE);
        lengthBeforeValueText.setVisibility(View.GONE);
        queueBottomReminderText.setVisibility(View.GONE);
        mLoadingInicator.setVisibility(View.VISIBLE);
    }

    private void setQueueNumber(String tid) {
        String queueNumber = TIDParser.getQueueNumber(tid);
        queueNumberValueText.setText(queueNumber);
        int queueLengthBefore = mBookingPreferences.getInt((getString(R.string.pref_booking_length_before_key)), -1);
        if (queueLengthBefore >= 0) {
            lengthBeforeValueText.setText(String.valueOf(queueLengthBefore));
        } else {
            lengthBeforeValueText.setText(getString(R.string.queue_length_before_unavailable_text));
        }
    }

    private void setInactive(String tid) {
        mQRCodeImage.setImageBitmap(generateQRCode(tid));
        qrTopReminderText.setText(R.string.qr_top_reminder_text_inactive);
        qrBottomReminderText.setText(R.string.qr_bottom_reminder_text_inactive);
        showQRCode();
    }

    private void setActive(String tid) {
        setQueueNumber(tid);
        queueBottomReminderText.setText(R.string.queue_reminder_text_active);
        showQueueNumber();
    }

    private void setMissed(String tid, Calendar missTimeAllowed) {
        mQRCodeImage.setImageBitmap(generateQRCode(tid));
        qrTopReminderText.setText(R.string.qr_top_reminder_text_miss);
        String reactivateInstructions = getString(R.string.qr_bottom_reminder_text_miss, missTimeAllowed);
        qrBottomReminderText.setText(Html.fromHtml(reactivateInstructions));
        showQRCode();
    }

    private void setReactivated(String tid) {
        setQueueNumber(tid);
        queueBottomReminderText.setText(R.string.queue_reminder_text_reactivated);
        showQueueNumber();
    }

    private void setAbsent() {
        qrTopReminderText.setText(R.string.qr_top_reminder_text_absent);
        qrBottomReminderText.setText(R.string.qr_bottom_reminder_text_no_pending);
        showNoPendingBooking();
    }

    private void setCompleted() {
        qrTopReminderText.setText(R.string.qr_top_reminder_text_completed);
        qrBottomReminderText.setText(R.string.qr_bottom_reminder_text_no_pending);
        showNoPendingBooking();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_booking_status_key))) {
            updateQueueActivity();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_create_booking) {
            startActivity(new Intent(this, ChooseClinic.class));
            this.finish();
        }
    }
}
