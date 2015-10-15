package yaodong;

import android.app.Application;
import yaodong.cache.FileCache;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FileCache.newInstance(getApplicationContext());
    }
}
