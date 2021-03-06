package kr.contextlogic.android.home;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

public class MemoryCache {
    private HashMap<String, SoftReference<Bitmap>> cache=new HashMap<String, SoftReference<Bitmap>>();
    
    public Bitmap get(String id){
       if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref=cache.get(id);
        if(ref == null) return null; //2011.11.16 ref NullPointerException
        return ref.get();
    }
    
    public void put(String id, Bitmap bitmap){
        cache.put(id, new SoftReference<Bitmap>(bitmap));
    }

    public void clear() {
        cache.clear();
    }
}