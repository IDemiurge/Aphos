package system.utils.old;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static final List<String> missing = new ArrayList<>();
    private static final Map<String, Boolean> fileCheckMap = new HashMap<>();
    private static final Map<String, Boolean> directoryCheckMap = new HashMap<>();
    private static final Map<String, List<File>> folderCache = new HashMap<>();
    private static final Map<String, List<File>> variantCache = new HashMap<>();

    public static String readFile(String filePath) {
        File file = getFile(filePath, true, false);
        return readFile(file);
    }

    public static List<String> readFileLines(String filePath) {
        File file = getFile(filePath);
        return readFileLines(file);
    }

    public static List<String> readFileLines(File file) {
        String string = readFile(file, "\n");
        return ContainerUtils.openContainer(string, "\n");
    }

    public static String readFile(File file) {
        return readFile(file, "");
    }

    public static String readFile(File file, String lineSeparator) {
        if (!isFile(file)) {
            return "";
            // if (Flags.isJar()) {
            //     if (!Flags.isWindows()) {
            //         String lowerCase = file.getPath().toLowerCase();
            //         if (!lowerCase.equals(file.getPath()))
            //             return readFile(FileManager.getFile(file.getPath().toLowerCase()));
            //
            //     }
            //     SysLog.printOut("Failed to read " , file.getPath());
                //              TODO wtf  try {
                //                    throw new RuntimeException();
                //                } catch (Exception e) {
                //                    main.system.ExceptionMaster.printStackTrace(e);
                //                }
                // return "";
            // }
            //TODO
            // if (!PathUtils.fixSlashes(file.getPath()).toLowerCase().contains(PathFinder.getRootPath().toLowerCase()))
            //     return readFile(FileManager.getFile(PathFinder.getRootPath() + file.getPath()),
            //             lineSeparator);
            // return "";
        }
        String result = "";

        //        Charset charset= Charset.availableCharsets().get("Windows-1251");
        //        if (charset==null )
        //            charset= Charset.defaultCharset();
        try {
            result = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
           e.printStackTrace();
        }

        return result;
    }
    public static boolean isFile(String file) {
        Boolean result = fileCheckMap.get(file);
        if (result != null) {
            return result;
        }
        result = isFile(FileManager.getFile(file));
        fileCheckMap.put(file, result);
        return result;
    }

    public static boolean isFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return false;
        }
        return file.isFile();
    }

    public static boolean isDirectory(String file) {
        Boolean result = directoryCheckMap.get(file);
        if (result != null) {
            return result;
        }
        result = isDirectory(FileManager.getFile(file));
        directoryCheckMap.put(file, result);
        return result;
    }

    public static boolean isDirectory(File file) {
        if (file == null) {
            return false;
        }
        return file.isDirectory();
    }

    public static File getFile(String path) {
        return getFile(path, true);
    }

    public static File getFile(String path, boolean first) {
        return getFile(path, first, true);
    }

    private static String formatPath(String path) {
        return path;
    }

    public static File getFile(String path, boolean first, boolean allowInvalid) {
        File file = new File(path);
        if (file.isFile() || file.isDirectory()) {
            return file;
        }
        if (first) {
            path = formatPath(path);
            file = getFile((path), false);
            if (file.isFile() || file.isDirectory()) {
                return file;
            }
            // if (!Flags.isActiveTestMode())
            //     if (!Flags.isFullFastMode()) {
            //         if (!missing.contains(file.getPath())) {
            //             main.system.auxiliary.log.LogMaster.verbose("FILE NOT FOUND: " + file);
            //             missing.add(file.getPath());
            //         }
            //     }
        }

        return file;
    }


    public static long getDateCreated(File file) {
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        // System.out.println("lastAccessTime: " + attr.lastAccessTime());
        // System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
        return attr.creationTime().toMillis();
    }


    // public static String formatPath(String path) {
    //     return formatPath(path, false);
    // }

    // public static String formatPath(String path, boolean force, boolean removeLastSlash) {
    //     String v = formatPath(path, force);
    //     if (removeLastSlash) {
    //         if (v.endsWith("/")) {
    //             return v.substring(0, v.length() - 1);
    //         }
    //     }
    //     return v;
    // }


    // public static String formatPath(String path, boolean force) {
    //     StringBuilder formatted = new StringBuilder();
    //     int index = path.lastIndexOf(PathFinder.getRootPath(), PathFinder.getRootPath().length() - 1);
    //     if (index == -1 && !force) {
    //         return path.toLowerCase();
    //     }
    //     if (index == 0 && !force) {
    //         index += PathFinder.getRootPath().length() - 1;
    //     }
    //     String afterClass = force ? path : path.substring(
    //             index);
    //
    //     //fix slashes
    //     if (!afterClass.isEmpty()) {
    //         for (String sub : PathUtils.splitPath(afterClass)) {
    //             formatted.append(StringMaster.replace(true, sub, "/", "")).append("/");
    //         }
    //     }
    //     if (force) {
    //         return formatted.toString().toLowerCase();
    //     }
    //     if (!Flags.isWindows()) {
    //         return (PathFinder.getRootPath() + formatted.toString().toLowerCase())
    //                 .replace("%20", " ");
    //     }
    //     //fix case
    //     return PathFinder.getRootPath() + formatted.toString().toLowerCase();
    // }


}
