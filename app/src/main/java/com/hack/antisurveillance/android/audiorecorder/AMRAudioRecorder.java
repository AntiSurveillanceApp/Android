package com.hack.antisurveillance.android.audiorecorder;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class AMRAudioRecorder {

    private MediaRecorder recorder;

    private String fileDirectory;

    private String finalAudioPath;

    public String getAudioFilePath() {
        return finalAudioPath;
    }

    public AMRAudioRecorder(String audioFileDirectory) {
        this.fileDirectory = audioFileDirectory;

        if (!this.fileDirectory.endsWith("/")) {
            this.fileDirectory += "/";
        }

        newRecorder();
    }

    public boolean start() {
        newRecorder();

        prepareRecorder();

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (recorder != null) {
            recorder.start();

            return true;
        }

        return false;
    }

    public boolean stop() {
        if (recorder == null) {
            return false;
        }

        recorder.stop();
        recorder.release();
        recorder = null;
        finalAudioPath = "";

        return true;
    }

    private void newRecorder() {
        recorder = new MediaRecorder();
    }

    private void prepareRecorder() {

        File directory = new File(this.fileDirectory);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("[AMRAudioRecorder] audioFileDirectory is a not valid directory!");
        }

        String filePath = directory.getAbsolutePath() + "/" + System.currentTimeMillis() + ".amr";
        finalAudioPath = filePath;

        recorder.setOutputFile(filePath);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }
}
