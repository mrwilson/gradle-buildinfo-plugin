package uk.co.probablyfine.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.security.DigestInputStream
import java.security.MessageDigest

class BuildInfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.tasks.create('generateBuildInfo') {

            File buildInfoFile = createBuildInfoFile(project)

            buildInformation(project).each { section ->
                section.each { key, value ->
                    buildInfoFile << "${key}=${value}"
                    buildInfoFile << "\n"
                }

                buildInfoFile << "\n"
            }

        }
    }

    private static File createBuildInfoFile(Project project) {
        File buildInfoFile = new File("${project.name}.buildinfo")

        buildInfoFile.delete()
        buildInfoFile
    }

    private static List<Map<String, Object>> buildInformation(Project project) {

        def header = [
            'buildinfo.version': '1.0-SNAPSHOT'
        ]

        def buildInfo = [
            'name': project.name,
            'group-id': project.group,
            'artifact-id': project.name,
            'version': project.version
        ]

        def environment = [
            'java.version': System.getProperty("java.version"),
            'java.vendor': System.getProperty("java.vendor"),
            'os.name': System.getProperty("os.name")
        ]

        def artifacts = project.jar.outputs.files.getFiles()
            .sort { it.name }
            .withIndex()
            .collect { File entry, int i ->
                [
                    "outputs.${i}.filename": entry.name,
                    "outputs.${i}.length": entry.length(),
                    "outputs.${i}.checksums.md5": calculateHash(entry, 'MD5'),
                    "outputs.${i}.checksums.sha1": calculateHash(entry, 'SHA1'),
                    "outputs.${i}.checksums.sha256": calculateHash(entry, 'SHA-256'),
                    "outputs.${i}.checksums.sha512": calculateHash(entry, 'SHA-512'),
                ]
            }

        [ header, buildInfo, environment ] + artifacts
    }

    static def calculateHash(File file, String algorithm) {
        file.withInputStream {
            new DigestInputStream(it, MessageDigest.getInstance(algorithm)).withStream {
                it.eachByte {}
                it.messageDigest.digest().encodeHex() as String
            }
        }
    }
}
