package uk.co.probablyfine.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildInfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.tasks.create('generateBuildInfo') {

            File buildInfoFile = createBuildInfoFile(project)

            generateBuildInfoFile(project).each { key, value ->
                buildInfoFile << "${key}=${value}\n"
            }


        }
    }

    private static File createBuildInfoFile(Project project) {
        File buildInfoFile = new File("${project.name}.buildinfo")

        buildInfoFile.delete()
        buildInfoFile
    }

    private static Map<String, Object> generateBuildInfoFile(Project project) {

        def buildInfo = [
            'buildinfo.version': '1.0-SNAPSHOT',
            'name': project.name,
            'group-id': project.group,
            'artifact-id': project.name,
            'version': project.version
        ]

        buildInfo
    }
}
