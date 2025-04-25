package org.build.buddy.file.gradle;

import org.build.buddy.file.BuildFileEditor;

public class GradleBuildFileEditor implements BuildFileEditor {
    @Override
    public void addDependency(String groupId, String artifactId, String version) throws Exception {
        GradleEditor.addDependency(groupId, artifactId, version);
    }
}
