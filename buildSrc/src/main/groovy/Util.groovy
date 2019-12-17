import org.gradle.api.Project

class Util {
    static String readLocal(String key, Project project) {
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())
        return properties.getProperty(key)
    }
}