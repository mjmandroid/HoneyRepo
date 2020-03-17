package com.beautystudiocn.allsale.autoupdate.product.custom.download;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.beautystudiocn.allsale.autoupdate.product.custom.CustomUpdateProduct;
import com.beautystudiocn.allsale.util.FileUtil;

/**
 * <br> ClassName:   DefaultDownLoader
 * <br> Description: 默认下载器
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:53
 */
public class DefaultDownLoader extends AbstractDownLoader {
    private Handler handler = new Handler();

    /**
     * <br> Description: 初始化
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/6/8 17:54
     *
     * @param listener 下载监听器
     */
    public DefaultDownLoader(IDownLoadListener listener) {
        super(listener);
    }

    @Override
    public boolean startDownLoad(String url, final String path) {
        if (isUpdate()) {
            mDownLoadListener.onDownLoadFail(CustomUpdateProduct.ERROR_IS_UPDATING);
            return false;
        } else {
            setIsUpdate(true);
        }
        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mDownLoadListener.onDownLoadStart();
            }

            @Override
            protected String doInBackground(String... params) {
                String urlStr = params[0];
                InputStream in = null;
                FileOutputStream out = null;
                File apkFile = null;
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.setConnectTimeout(10 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

                    urlConnection.connect();
                    long byteTotal = urlConnection.getContentLength();
                    long byteSum = 0;
                    in = urlConnection.getInputStream();
                    String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length()).trim();
                    apkFile = FileUtil.createNewFile(path, apkName);
                    out = new FileOutputStream(apkFile);
                    byte[] buffer = new byte[10 * 1024];

                    int oldProgress = 0;

                    int byteRead = 0;
                    while ((byteRead = in.read(buffer)) != -1) {
                        byteSum += byteRead;
                        out.write(buffer, 0, byteRead);

                        int progress = (int) (byteSum * 100L / byteTotal);
                        if (progress != oldProgress) {
                            publishProgress(progress);
                        }
                        oldProgress = progress;
                    }

                    return apkFile.getAbsolutePath();
                } catch (IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownLoadListener.onDownLoadFail(CustomUpdateProduct.ERROR_HTTP_ERROR);
                        }
                    });
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                mDownLoadListener.onDownLoadProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String file) {
                super.onPostExecute(file);
                if (!TextUtils.isEmpty(file)) {
                    mDownLoadListener.onDownLoadFinish(file);
                }
                setIsUpdate(false);
            }

            /**
             * Trust every server - dont check for any certificate
             */
        }.execute(url);
        return true;
    }
}
