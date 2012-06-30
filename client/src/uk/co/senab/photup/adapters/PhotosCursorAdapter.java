package uk.co.senab.photup.adapters;

import uk.co.senab.photup.cache.BitmapLruCache;
import uk.co.senab.photup.views.MultiChoiceGridView;
import uk.co.senab.photup.views.PhotoItemLayout;
import uk.co.senab.photup.views.PhotupImageView;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.Checkable;

public class PhotosCursorAdapter extends ResourceCursorAdapter {

	private final BitmapLruCache mCache;
	private MultiChoiceGridView mParent;

	public PhotosCursorAdapter(Context context, BitmapLruCache cache, int layout, Cursor c, boolean autoRequery) {
		super(context, layout, c, autoRequery);
		mCache = cache;
	}

	public void setParentView(MultiChoiceGridView gridView) {
		mParent = gridView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		PhotoItemLayout layout = (PhotoItemLayout) view;
		PhotupImageView iv = layout.getImageView();

		long id = cursor.getInt(cursor.getColumnIndexOrThrow(ImageColumns._ID));
		iv.requestThumbnailId(id, mCache);

		if (null != mParent) {
			((Checkable) view).setChecked(mParent.isItemIdChecked(id));
		}
	}

}