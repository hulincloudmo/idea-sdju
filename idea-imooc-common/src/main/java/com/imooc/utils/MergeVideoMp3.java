package com.imooc.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public String clearMusic(String videoInputPath, String videoOutputPath,String userId) throws Exception{

        videoOutputPath= videoOutputPath;
        String uploadPathDB = "/" + userId + "/video" + "/" + videoOutputPath;
        String finalVideoPath = "D:\\SDJU_research_userData" + uploadPathDB;


        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);


        command.add("-i");
        command.add(videoInputPath);
        command.add("-c:v");

        command.add("copy");

        command.add("-an");
        command.add(finalVideoPath);

//        System.out.println("clear:"+command);

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();


        return finalVideoPath;
    }

    public void convertor(String videoInputPath, String mp3InputPath,
                          double seconds, String videoOutputPath) throws Exception {

        List<String> command = new ArrayList<>();

//        command.add(ffmpegEXE);
//
//        command.add("-i");
//        command.add(videoInputPath);
//
//        command.add("-an");
//        command.add(videoOutputPath);
//
//
//        command.add("&&");


        command.add(ffmpegEXE);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutputPath);
//        System.out.println(command);


        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ( (line = br.readLine()) != null ) {
        }

        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

    public static void main(String[] args) {

    }
}
