package robin.scaffold.baisc.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TimberHelper implements Foreground.Listener{
    private static final String TAG = TimberHelper.class.getSimpleName();
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static String sDefaultDir;// log 默认存储目录
    private static String sFilePrefix = "error";// log 文件前缀
    private static ExecutorService sExecutor;
    private static TimberHelper instance;
    private static Application sApplication;

    private TimberHelper(){}

    public static TimberHelper getInstance() {
        if(instance == null) {
            synchronized (TimberHelper.class) {
                if(instance == null) {
                    instance = new TimberHelper();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        sApplication = application;
        Foreground.get(sApplication).addListener(this);
    }

    public void print2File(final String msg) {
        final String fullPath = getFilePath();
        if (!createOrExistsFile(fullPath)) {
            Log.e(TAG, "createOrExistsFile to " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(msg).append(LINE_SEP);
        final String content = sb.toString();
        if (asyncInput2File(content, fullPath)) {
            Log.d(TAG, "log to " + fullPath + " success!");
        } else {
            Log.e(TAG, "log to " + fullPath + " failed!");
        }
    }

    private boolean asyncInput2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            boolean isCreate = file.createNewFile();
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private String getFilePath() {
        return getDefaultDir(sApplication.getApplicationContext()) + sFilePrefix + ".txt";
    }

    private String getDefaultDir(Context context) {
        if (sDefaultDir != null) return sDefaultDir;
        if (SDCardUtil.checkSDCardMounted()) {
            sDefaultDir = context.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
        } else {
            sDefaultDir = context.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
        }
        return sDefaultDir;
    }

    private void upload() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        String data = FileUtil.readStringFromFile(getFilePath());
        if(TextUtils.isEmpty(data)) {
            return;
        }
        map.put("data", data);

    }

    @Override
    public void onBecameForeground() {
        Log.d(TAG, "onBecameForeground");
    }

    @Override
    public void onBecameBackground() {
        Log.d(TAG, "onBecameBackground");
        upload();
    }
}

