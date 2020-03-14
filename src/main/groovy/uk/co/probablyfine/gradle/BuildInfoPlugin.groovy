package uk.co.probablyfine.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildInfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.tasks.create('generateBuildInfo') {
            File buildInfoFile = new File("${project.name}.buildinfo")

            buildInfoFile.delete()

            generateBuildInfoFile(project).each { key, value ->
                buildInfoFile << "${key}=${value}\n"
            }


        }
    }

    static Map<String, String> generateBuildInfoFile(Project project) {

        def buildInfo = [
            'buildinfo.version': '1.0-SNAPSHOT',
            'name': project.name
        ]

        buildInfo
    }
}
