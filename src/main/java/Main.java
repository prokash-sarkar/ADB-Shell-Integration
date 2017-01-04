import com.vnetpublishing.java.suapp.SuperUserApplication;

/**
 * Created by Prokash Sarkar on 1/4/2017.
 */
public class Main extends SuperUserApplication {

    public Main() {

    }

    public static void main(String[] args) {
        //SU.run(new Main(), args);

        // copy the files
        CopyUtils.copyAdbFiles();

        // run any adb commands here. use & to append command
        ShellUtils.runProcess(true, "cd C:/temp & adb devices");
    }

    public int run(String[] strings) {
        System.out.println("RUN AS ADMIN!");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
