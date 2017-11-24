package com.tzly.jianghw.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class PluginBuild implements Plugin<Project> {

    /**
     * 当默认是app，
     * 直接运行assembleRelease的时候，等同于运行app:assembleRelease操作
     */
    String compileModule = "app"

    void apply(Project project) {
        /**
         * "component"用于添加而外的属性，用于module中的build.gradle中
         */
        project.extensions.create('component', PluginExtension)
        String taskNames = project.gradle.startParameter.taskNames.toString()
        System.out.println("taskNames is ===>" + taskNames);//[assembleRelease]
        System.out.println("project.path is ===>" + project.path);//:CttxApp
        String currentModule = project.path.replace(":", "")
        System.out.println("current module is ===>" + currentModule);//CttxApp

        AssembleTask assembleTask = getTaskInfo(project.gradle.startParameter.taskNames)

        if (assembleTask.isAssemble) {
            fetchMainModuleName(project, assembleTask);
            System.out.println("compileModule  is ===>" + compileModule);
        }

        if (!project.hasProperty("isRunAlone")) {
            throw new RuntimeException("you should set isRunAlone in " + currentModule + "'s gradle.properties")
        }
        /**
         * 从module中的gradle.properties中获取isRunAlone
         *
         * isRunAlone==true根据实际情况修改其值
         * false-->运行module:assembleRelease则发布aar
         */
        boolean isRunAlone = Boolean.parseBoolean((project.properties.get("isRunAlone")))
        String mainModuleName = project.rootProject.property("mainModuleName")
        if (isRunAlone && assembleTask.isAssemble) {
            //对于要编译的组件和主项目，isRunAlone修改为true，其他组件都强制修改为false
            //这就意味着组件不能引用主项目，这在层级结构里面也是这么规定的
            isRunAlone = currentModule.equals(compileModule) || currentModule.equals(mainModuleName)
        }
        System.out.println("isRunAlone  is ===>" + isRunAlone);
        project.setProperty("isRunAlone", isRunAlone)

        //根据配置添加各种组件依赖，并且自动化生成组件加载代码
        if (isRunAlone) {
            project.apply plugin: 'com.android.application'
            if (!currentModule.equals(mainModuleName)) {
                project.android.sourceSets {
                    main {
                        manifest.srcFile 'src/main/alone/AndroidManifest.xml'
                        java.srcDirs = ['src/main/java', 'src/main/alone/java']
                        res.srcDirs = ['src/main/res', 'src/main/alone/res']
                    }
                }
            }
            System.out.println("apply plugin is ===>" + 'com.android.application');
            if (assembleTask.isAssemble && currentModule.equals(compileModule)) {
                compileComponents(assembleTask, project)
                project.android.registerTransform(new CodeTransform(project))
            }
        } else {
            project.apply plugin: 'com.android.library'
            System.out.println("apply plugin is " + 'com.android.library');
            project.afterEvaluate {
                Task assembleReleaseTask = project.tasks.findByPath("assembleRelease")
                if (assembleReleaseTask != null) {
                    assembleReleaseTask.doLast {
                        File infile = project.file("build/outputs/aar/$currentModule-release.aar")
                        File outfile = project.file("../componentRelease")
                        File desFile = project.file("$currentModule-release.aar");
                        project.copy {
                            from infile
                            into outfile
                            rename {
                                String fileName -> desFile.name
                            }
                        }
                        System.out.println("$currentModule-release.aar copy success ");
                    }
                }
            }
        }

    }

    /**
     * 根据task的名字对应生成 assembleTask
     * debug环境下时，isDebug = true
     * 添加要运行的module--> assembleTask.modules
     */
    private AssembleTask getTaskInfo(List<String> taskNames) {
        AssembleTask assembleTask = new AssembleTask();
        for (String task : taskNames) {
            if (task.toUpperCase().contains("ASSEMBLE")//assemble
                    || task.contains("aR")
                    || task.toUpperCase().contains("RESGUARD")) {//resguard
                if (task.toUpperCase().contains("DEBUG")) {//debug
                    assembleTask.isDebug = true;
                }
                assembleTask.isAssemble = true;
                String[] arrayOfStrings = task.split(":")
                assembleTask.modules.add(arrayOfStrings.length > 1
                        ? arrayOfStrings[arrayOfStrings.length - 2] : "all");
                break;
            }
        }
        return assembleTask
    }
    /**
     * 根据当前的task，获取要运行的主组件，规则如下：
     * assembleRelease ---app
     * 或从根目录gradle.properties中获取mainModuleName
     */
    private void fetchMainModuleName(Project project, AssembleTask assembleTask) {
        if (!project.rootProject.hasProperty("mainModuleName")) {
            throw new RuntimeException("you should set compileModule in rootproject's gradle.properties")
        }
        if (assembleTask.modules.size() > 0 && assembleTask.modules.get(0) != null
                && assembleTask.modules.get(0).trim().length() > 0
                && !assembleTask.modules.get(0).equals("all")) {
            compileModule = assembleTask.modules.get(0);
        } else {
            compileModule = project.rootProject.property("mainModuleName")
        }
        if (compileModule == null || compileModule.trim().length() <= 0) {
            compileModule = "app"
        }
    }

    /**
     * 自动添加依赖，只在运行assemble任务的才会添加依赖，因此在开发期间组件之间是完全感知不到的，这是做到完全隔离的关键
     * 支持两种语法：module或者modulePackage:module,
     * 前者之间引用module工程，后者使用componentrelease中已经发布的aar
     */
    private void compileComponents(AssembleTask assembleTask, Project project) {
        String components;
        if (assembleTask.isDebug) {
            components = (String) project.properties.get("debugComponent")
        } else {
            components = (String) project.properties.get("compileComponent")
        }
        if (components == null || components.length() == 0) {
            System.out.println("there is no add dependencies !!!");
            return;
        }
        String[] compileComponents = components.split(",")
        if (compileComponents == null || compileComponents.length == 0) {
            System.out.println("there is no add dependencies ~");
            return;
        }
        for (String str : compileComponents) {
            System.out.println("compile is Lib===>" + str);
            //当包含 ：时即为引用aar包
            if (str.contains(":")) {
                File file = project.file("../componentRelease/" + str.split(":")[1] + "-release.aar")
                if (file.exists()) {
                    project.dependencies.add("compile", str + "-release@aar")
                    //格式 compile(name: 'zbar-release', ext: 'aar')
                    System.out.println("add dependencies ===>" + str + "-release@aar");
                } else {
                    throw new RuntimeException(str + " not found ! maybe you should generate a new one ")
                }
            } else {//即为本地引用
                project.dependencies.add("compile", project.project(':' + str))
                //格式 compile project(':XRecyclerView')
                System.out.println("add dependencies project : " + str);
            }
        }
    }

    public class AssembleTask {
        boolean isAssemble = false;
        boolean isDebug = false;
        List<String> modules = new ArrayList<>();
    }

}

