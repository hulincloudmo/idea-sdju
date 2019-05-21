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

    public void convertor(String videoInputPath, String mp3InputPath,
                          double seconds, String videoOutputPath) throws Exception {

        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutputPath);

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

    }
}
