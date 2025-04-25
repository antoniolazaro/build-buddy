package org.build.buddy.file;


import org.build.buddy.file.gradle.GradleBuildFileEditor;
import org.build.buddy.file.maven.MavenBuildFileEditor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class BuildFileStrategy {

    private static final Map<String, BuildFileEditor> editors = new HashMap<>();

    static {
        editors.put("pom.xml", new MavenBuildFileEditor());
        editors.put("build.gradle", new GradleBuildFileEditor());
    }

    public static void addDependency(String groupId, String artifactId, String version) throws Exception {
        for (Map.Entry<String, BuildFileEditor> entry : editors.entrySet()) {
            if (Files.exists(Path.of(entry.getKey()))) {
                entry.getValue().addDependency(groupId, artifactId, version);
                return;
            }
        }
        throw new IllegalStateException("No supported build file found.");
    }
}

