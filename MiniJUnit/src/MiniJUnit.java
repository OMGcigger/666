import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 自定义的 MiniJUnit 测试框架，用于自动发现并运行测试方法
 */
@Retention(RetentionPolicy.RUNTIME)
@interface Test {}

/**
 * 这是一个示例类
 * @author xqf
 * @version 1.0
 * @since 19 March 2025
 */
public class MiniJUnit {
    /**
     * 扫描所有类，找到符合条件的测试方法，并执行它们
     *
     * @throws Exception 如果类加载失败或方法调用失败，则抛出异常
     */
    public static void main(String[] args) throws Exception {
        List<Class<?>> classes = findAllClasses(); // 获取所有类
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().startsWith("test") || method.isAnnotationPresent(Test.class)) {
                    System.out.println("Running: " + method.getName());
                    method.invoke(clazz.getDeclaredConstructor().newInstance());
                }
            }
        }
    }

    /**
     * 扫描当前目录下的所有.class文件并加载它们
     *
     * 得到类对象数组。
     * @return 返回所有class对象列表
     * @throws Exception 如果文件操作失败或类加载失败，则抛出异常
     */
    private static List<Class<?>> findAllClasses() throws Exception {
        List<Class<?>> classList = new ArrayList<>();
        File currentDir = new File("C:\\Users\\ROG\\IdeaProjects\\MiniJUnit\\out");
        if (currentDir.exists()) {
            load(currentDir, classList);
        }
        return classList;
    }

    /**
     * 递归读取当前文件夹下的所有.class文件，并转换为类对象
     *
     * @param directory 目标目录，可以包含.class的文件或子目录
     * @param classList 存储所有加载类对象的列表
     * @throws Exception 如果类无法加载或文件读取失败，则抛出异常
     */
    private static void load(File directory, List<Class<?>> classList) throws Exception {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                load(file, classList);
            } else if (file.getName().endsWith(".class")) {
                String className =  file.getName().replace(".class", "");
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{directory.toURI().toURL()});
                Class<?> clazz = Class.forName(className,true,classLoader);
                classList.add(clazz);
            }
        }
    }
}



