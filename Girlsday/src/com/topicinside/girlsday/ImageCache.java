package com.topicinside.girlsday;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ImageCache {
	private static HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
	
	public static Bitmap getImage(String key) {
		if(cache.containsKey(key)) {
			return cache.get(key);
		}
		return null;
	}
	
	public static void setImage(String key, Bitmap bmp) {
		cache.put(key, bmp);
	}
	
	public static void clearCache() {
		cache = null;
		cache = new HashMap<String, Bitmap>();
	}

}
