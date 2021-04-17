package com.wty.one_to_many_03;

import java.util.List;
import java.util.Set;

public class Project {

    private Long id;

    private String projectName;

    private Set<Industry> industrySet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<Industry> getIndustrySet() {
        return industrySet;
    }

    public void setIndustrySet(Set<Industry> industrySet) {
        this.industrySet = industrySet;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", industrySet=" + industrySet +
                '}';
    }
}
