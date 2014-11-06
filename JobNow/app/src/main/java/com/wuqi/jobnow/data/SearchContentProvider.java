package com.wuqi.jobnow.data;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;

public class SearchContentProvider extends SearchRecentSuggestionsProvider {

    static final String TAG = "com.wuqi.jobnow";
    public static final String AUTHORITY = "com.wuqi.jobnow.search_content_provider";

    public static final int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;
    private static final String[] COLUMNS = {
            "_id", // must include this column
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_ICON_1,
            SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA};

    public SearchContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String query = uri.getLastPathSegment().toLowerCase();
        if (query.length() == 0) {
            return null;
        }

        final MatrixCursor cursor = new MatrixCursor(COLUMNS);
        OfferSearchResult result = JobnowApplication.getInstance().getApi().getOffersByKeywords(query);
        int id = 0;
        for (Offer o : result.result) {
            cursor.addRow(new Object[]{
                    id, o.short_description, R.drawable.categoria0, o.short_description + "--,--" + o.price + "--,--" +  o.long_description + "--,--" +  String.valueOf(o.lat) + "--,--" +  String.valueOf(o.lng)
            });
            id++;
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
