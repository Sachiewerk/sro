package edu.odu.cs441.sro.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;

/**
 * Created by michael on 3/15/18.
 */

public class ReceiptBaseAdapter extends BaseAdapter {
    private final String MY_LOCATION_LABEL = "Location: ";
    private final String MY_PRICE_LABEL = "Subtotal: ";
    private final String MY_DATE_LABEL = "Date: ";

    private Context mContext;
    private List<Receipt> mReceiptCollection;

    public ReceiptBaseAdapter(Context context) {
        mReceiptCollection = new ArrayList<>();
        mContext = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDate;
        TextView txtPrice;
        TextView txtLocation;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_row_item, null);
            holder = new ViewHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.custom_listview_row_item_receipt_date);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.custom_listview_row_item_receipt_title);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.custom_listview_row_item_receipt_price);
            holder.txtLocation = (TextView) convertView.findViewById(R.id.custom_listview_row_item_receipt_location);
            holder.imageView = (ImageView) convertView.findViewById(R.id.custom_listview_row_item_icon);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Receipt receipt = (Receipt) getItem(position);

        holder.txtDate.setText(MY_DATE_LABEL + receipt.getCreatedDate());
        holder.txtTitle.setText(receipt.getTitle());
        holder.txtPrice.setText(MY_PRICE_LABEL + receipt.getPrice());
        holder.txtLocation.setText(MY_LOCATION_LABEL + receipt.getLocation());

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        // Hook up clicks on the thumbnail views.
        Bitmap bitmap = BitmapFactory.decodeFile(receipt.getImageFilePath(), bmOptions);

        try {
            final Bitmap mBitmap = modifyOrientation(bitmap, receipt.getImageFilePath());


            holder.imageView.setImageBitmap(mBitmap);

        } catch(IOException e) {
            Toast.makeText(
                    mContext,
                    "Error reading receipt image file",
                    Toast.LENGTH_LONG).show();

            //TODO Use the default image
        }

        return convertView;
    }

    public void setItems(List<Receipt> receipts) {
        mReceiptCollection.clear();
        mReceiptCollection.addAll(receipts);
    }

    @Override
    public int getCount() {
        return mReceiptCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return mReceiptCollection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mReceiptCollection.indexOf(getItem(position));
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
    public Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
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
    public Bitmap rotate(Bitmap bitmap, float degrees) {
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
    public Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}