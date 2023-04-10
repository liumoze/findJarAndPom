
import java.io.File;
import java.text.ParseException;
import java.util.*;

public class test {

    private static ArrayList filelist = new ArrayList();

    public static void main(String[] args) throws ParseException {
        refreshFileList("C:\\Users\\12639\\Desktop\\rocketmq");
    }


    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getAbsolutePath());
            } else {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                //筛选有jar和有pom的
                if((strFileName.endsWith(".jar")) && !strFileName.contains("sources.jar")){
                    //如果有jar，看是否存在pom文件
                    String pomName = strFileName.substring(0,strFileName.length()-4)+".pom";
                    File file = new File(pomName);
                    if(file.exists()){
                        System.out.println("mvn deploy:deploy-file  -Dpackaging=jar  -Dfile="+strFileName+" -DpomFile="+pomName+" -DrepositoryId=sd_oss_zhgj-release-maven-local  -Durl=https://yd-artifact.srdcloud.cn/artifactory/sd_oss_zhgj-release-maven-local");
                    }
                }

                //筛选有pom但无jar的
                if((strFileName.endsWith(".pom"))){
                    //如果有pom，看是否存在jar文件
                    String jarName = strFileName.substring(0,strFileName.length()-4)+".jar";
                    File file = new File(jarName);
                    if(!file.exists()){
                        System.out.println("mvn deploy:deploy-file  -Dfile="+strFileName+" -DrepositoryId=sd_oss_zhgj-release-maven-local  -Durl=https://yd-artifact.srdcloud.cn/artifactory/sd_oss_zhgj-release-maven-local -DpomFile="+strFileName+" -Dpackaging=pom");
                    }
                }
                filelist.add(files[i].getAbsolutePath());
            }
        }
    }
}
