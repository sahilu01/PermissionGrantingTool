package com.example.PermissionGrantingTool.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = PGT.COLLECTION_NAME)
public class PGT {
    public static final String COLLECTION_NAME = "Employee";
    @Id
    private String empId;
    private String name;
    private String deptId;
    private String label;
    private String priority;
    private String email;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPriority() { return priority; }

    public void setPriority(String priority) { this.priority = priority; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }
}
