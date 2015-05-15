package synthesejava;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LibHandler {
    private Class klass;
    private File path;
    private Method construct;
    private String pkg;

    public LibHandler(File path, String pkg) {
        this.path = path;
        this.pkg = pkg;
//        try {
////            URLClassLoader child =
////                new URLClassLoader(new , this.getClass().getClassLoader());
////            Class classToLoad = Class.forName("MaCreature", true, child);
////            Method method = classToLoad.getDeclaredMethod("MaCreature");
////            Object instance = classToLoad.newInstance();
////            Object result = method.invoke(instance);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
