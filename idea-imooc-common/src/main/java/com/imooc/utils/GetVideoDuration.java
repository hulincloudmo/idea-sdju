package com.imooc.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.utils
 * @ClassName: MergeVideoMp3
 * @Author: hulincloud
 * @Description: 获取视频长度（APP中没有能力获取，故调用ffmpeg获取长度)
 * @Date: 2019/5/20 15:13
 * @Version: 1.0
 */
public class GetVideoDuration {
    private String ffmpegEXE;

    public GetVideoDuration(String ffmpegEXE) {
        super();
        this.ffmpegEXE = ffmpegEXE;
    }


    public void convertor(String videoInputPath, String mp3InputPath,
                          double seconds, String videoOutputPath) throws Exception {

        List<String> command = new ArrayList<>();


        command.add(ffmpegEXE);

        command.add("-i");
        command.add(mp3InputPath);



        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((process.getErrorStream())));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        line = bufferedReader.readLine();
        while ( line!= null){
            stringBuffer.append(line);
        }
        bufferedReader.close();

        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
        Pattern pattern = Pattern.compile(regexDuration);
        Matcher matcher = pattern.matcher(stringBuffer.toString());
        if (matcher.find()){
            int time = getTimelen(matcher.group(1));
            System.out.println(time);
            System.out.println(matcher.group(3));

        }

    }

    private static int getTimelen(String timelen){
        int min=0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min+=Integer.valueOf(strs[0])*60*60;
        }
        if(strs[1].compareTo("0")>0){
            min+=Integer.valueOf(strs[1])*60;
        }
        if(strs[2].compareTo("0")>0){
            min+=Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    public static void main(String[] args) {

    }
}
