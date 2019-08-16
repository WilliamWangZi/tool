package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetModifyFileUtils {

//    public static void main(String[] args) {
//
//        getFile("class",
//                "D:\\ideaProject\\SaasApi2.4.10\\target\\api\\WEB-INF\\classes",
//                "D:\\demo\\",
//                new DateTime(2019, 8, 16, 19, 57, 0, 0).toDate().getTime());
//    }

    public static void getFile(String source, String target, long dateTime){
		getFile(null, source, target, dateTime);
	}

    public static void getFile(String suffix, String source, String target, long dateTime){
        File file = new File(source);
        List<File> fileList = new ArrayList<>();
        aroundFile(fileList, file, suffix);
        fileList.stream()
                .filter(file1 -> file1.lastModified() > dateTime)
                .collect(Collectors.toList())
                .forEach(file12 -> {
					String pathFile = file12.getAbsolutePath().replace(source, target);
                    try (FileInputStream fis  = new FileInputStream(file12)){
                        Path path = Paths.get(pathFile);
                        byte[] b = new byte[Integer.valueOf(String.valueOf(file12.length()))];
                        fis.read(b);
                        FileUtils.forceMkdirParent(new File(pathFile));
                        Files.write(path, b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    public static void aroundFile(List<File> fileList, File file, String suffix){
        File[] files = file.listFiles();
        if(files != null){
            for (File f : files) {
                if(f.isFile()){
					if(suffix != null && !f.getName().contains(suffix)){
						continue;
					}
                    fileList.add(f);
                }else{
                    aroundFile(fileList, f, suffix);
                }
            }
        }
    }
}
