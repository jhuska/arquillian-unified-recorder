/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.extension.recorder;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Exports some resource to defined location, move some resource from one location to another.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public interface Exporter<T extends Resource<? extends ResourceMetaData, ? extends ResourceType>> {

    /**
     *
     * @param src
     * @param dest
     */
    public void export(T src, T dest);

    /**
     *
     * @param src
     * @param dest
     */
    public void export(File src, File dest);

    /**
     *
     * @param src
     * @param dest
     */
    public void export(URI src, URI dest);

    /**
     *
     * @param src
     * @param dest
     */
    public void export(URL src, URL dest);
}
