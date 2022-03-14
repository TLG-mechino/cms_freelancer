package vn.compedia.website.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UnzipUtil {

    private static final Logger logger = LoggerFactory.getLogger(UnzipUtil.class);

    public static boolean unzipFile(String zipFilePath) {
        String cmdStr = "unzip -d " + zipFilePath.replace(".zip", "") + " " + zipFilePath;

        System.out.println("==============================================================================================================");
        System.out.println(cmdStr);
        System.out.println("==============================================================================================================");

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            cmdStr = "cmd /c " + cmdStr;
        }
        try {
            Process process = Runtime.getRuntime().exec(cmdStr);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return true;
            } else {
                logger.info(String.valueOf(output));
                logger.error("Can not resize file: " + zipFilePath);
                return false;
            }

        } catch (Exception e) {
            logger.warn("unzipFile error: ", e);
            return false;
        }
    }
}
