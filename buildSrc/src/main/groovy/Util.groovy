import org.gradle.api.Project

class Util {
    static String readLocal(String key, Project project) {
        Properties properties = new Properties()
        File localFile = project.rootProject.file('local.properties')
        if (!localFile.exists()){
            return ""
        }
        properties.load(localFile.newDataInputStream())
        return properties.getProperty(key)
    }
}