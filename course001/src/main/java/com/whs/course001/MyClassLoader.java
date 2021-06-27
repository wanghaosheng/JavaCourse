package com.whs.course001;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("course001/src/main/resources/Hello.xlass");
        byte[] content = null;
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while((len = fileInputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer,0,len);
            }
            byteArrayOutputStream.flush();
            content = byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] newContent = new byte[content.length];
        for (int i = 0; i < content.length; i++) {
            newContent[i] = (byte)(255 - content[i]);
        }
        return defineClass(name,newContent,0,newContent.length);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        try {
            Class hello = myClassLoader.findClass("Hello");
            Method method = hello.getMethod("hello");
            method.invoke(hello.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
