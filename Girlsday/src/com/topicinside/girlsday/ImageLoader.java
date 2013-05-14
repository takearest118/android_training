/**
 * @author ung78.kim
 *
 */
package com.topicinside.girlsday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.ImageView;



public class ImageLoader {
    MemoryCache memoryCache=new MemoryCache();

    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    Context mContext;
    private final int mProgressDrawable;    
    private final int mDefaultDrawable; 
    private static File mCacheDir = null;

    BitmapFactory.Options opts = new BitmapFactory.Options();
    
    public ImageLoader(Context context, int defaultDrawable, String sCacheFolderPath ){
    	this(context, -1, defaultDrawable, sCacheFolderPath);
    }

    public ImageLoader(Context context, int progressDrawable, int defaultDrawable, String sCacheFolderPath) {
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY-1);
    	opts.inPreferredConfig = Bitmap.Config.RGB_565;

    	this.mContext = context;
        this.mProgressDrawable = progressDrawable;
        this.mDefaultDrawable = defaultDrawable;
        
        if (mCacheDir == null) {
        	mCacheDir = new File(sCacheFolderPath);
        }
        
        if (!mCacheDir.exists()){
        	mCacheDir.mkdirs();
        } else {
        	mCacheDir.delete();
        	mCacheDir.mkdirs();
        }
    }
    
    public void displayImage(String url, Activity activity, ImageView imageView)
    {
    	imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        } else {
        	String filename = String.valueOf(url.hashCode());
        	File file = new File(mCacheDir, filename);
        	if(file.exists()){
        		bitmap = decodeFile(file);
        	}
        	
        	if(bitmap!=null){
        		imageView.setImageBitmap(bitmap);
        	} else {
        		queuePhoto(url, activity, imageView);
        		imageView.setImageResource((mProgressDrawable != -1) ? mProgressDrawable : mDefaultDrawable);
        	}
        }     
    }
    
    private void queuePhoto(String url, Activity activity, ImageView imageView)
    {
        photosQueue.Clean(imageView);
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        synchronized(photosQueue.photosToLoad){
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }
        
        if(photoLoaderThread.getState()==Thread.State.NEW)
            photoLoaderThread.start();
    }
    
    private Bitmap getBitmap(String url) 
    {
    	String filename = String.valueOf(url.hashCode());
    	File f = new File(mCacheDir, filename);
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            copyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
           return null;
        }
    }

    private Bitmap decodeFile(File f){
       	int bHeight = 0;
    	int bWidth = 0;
    	int newWidth = 256;
        int newHeight = 256;
    	Bitmap b = null;
    	
    	Bitmap resizedBitmap = null;
    	
        if(f.exists() && f.isFile())
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            if ( b != null ) {
            	bHeight = b.getHeight();
                bWidth = b.getWidth();
                if ( bHeight > bWidth ) {
                	resizedBitmap = getNewHeight(bHeight, bWidth, b);
                	resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap, newWidth, newHeight, true);
                } else if ( bWidth > bHeight ) {
                	resizedBitmap = getNewWidth(bHeight, bWidth, b);
                	resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap, newWidth, newHeight, true);
                } else {
                	resizedBitmap = Bitmap.createScaledBitmap(b, newWidth, newHeight, true);	
                }
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        
        if(bWidth > 320 && bHeight > 320) {
            saveBitmap(resizedBitmap, f.toString(), CompressFormat.JPEG);
        } // end of if else.
        return resizedBitmap;
    }


	/**
	 * Bitmap�����濡����.
	 * 
	 * @param srcBitmap
	 * @param filePath
	 * @param format
	 */
	private void saveBitmap(Bitmap srcBitmap, String filePath, CompressFormat format){
		if (format == CompressFormat.PNG) {
			// nothing
		} else  {
			//if (format == CompressFormat.JPEG)
			// �곗� 諛곌꼍��源��以� (�щ���� �����寃�� �����臾몄� 蹂댁�)
			Bitmap backupBitmap =  Bitmap.createBitmap(srcBitmap);
			Canvas srcCanvas = new Canvas(srcBitmap);
			srcBitmap.eraseColor(0xFFFFFFFF);
			srcCanvas.drawBitmap(backupBitmap, 0, 0, null);
			backupBitmap.recycle();
			backupBitmap = null;
		}
		
		File fileCacheItem = new File(filePath);
		OutputStream out = null;

		try {
			fileCacheItem.createNewFile();
			out = new FileOutputStream(fileCacheItem);

			srcBitmap.compress(format, 100, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    private class PhotoToLoad
    {
    	public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u; 
            imageView=i;
        }
    }
    
    PhotosQueue photosQueue=new PhotosQueue();
    
    public void stopThread()
    {
        photoLoaderThread.interrupt();
    }
    
    class PhotosQueue {
        private Stack<PhotoToLoad> photosToLoad=new Stack<PhotoToLoad>();
        public void Clean(ImageView image)
        {
            for(int j=0 ;j<photosToLoad.size();j++){
                if(photosToLoad.get(j).imageView==image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }
    
    class PhotosLoader extends Thread {
        public void run() {
            try {
                while(true)
                {
                    if(photosQueue.photosToLoad.size()==0)
                        synchronized(photosQueue.photosToLoad){
                            photosQueue.photosToLoad.wait();
                        }
                    if(photosQueue.photosToLoad.size()!=0)
                    {
                        PhotoToLoad photoToLoad;
                        synchronized(photosQueue.photosToLoad){
                        	photoToLoad=photosQueue.photosToLoad.remove(0);
                            //photoToLoad=photosQueue.photosToLoad.pop();
                        }

                        Bitmap bmp = null;
                        bmp=getBitmap(photoToLoad.url);
                        
						if(bmp != null) {
	                        memoryCache.put(photoToLoad.url, bmp);
	                        String tag=imageViews.get(photoToLoad.imageView);
	                        if(tag!=null && tag.equals(photoToLoad.url)){
	                            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad.imageView);
	                            Activity a=(Activity)photoToLoad.imageView.getContext();
	                            a.runOnUiThread(bd);
	                        }
                        }
                    }
                    if(Thread.interrupted()){
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    PhotosLoader photoLoaderThread=new PhotosLoader();
    
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageView imageView;
        public BitmapDisplayer(Bitmap b, ImageView i){bitmap=b;imageView=i;}
        public void run()
        {
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
            } else {
            	imageView.setImageBitmap(null);
            }
        }
    }

    public void clearCache() {
        memoryCache.clear();

        //clear SD cache
        File[] files = mCacheDir.listFiles();
        if (files == null) return;
        if (files.length > 0) {
        	for(File f:files)
        		f.delete();
        }
    }

	public void copyStream(InputStream is, OutputStream os){
		final int buffer_size = 8192;//4096;//1024;
		try{
			byte[] bytes = new byte[buffer_size];
			for(;;)
			{
				int count= is.read(bytes, 0, buffer_size);
				if (count==-1)
					break;
				os.write(bytes,0,count);
			}
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	} // end of method.
	
    private Bitmap getNewHeight(int height, int width, Bitmap old) {
    	//Logger.d(TAG, "height : " + height + " width " + width);
    	int h = height - width;
    	int newStart = 0;
    	Bitmap newbitmap = null;
    	
    	newStart = h/2;
    	//Logger.d(TAG, "newStart " + newStart + " h " + h);
    	
    	newbitmap = Bitmap.createBitmap(old, 0, newStart, width, width);
    	
    	return newbitmap;
    }
    
    private Bitmap getNewWidth(int height, int width, Bitmap old) {
    	//Logger.d(TAG, "height : " + height + " width " + width);
    	int w = width - height;
    	int newStart = 0;
    	Bitmap newbitmap = null;
    	
    	newStart = w/2;
    	//Logger.d(TAG, "newStart " + newStart + " w " + w);
    	
    	newbitmap = Bitmap.createBitmap(old, newStart, 0, height, height);
    	
    	return newbitmap;
    }
}
