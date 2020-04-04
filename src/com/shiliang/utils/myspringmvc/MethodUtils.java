package com.shiliang.utils.myspringmvc;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodUtils {
    /**
     * 获取方法参数名列表
     */
    public static List<String> getMethodParamNames(Class<?> clazz, Method m) throws IOException {
        try (InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            return getParamNames(in,
                    new EnclosingMetadata(m.getName(),
                            jdk.internal.org.objectweb.asm.Type.getMethodDescriptor(m),
                            m.getParameterTypes().length));
        }
    }
    /**
     * 获取参数名列表辅助方法
     */
    private static List<String> getParamNames(InputStream in, EnclosingMetadata m) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.EXPAND_FRAMES);// 建议EXPAND_FRAMES
        // ASM树接口形式访问
        List<MethodNode> methods = cn.methods;
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < methods.size(); ++i) {
            List<LocalVariable> varNames = new ArrayList<LocalVariable>();
            MethodNode method = methods.get(i);
            // 验证方法签名
            if (method.desc.equals(m.desc) && method.name.equals(m.name)) {
                //System.out.println("desc->"+method.desc+":"+m.desc);
                List<LocalVariableNode> local_variables = method.localVariables;
                for (int l = 0; l < local_variables.size(); l++) {
                    String varName = local_variables.get(l).name;
                    // index-记录了正确的方法本地变量索引。(方法本地变量顺序可能会被打乱。而index记录了原始的顺序)
                    int index = local_variables.get(l).index;
                    if (!"this".equals(varName)) // 非静态方法,第一个参数是this
                        varNames.add(new LocalVariable(index, varName));
                }
                LocalVariable[] tmpArr = varNames.toArray(new LocalVariable[varNames.size()]);
                // 根据index来重排序，以确保正确的顺序
                Arrays.sort(tmpArr);
                for (int j = 0; j < m.size; j++) {
                    list.add(tmpArr[j].name);
                }
                break;
            }
        }
        return list;
    }

    /**
     * 方法本地变量索引和参数名封装
     */
    static class LocalVariable implements Comparable<LocalVariable> {
        public int index;
        public String name;

        public LocalVariable(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int compareTo(LocalVariable o) {
            return this.index - o.index;
        }
    }

    /**
     * 封装方法描述和参数个数
     */
    static class EnclosingMetadata {
        //method name
        public String name;
        // method description
        public String desc;
        // params size
        public int size;

        public EnclosingMetadata(String name, String desc, int size) {
            this.name = name;
            this.desc = desc;
            this.size = size;
        }
    }
}
