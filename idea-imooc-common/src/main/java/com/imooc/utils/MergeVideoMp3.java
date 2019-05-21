package com.imooc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.utils
 * @ClassName: MergeVideoMp3
 * @Author: hulincloud
 * @Description: 视频中加入BGM
 * @Date: 2019/5/20 15:13
 * @Version: 1.0
 */
public class MergeVideoMp3 {
    private String ffmpegEXE;

    public MergeVideoMp3(String ffmpegEXE) {
        super();
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath, String videoOutputPath, String MP3OutputPath, double senconds) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(MP3OutputPath);
        command.add("-t");
        command.add(String.valueOf(senconds));
        command.add("-y");
        command.add(videoOutputPath);
        processBuilder.start();
    }
}
