package synthesejava;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassHackThing {
    private static final URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
    private static Class sysclass;
//    private static final Class<URLClassLoader> URLCLASSLOADER = (URLClassLoader)ClassLoader.getSystemClassLoader();
    private static final Class<?>[] PARAMS = new Class[] { URL.class };
    private static URLClassLoader urlClassLoader;
    private static Method addUrlMethod;

    public static void addFile(final String s) throws IOException {
        addFile(new File(s));
    }

    public static void addFile(final File f) throws IOException {
        addURL(f.toURI().toURL());
    }

    public static void addURL(final URL u) throws IOException {
        final URLClassLoader urlClassLoader = getUrlClassLoader();
        try {
            final Method method = getAddUrlMethod();
            method.setAccessible(true);
            method.invoke(urlClassLoader, new Object[] { u });
        } catch (final Exception e) {
            e.printStackTrace();
            //            throw new IOException("Error, could not add URL to system classloader");
        }
    }

    private static Method getAddUrlMethod() throws NoSuchMethodException {
        try {
            sysclass = Class.forName("MaCreature");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (addUrlMethod == null) {
            addUrlMethod = sysclass.getDeclaredMethod("testage");
        }
        return addUrlMethod;
    }

    private static URLClassLoader getUrlClassLoader() {
        if (urlClassLoader == null) {
            final ClassLoader sysloader = ClassLoader.getSystemClassLoader();
            if (sysloader instanceof URLClassLoader) {
                urlClassLoader = (URLClassLoader)sysloader;
            } else {
                throw new IllegalStateException("Not an UrlClassLoader: " + sysloader);
            }
        }
        return urlClassLoader;
    }
}
