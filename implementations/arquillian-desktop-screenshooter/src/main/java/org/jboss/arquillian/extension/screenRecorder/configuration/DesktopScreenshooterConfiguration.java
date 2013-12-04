/*
* JBoss, Home of Professional Open Source
* Copyright 2013, Red Hat Middleware LLC, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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

package org.jboss.arquillian.extension.screenRecorder.configuration;

import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;

/**
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshooterConfiguration extends ScreenshooterConfiguration {

    private String screenshotBaseFolder = "screenshots";

    
    /**
     * By default set to "screenshots"
     *
     * @return folder inside the root folder where the screenshots be placed. Can also be empty
     */
    public String getScreenshotBaseFolder() {
        return getProperty("screenshotBaseFolder", screenshotBaseFolder);
    }
    
    
}
