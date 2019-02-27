import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */

public class FileHelper {
    public static String writeStoryImage(@NonNull final Context context, final int imageResourceId) throws IOException {
        final int bufferSize = 1024;
        InputStream ins = null;
        FileOutputStream fos = null;
        String outputFileName;
        try {
            ins = context.getResources().openRawResource(imageResourceId);

            final TypedValue value = new TypedValue();
            context.getResources().getValue(imageResourceId, value, true);
            final String imageFileName =  value.string == null ? null : value.string.toString();
            String extension = null;
            if(imageFileName != null)
                extension = getExtension(imageFileName);
            if(extension == null)
                extension = ".jpg";

            final File diskCacheDir = new File(context.getCacheDir(), "story");

            if (!diskCacheDir.exists() && !diskCacheDir.mkdirs()) {
                Log.e("FileHelper", "failed to mkdir: " + diskCacheDir.getPath());
            }

            outputFileName = diskCacheDir.getAbsolutePath() + File.separator + "temp_" + System.currentTimeMillis() + extension;

            fos = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[bufferSize];
            int read;
            while((read = ins.read(buffer)) != -1){
                fos.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ins != null)
                    ins.close();
                if (fos != null)
                    fos.close();
            } catch (Throwable ignored) {
            }
        }

        return outputFileName;
    }

    private static String getExtension(String fileName) {
        String ext = null;
        int i = fileName.lastIndexOf('.');
        int endIndex = fileName.lastIndexOf("?");

        if (i > 0 && i < fileName.length() - 1) {
            Locale curLocale = Locale.getDefault();
            if (endIndex < 0) {
                ext = fileName.substring(i).toLowerCase(curLocale);
            } else {
                ext = fileName.substring(i, endIndex).toLowerCase(curLocale);
            }
        }
        return ext;
    }
}
