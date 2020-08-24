import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle

class MyConfigUtils {
    static addBuildListener(Gradle g) {
        g.addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {
                GLog.d("buildStarted")
            }

            @Override
            void settingsEvaluated(Settings settings) {
                // hook settings.gradle
                GLog.d("settingsEvaluated")
            }

            @Override
            void projectsLoaded(Gradle gradle) {
                GLog.d("projectsLoaded")
                gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
                    // 可以在这里 hook 每个 module
                    @Override
                    void beforeEvaluate(Project project) {
                        GLog.d("projectsLoaded beforeEvaluate ${project.name} ${project.subprojects.isEmpty()}")
                    }

                    @Override
                    void afterEvaluate(Project project, ProjectState projectState) {
                        GLog.d("projectsLoaded afterEvaluate")
                    }
                })
            }

            @Override
            void projectsEvaluated(Gradle gradle) {
                GLog.d("projectsEvaluated")
            }

            @Override
            void buildFinished(BuildResult buildResult) {
                GLog.d("buildFinished")
            }
        })
    }
}