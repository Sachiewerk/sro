package edu.odu.cs441.sro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.utility.NumberTextWatcher;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

/**
 *
 */
public class PhotoReceiptAddActivity extends AppCompatActivity {

    // ViewModels
    ReceiptViewModel receiptViewModel;
    LocationViewModel locationViewModel;
    CategoryViewModel categoryViewModel;
    MethodViewModel methodViewModel;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    // Receipt Image file received from the MainActivity
    File mImageFile;

    // The UUID of this receipt, which is also part of the image file name
    UUID mUUID;


    private ArrayList<String> mLocationList;
    private ArrayList<String> mPriceList;
    private ArrayList<String> mCategoryList;
    private ArrayList<String> mMethodList;

    private ArrayAdapter<String> mLocationAutoCompleteAdapter;
    private ArrayAdapter<String> mPriceAutoCompleteAdapter;
    private ArrayAdapter<String> mCategoryAutoCompleteAdapter;
    private ArrayAdapter<String> mMethodAutoCompleteAdapter;

    private Date mDate;
    private EditText mTitleEditText;
    private AutoCompleteTextView mLocationAutoCompleteTextView;
    private AutoCompleteTextView mPriceAutoCompleteTextView;
    private AutoCompleteTextView mCategoryAutoCompleteTextView;
    private AutoCompleteTextView mMethodAutoCompleteTextView;
    private EditText mCommentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_receipt_add);

        // Obtain image files and UUID from the calling MainMethod and set the current date/time
        mImageFile =
                (File)getIntent().getSerializableExtra(MainActivity.MY_IMAGE_FILE_INTENT_IDENTIFIER);

        mUUID = (UUID)getIntent().getSerializableExtra(MainActivity.MY_UUID_INTENT_IDENTIFIER);

        mDate = new Date();

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);



        mLocationList = new ArrayList<> ();
        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location location : locations) {
                    mLocationList.add(location.getLocation());
                }
            }
        });

        mCategoryList = new ArrayList<> ();
        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mCategoryList.clear();
                for(Category category : categories) {
                    mCategoryList.add(category.getCategory());
                }
            }
        });


        mMethodList = new ArrayList<> ();
        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                mMethodList.clear();
                for(Method method : methods) {
                    mMethodList.add(method.getMethod());
                }
            }
        });


        mTitleEditText = findViewById(R.id.photo_receipt_add_edittext_title);
        mLocationAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.photo_receipt_add_autoCompleteTextview_location);
        mPriceAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.photo_receipt_add_autoCompleteTextview_price);
        mCategoryAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.photo_receipt_add_autoCompleteTextview_category);
        mMethodAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.photo_receipt_add_autoCompleteTextview_method);
        mCommentEditText = findViewById(R.id.photo_receipt_add_edittext_comment);


        mLocationAutoCompleteAdapter = new ArrayAdapter<>(
                this,android.R.layout.select_dialog_singlechoice, mLocationList);
        mCategoryAutoCompleteAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, mCategoryList);
        mMethodAutoCompleteAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, mMethodList);

        setThumbnailImage();
        setReceiptDate();
        initializeTitleViews();
        initializeAutoCompleteTextViews();
        initializeButtonEventListener();
    }

    private void setReceiptDate() {
        TextView dateView = findViewById(R.id.photo_receipt_add_textview_date);

        // Create a temporary DateTime for date formatting
        Date dateTime = new Date();
        dateView.setText(dateTime.toString());
    }

    private void initializeTitleViews() {
        final TextView titleView = findViewById(R.id.photo_receipt_add_receipt_title);

        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                titleView.setText(mTitleEditText.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing Necessary here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleView.setText(mTitleEditText.getText().toString());
            }
        });
    }

    private void initializeAutoCompleteTextViews() {

        // Add the currency TextWatcher for the price AutoCompleteTextView
        mPriceAutoCompleteTextView.addTextChangedListener
                (new NumberTextWatcher(mPriceAutoCompleteTextView));

        // Initialize the location AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                    mLocationAutoCompleteTextView,
                    mLocationAutoCompleteAdapter,
                    mLocationList
                );

        // Initialize the Category AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                    mCategoryAutoCompleteTextView,
                    mCategoryAutoCompleteAdapter,
                    mCategoryList
                );

        // Initialize the Method AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                    mMethodAutoCompleteTextView,
                    mMethodAutoCompleteAdapter,
                    mMethodList
                );
    }

    private void initializeAutoCompleteTextView
            (final AutoCompleteTextView acTextView,
             ArrayAdapter<String> adapter,
             ArrayList<String> list) {

        acTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View paramView) {
                    acTextView.showDropDown();

                return false;
            }
        });

        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);
    }

    private void initializeButtonEventListener() {
        final Button saveButton = findViewById(R.id.photo_receipt_add_Button_Save);
        final Button splitButton = findViewById(R.id.photo_receipt_add_Button_Split);
        final Button discardButton = findViewById(R.id.photo_receipt_add_Button_Discard);


        // What should happen when user clicks "Save" button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO Add the Receipt object created from this Activity
                String title = mTitleEditText.getText().toString();
                String category = mCategoryAutoCompleteTextView.getText().toString();
                String location = mLocationAutoCompleteTextView.getText().toString();
                String method = mMethodAutoCompleteTextView.getText().toString();
                String price = mPriceAutoCompleteTextView.getText().toString();
                String comment = mCommentEditText.getText().toString();

                price = price.replace("$", "").trim();
                Double doublePrice = Double.valueOf(price);
                BigDecimal bdPrice = BigDecimal.valueOf(doublePrice);

                Receipt receipt = new Receipt(mUUID.toString(), mDate, mImageFile.getAbsolutePath());
                receipt.setTitle(title);
                receipt.setCategory(category);
                receipt.setLocation(location);
                receipt.setMethod(method);
                receipt.setPrice(bdPrice);
                receipt.setComment(comment);

                receiptViewModel.insert(receipt);
                categoryViewModel.insert(new Category(category));
                locationViewModel.insert(new Location(location));
                methodViewModel.insert(new Method(method));

                setResult(RESULT_OK);
                finish();
            }
        });

        // What should happen when user clicks "Split" button
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO Duplicate the image file, assign a different name with different UUID
                //TODO and create another instance of this Activity. Use the same request code as
                //TODO in the MainActivity - MY_PHOTO_RECEIPT_ADD_ACTIVITY_REQUEST_CODE
            }
        });

        // What should happen when user clicks "Discard" button
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Delete the image file of this activity
                mImageFile.delete();

                // Set the result to canceled
                setResult(RESULT_CANCELED);

                // Close this activty and return to parent activity
                finish();
            }
        });
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


            final ImageButton imageButton = findViewById(R.id.photo_receipt_add_image_button);

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

            //TODO Use the default image
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
                R.id.photo_receipt_add_expanded_image);
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
        findViewById(R.id.photo_receipt_add_container)
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
