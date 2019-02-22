//package com.deedat.landsystem;
//
//import android.widget.ImageView;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//public class LoadingScreen {
//
//    private ImageView loading;
//
//    LoadingScreen(ImageView loading) {
//        this.loading = loading;
//    }
//
//    public void setLoadScreen(){
//        final Integer[] loadingImages = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round};
//        final Handler loadingHandler = new Handler() {
//            @Override
//            public void publish(LogRecord record) {
//
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public void close() throws SecurityException {
//
//            }
//        };
//        Runnable runnable = new Runnable() {
//            int loadingImgIndex = 0;
//            public void run() {
//                loading.setImageResource(loadingImages[loadingImgIndex]);
//                loadingImgIndex++;
//                if (loadingImgIndex >= loadingImages.length)
//                    loadingImgIndex = 0;
//                loadingHandler.postDelayed(this, 500);
//            }
//        };
//        loadingHandler.postDelayed(runnable, 500);
//    }
//}
