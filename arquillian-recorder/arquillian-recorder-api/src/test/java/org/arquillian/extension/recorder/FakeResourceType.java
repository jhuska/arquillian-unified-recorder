package org.arquillian.extension.recorder;

public enum FakeResourceType implements ResourceType {

    SOMETYPE("type");

    private String name;

    private FakeResourceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
