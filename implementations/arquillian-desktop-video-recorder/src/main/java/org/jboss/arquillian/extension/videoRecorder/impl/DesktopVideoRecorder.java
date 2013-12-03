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
package org.jboss.arquillian.extension.videoRecorder.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.arquillian.extension.recorder.video.VideoConfiguration;
import org.arquillian.extension.recorder.video.VideoMetaData;
import org.arquillian.extension.recorder.video.event.StartRecordVideo;
import org.arquillian.extension.recorder.video.event.StopRecordVideo;
import org.arquillian.extension.recorder.video.event.VideoExtensionConfigured;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopVideoRecorder {

    private static final Logger logger = LoggerFactory.getLogger(DesktopVideoRecorder.class);
    
    @Inject
    private Instance<VideoConfiguration> configuration;
    private File root;
    private VideoRecorder recorder;
    private Timer timer;
    private VideoMetaData testMetaData;

    public void onRecorderConfigured(@Observes VideoExtensionConfigured event) throws IOException {
        System.out.println("aaaaaa");
        root = new File(configuration.get().getRootFolder(), "video");
        FileUtils.deleteDirectory(root);
        root.mkdirs();
        recorder = new VideoRecorder(50, configuration.get().getVideoType());
        timer = new Timer();
        timer.schedule(new TestTimeoutTask(), TimeUnit.SECONDS.toMillis(configuration.get().getTestTimeout()));
        System.out.println("bbbbb");
    }

    public void onStartRecording(@Observes StartRecordVideo event) {
        testMetaData = event.getMetaData();
        recorder.startRecording();
    }

    public void onStopRecording(@Observes StopRecordVideo event) {
        File testClassDirectory = getDirectory(root, testMetaData.getTestClass());
        stopRecording(testClassDirectory, testMetaData.getTestMethodName() + "_" + testMetaData.getTestResult().getStatus().name().toLowerCase());
    }

    private synchronized void stopRecording(File directory, String fileName) {
        directory.mkdirs();
        String videoName = fileName + "." + configuration.get().getVideoType();
        File video = FileUtils.getFile(directory, videoName);
        recorder.stopRecording(video);
        if (timer != null) {
            timer.cancel();
        }
    }

    private File getDirectory(File root, TestClass clazz) {
        String packageName = clazz.getJavaClass().getPackage().getName();
        String className = clazz.getJavaClass().getSimpleName();
        File directory = FileUtils.getFile(root, packageName, className);
        return directory;
    }

    private class TestTimeoutTask extends TimerTask {

        private final TestClass testClass;
        private final Method method;

        public TestTimeoutTask() {
            this.testClass = null;
            this.method = null;
        }

        public TestTimeoutTask(TestClass testClass, Method method) {
            this.testClass = testClass;
            this.method = method;
        }

        @Override
        public void run() {
            if (testClass != null && method != null) {
                stopRecording(getDirectory(root, testClass), method.getName() + "_timeout");
                logger.error("Test method {} in class {} has reached its timeout, stopping video recording", method.getName(), testClass.getName());
            } else {
                stopRecording(root, configuration.get().getVideoName());
                logger.error("The last test method reached its timeout, stopping video recording.");
            }
        }
    }
}
