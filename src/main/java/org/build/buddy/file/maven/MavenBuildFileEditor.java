package org.build.buddy.file.maven;

import org.build.buddy.file.BuildFileEditor;

public class MavenBuildFileEditor implements BuildFileEditor {
    @Override
    public void addDependency(String groupId, String artifactId, String version) throws Exception {
        MavenEditor.addDependency(groupId, artifactId, version);
    }
}
