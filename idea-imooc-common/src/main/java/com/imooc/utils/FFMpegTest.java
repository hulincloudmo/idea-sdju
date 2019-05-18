package com.imooc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.utils
 * @ClassName: FFMpegTest
 * @Author: hulincloud
 * @Description: hulincloud
 * @Date: 2019/5/17 18:17
 * @Version: 1.0
 */
public class FFMpegTest {
    private String ffmpegEXE;

    public FFMpegTest(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath, String videoOutputPath) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);
        processBuilder.start();
    }

    public static void main(String[] args) {
        FFMpegTest ffMpegTest = new FFMpegTest("C:\\Users\\hulincloud\\Downloads\\ffmpeg-20190517-c3458f0-win64-static\\bin\\ffmpeg.exe");
        try {
//            ffMpegTest.convertor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
