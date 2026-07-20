package com.sortsense.app.models;

import java.io.Serializable;

public class BacktrackingAlgorithm implements Serializable {
    private String name;
    private String explanation;
    private String steps;
    private String pseudocode;
    private String timeComplexity;
    private String spaceComplexity;
    private String javaCode;
    private String applications;

    public BacktrackingAlgorithm(String name, String explanation, String steps, String pseudocode, 
                                 String timeComplexity, String spaceComplexity, String javaCode, 
                                 String applications) {
        this.name = name;
        this.explanation = explanation;
        this.steps = steps;
        this.pseudocode = pseudocode;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.javaCode = javaCode;
        this.applications = applications;
    }

    public String getName() { return name; }
    public String getExplanation() { return explanation; }
    public String getSteps() { return steps; }
    public String getPseudocode() { return pseudocode; }
    public String getTimeComplexity() { return timeComplexity; }
    public String getSpaceComplexity() { return spaceComplexity; }
    public String getJavaCode() { return javaCode; }
    public String getApplications() { return applications; }
}
