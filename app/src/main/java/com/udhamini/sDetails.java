package com.udhamini;

import android.os.AsyncTask;

/**
 * Created by brian on 26/Apr/2018.
 */

public class sDetails  extends AsyncTask<Void, Void, Void> {

    /**
     * `doInBackground` is run on a separate, background thread
     * (not on the main/ui thread). DO NOT try to update the ui
     * from here.
     */
    @Override
    protected Void doInBackground(Void... params) {
        //deleteSelectedGalleryItems();
        return null;
    }

    /**
     * `onPostExecute` is run after `doInBackground`, and it's
     * run on the main/ui thread, so you it's safe to update ui
     * components from it. (this is the correct way to update ui
     * components.)
     */
    @Override
    protected void onPostExecute(Void param) {
        //cleanupUiAfterCancelOrDelete();
        //galleryItemAdapter.notifyDataSetChanged();
    }
}
