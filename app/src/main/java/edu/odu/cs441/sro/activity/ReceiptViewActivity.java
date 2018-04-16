package edu.odu.cs441.sro.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.io.File;
import java.io.IOException;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.intent.EmailReceiptIntent;
import edu.odu.cs441.sro.utility.data.StringPriceParser;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class ReceiptViewActivity extends AppCompatActivity {

    public static String RECEIPT_UNIQUE_IDENTIFIER = "RECEIPT_PRIMARY_KEY";

    private Receipt receipt;
    private ReceiptViewModel receiptViewModel;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    // Receipt Image file received from the MainActivity
    File mImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_view);

        Intent intent = getIntent();
        String receiptKey = intent.getStringExtra(RECEIPT_UNIQUE_IDENTIFIER);
        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);

        receipt = receiptViewModel.findByKey(receiptKey);

        if(receipt == null) {
            finish();
        }

        initializeTextViews();
        initializeButtons();

        if(receipt.getImageFilePath() != null) {
            mImageFile = new File(receipt.getImageFilePath());
            setThumbnailImage();
        }
    }

    private void initializeTextViews() {
        TextView titleTextView = findViewById(R.id.receipt_view_receipt_title);
        TextView dateTextView = findViewById(R.id.receipt_view_textview_date);
        TextView categoryTextView = findViewById(R.id.receipt_view_textview_value_category);
        TextView locationTextView = findViewById(R.id.receipt_view_textview_value_location);
        TextView methodTextView = findViewById(R.id.receipt_view_textview_value_method);
        TextView priceTextView = findViewById(R.id.receipt_view_textview_value_price);
        TextView commentTextView = findViewById(R.id.receipt_view_textview_value_comment);

        titleTextView.setText(receipt.getTitle());
        dateTextView.setText(new DateTime(receipt.getCreatedDate()).toString(DateTimeFormat.shortDateTime()));
        categoryTextView.setText(receipt.getCategory());
        locationTextView.setText(receipt.getLocation());
        methodTextView.setText(receipt.getMethod());
        priceTextView.setText(new StringPriceParser(receipt.getPrice()).getStringValue());
        commentTextView.setText(receipt.getComment());
    }

    private void initializeButtons() {
        Button closeButton = findViewById(R.id.receipt_view_button_close);
        Button sendButton = findViewById(R.id.receipt_view_button_send);
        Button splitButton = findViewById(R.id.receipt_view_button_split);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailReceipt();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void emailReceipt() {
        EmailReceiptIntent emailReceiptIntent = new EmailReceiptIntent(this);
        emailReceiptIntent.sendEmail(receipt);
    }

    /**
     * Method to set the thumbnail and enlarged image
     */
    private void setThumbnailImage() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        // Hook up clicks on the thumbnail views.
        Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(),bmOptions);

        try {
            final Bitmap mBitmap = modifyOrientation(bitmap, mImageFile.getAbsolutePath());


            final ImageButton imageButton = findViewById(R.id.receipt_view_image_button);

            imageButton.setImageBitmap(mBitmap);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zoomImageFromThumb(imageButton, mBitmap);
                }
            });

            // Retrieve and cache the system's default "short" animation time.
            mShortAnimationDuration = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);
        } catch(IOException e) {
            Toast.makeText(
                    this,
                    "Error reading receipt image file",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Originally, I had the issue where the orientation of the thumbnail and enlarged image did
     * not match the camera orientation and saved image orientation. This method is for fixing
     * the orientation issue.
     *
     * This code was copied and pasted from https://github.com/google/cameraview/issues/84
     *
     * - Michael Park -
     * @param bitmap Bitmap
     * @param image_absolute_path String
     * @return Bitmap corrected Bitmap
     * @throws IOException
     */
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    /**
     * Originally, I had the issue where the orientation of the thumbnail and enlarged image did
     * not match the camera orientation and saved image orientation. This method is for fixing
     * the orientation issue.
     *
     * This code was copied and pasted from https://github.com/google/cameraview/issues/84
     *
     * - Michael Park -
     * @param bitmap Bitmap
     * @param degrees float
     * @return Bitmap
     */
    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Originally, I had the issue where the orientation of the thumbnail and enlarged image did
     * not match the camera orientation and saved image orientation. This method is for fixing
     * the orientation issue.
     *
     * This code was copied and pasted from https://github.com/google/cameraview/issues/84
     *
     * - Michael Park -
     * @param bitmap Bitmap
     * @param horizontal boolean
     * @param vertical boolean
     * @return Bitmap
     */
    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * This is a method for zooming out when user clicks the thumbnail.
     * This was copied and pasted from https://developer.android.com/training/animation/zoom.html.
     * We do not need to look too much into this method.
     *
     *  - Michael Park -
     *
     * @param thumbView View
     * @param bm Bitmap
     */
    private void zoomImageFromThumb(final View thumbView, Bitmap bm) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.receipt_view_expanded_image);
        expandedImageView.setImageBitmap(bm);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.receipt_view_container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
