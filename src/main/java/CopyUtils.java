import java.io.*;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

/**
 * Created by Prokash Sarkar on 1/4/2017.
 */
public class CopyUtils {

    /**
     * copies adb files to windows system32 directory
     */
    public static synchronized void copyAdbFiles() {

        File s_adb;
        File s_adb_win_api;

        try {
            // get required files from resource directory
            s_adb = new File(ResourceLoader.getResource("adb.exe").toURI());
            s_adb_win_api = new File(ResourceLoader.getResource("AdbWinApi.dll").toURI());
        } catch (URISyntaxException e) {
            s_adb = new File(ResourceLoader.getResource("adb.exe").getPath());
            s_adb_win_api = new File(ResourceLoader.getResource("AdbWinApi.dll").getPath());
        }

        if (s_adb != null && s_adb_win_api != null) {

            File s_directory = new File(Constant.ADB_COPY_PATH);
            File d_adb = new File(Constant.ADB_COPY_PATH + s_adb.getName());
            File d_adb_win_api = new File(Constant.ADB_COPY_PATH + s_adb_win_api.getName());

            // if the directory does not exist, create it
            if (!s_directory.exists()) {
                System.out.println("creating directory: " + s_directory);
                boolean result = false;

                try {
                    s_directory.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    //handle it
                }
                if (result) {
                    System.out.println("DIR created");

                    long start = System.nanoTime();

                    try {
                        CopyUtils.copyFileUsingStream(s_adb, d_adb);
                        CopyUtils.copyFileUsingStream(s_adb_win_api, d_adb_win_api);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Time taken by Stream Copy = " + (System.nanoTime() - start));
                }
            }
        }
    }

    /**
     * @param source
     * @param dest
     * @throws IOException
     */

    public static synchronized void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    /**
     * @param source
     * @param dest
     * @throws IOException
     */
    public static synchronized void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }
}
