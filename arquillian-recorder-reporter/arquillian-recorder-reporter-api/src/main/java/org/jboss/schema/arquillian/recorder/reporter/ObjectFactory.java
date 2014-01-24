/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.schema.arquillian.recorder.reporter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * org.jboss.schema.arquillian.recorder.reporter package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type
 * definitions, element declarations and model groups. Factory methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _When_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter", "when");
    private static final QName _File_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter", "file");
    private static final QName _Result_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter", "result");
    private static final QName _TestMethodName_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter",
        "testMethodName");
    private static final QName _TestClassName_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter",
        "testClassName");
    private static final QName _Type_QNAME = new QName("http://jboss.org/schema/arquillian/recorder/reporter", "type");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * org.jboss.schema.arquillian.recorder.reporter
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Screenshot }
     *
     */
    public Screenshot createScreenshot() {
        return new Screenshot();
    }

    /**
     * Create an instance of {@link TestMethod }
     *
     */
    public TestMethod createTestMethod() {
        return new TestMethod();
    }

    /**
     * Create an instance of {@link Duration }
     *
     */
    public Duration createDuration() {
        return new Duration();
    }

    /**
     * Create an instance of {@link Screenshots }
     *
     */
    public Screenshots createScreenshots() {
        return new Screenshots();
    }

    /**
     * Create an instance of {@link Report }
     *
     */
    public Report createReport() {
        return new Report();
    }

    /**
     * Create an instance of {@link Suite }
     *
     */
    public Suite createSuite() {
        return new Suite();
    }

    /**
     * Create an instance of {@link TestClass }
     *
     */
    public TestClass createTestClass() {
        return new TestClass();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "when")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createWhen(String value) {
        return new JAXBElement<String>(_When_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "file")
    public JAXBElement<String> createFile(String value) {
        return new JAXBElement<String>(_File_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "result")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createResult(String value) {
        return new JAXBElement<String>(_Result_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "testMethodName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createTestMethodName(String value) {
        return new JAXBElement<String>(_TestMethodName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "testClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createTestClassName(String value) {
        return new JAXBElement<String>(_TestClassName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    @XmlElementDecl(namespace = "http://jboss.org/schema/arquillian/recorder/reporter", name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

}
