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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://jboss.org/schema/arquillian/recorder/reporter}testMethodName"/>
 *         &lt;element ref="{http://jboss.org/schema/arquillian/recorder/reporter}result"/>
 *         &lt;element ref="{http://jboss.org/schema/arquillian/recorder/reporter}duration" minOccurs="0"/>
 *         &lt;element ref="{http://jboss.org/schema/arquillian/recorder/reporter}screenshots" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "testMethodName",
    "result",
    "duration",
    "screenshots"
})
@XmlRootElement(name = "testMethod")
public class TestMethod {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String testMethodName;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String result;

    protected Duration duration;
    protected Screenshots screenshots;

    /**
     * Gets the value of the testMethodName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTestMethodName() {
        return testMethodName;
    }

    /**
     * Sets the value of the testMethodName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTestMethodName(String value) {
        this.testMethodName = value;
    }

    /**
     * Gets the value of the result property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the duration property.
     *
     * @return possible object is {@link Duration }
     *
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     *
     * @param value allowed object is {@link Duration }
     *
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the screenshots property.
     *
     * @return possible object is {@link Screenshots }
     *
     */
    public Screenshots getScreenshots() {
        return screenshots;
    }

    /**
     * Sets the value of the screenshots property.
     *
     * @param value allowed object is {@link Screenshots }
     *
     */
    public void setScreenshots(Screenshots value) {
        this.screenshots = value;
    }

}
