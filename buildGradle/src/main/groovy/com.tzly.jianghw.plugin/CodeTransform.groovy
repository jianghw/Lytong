package com.tzly.jianghw.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class CodeTransform extends Transform {

    private Project project
    ClassPool classPool
    String applicationName;

    CodeTransform(Project project) {
        this.project = project
    }

    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {

        getRealApplicationName(transformInvocation.getInputs());

        classPool = new ClassPool()
        project.android.bootClasspath.each {
            classPool.appendClassPath((String) it.absolutePath)
        }
        def box = ConvertUtils.toCtClasses(transformInvocation.getInputs(), classPool)

        //要收集的application，一般情况下只有一个
        List<CtClass> applicationList = new ArrayList<>();
        //要收集的applicationlikes，一般情况下有几个组件就有几个applicationlike
        List<CtClass> likeList = new ArrayList<>();

        for (CtClass ctClass : box) {
            if (isApplication(ctClass)) {
                applicationList.add(ctClass)
                continue;
            }
            if (isActivator(ctClass)) {
                likeList.add(ctClass)
            }
        }
        for (CtClass ctClass : applicationList) {
            System.out.println("application is ===>" + ctClass.getName());
        }
        for (CtClass ctClass : likeList) {
            System.out.println("applicationLike is ===>" + ctClass.getName());
        }

        transformInvocation.inputs.each { TransformInput input ->
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput ->
                //jar文件一般是第三方依赖库jar文件
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
            }

            //对类型为“文件夹”的input进行遍历 是否为自动注册时
            input.directoryInputs.each { DirectoryInput directoryInput ->
                boolean isRegisterCompoAuto = project.extensions.component.isRegisterCompoAuto
                if (isRegisterCompoAuto) {
                    String fileName = directoryInput.file.absolutePath
                    File dir = new File(fileName)
                    dir.eachFileRecurse { File file ->
                        String filePath = file.absolutePath
                        String classNameTemp = filePath.replace(fileName, "").replace("\\", ".").replace("/", ".")
                        if (classNameTemp.endsWith(".class")) {
                            String className = classNameTemp.substring(1, classNameTemp.length() - 6)
                            if (className.equals(applicationName)) {
                                injectApplicationCode(applicationList.get(0), likeList, fileName);
                            }
                        }
                    }
                }
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes, Format.DIRECTORY)
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }

    /**
     * 获取 application 名字
     * 从每个module的build.gradle 中获取
     * @param inputs
     */
    private void getRealApplicationName(Collection<TransformInput> inputs) {
        applicationName = project.extensions.component.applicationName
        if (applicationName == null || applicationName.isEmpty()) {
            throw new RuntimeException("you should set applicationName in component")
        }
    }
    /**
     * 是否是 application
     */
    private boolean isApplication(CtClass ctClass) {
        try {
            if (applicationName != null && applicationName.equals(ctClass.getName())) {
                return true;
            }
        } catch (Exception e) {
            println "class not found exception class name:  " + ctClass.getName()
        }
        return false;
    }
    /**
     * 从componentRouter 中获取到接口IApplicationLike
     */
    private boolean isActivator(CtClass ctClass) {
        try {
            for (CtClass ctClassInter : ctClass.getInterfaces()) {
                if ("com.tzly.ctcyh.router.IApplicationLike".equals(ctClassInter.name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            println "class not found exception class name:  " + ctClass.getName()
        }

        return false;
    }

    private void injectApplicationCode(CtClass ctClassApplication, List<CtClass> activators, String patch) {
        System.out.println("injectApplicationCode begin");
        ctClassApplication.defrost();
        try {
            CtMethod attachBaseContextMethod = ctClassApplication.getDeclaredMethod("onCreate", null)
            attachBaseContextMethod.insertAfter(getAutoLoadComCode(activators))
        } catch (CannotCompileException | NotFoundException e) {
            StringBuilder methodBody = new StringBuilder();
            methodBody.append("protected void onCreate() {");
            methodBody.append("super.onCreate();");
            methodBody.append(getAutoLoadComCode(activators));
            methodBody.append("}");
            ctClassApplication.addMethod(CtMethod.make(methodBody.toString(), ctClassApplication));
        } catch (Exception e) {
            e.printStackTrace()
        }
        ctClassApplication.writeFile(patch)
        ctClassApplication.detach()

        System.out.println("injectApplicationCode success ");
    }


    private String getAutoLoadComCode(List<CtClass> activators) {
        StringBuilder autoLoadComCode = new StringBuilder();
        for (CtClass ctClass : activators) {
            autoLoadComCode.append("new " + ctClass.getName() + "()" + ".onCreate();")
            println "Auto Load class name:====>" + ctClass.getName()
        }
        return autoLoadComCode.toString()
    }

    @Override
    String getName() {
        return "ComponentCode"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

}