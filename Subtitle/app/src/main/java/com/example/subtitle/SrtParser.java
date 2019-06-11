package com.example.subtitle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SrtParser {
    //    public static TreeMap<Integer, SRT> srt_map;
    public static ArrayList<SRT>srtList;
    public static int lastEndTime;
    /**
     * 解析SRT字幕文件
     * 字幕路径
     */
    public static void parseSrt(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(R.raw.subtitle);
            // TODO Auto-generated catch block
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream,"GB2312"));
            String line = null;
            srtList = new ArrayList<SRT>();
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {

                if (!line.equals("")) {
                    Log.d("gaolei","line-------------------"+ line);
                    sb.append(line).append("@");
                    continue;
                }
                Log.d("gaolei", "sb.toString()-----------"+sb.toString());

                String[] parseStrs = sb.toString().split("@");
                // 该if为了适应一开始就有空行以及其他不符格式的空行情况
                if (parseStrs.length < 3) {
                    sb.delete(0, sb.length());// 清空，否则影响下一个字幕元素的解析</i>
                    continue;
                }

                SRT srt = new SRT();
                // 解析开始和结束时间
                String timeTotime = parseStrs[1];
                int begin_hour = Integer.parseInt(timeTotime.substring(0, 2));
                int begin_mintue = Integer.parseInt(timeTotime.substring(3, 5));
                int begin_scend = Integer.parseInt(timeTotime.substring(6, 8));
                int begin_milli = Integer.parseInt(timeTotime.substring(9, 12));
                int beginTime = (begin_hour * 3600 + begin_mintue * 60 + begin_scend)
                        * 1000 + begin_milli;
                int end_hour = Integer.parseInt(timeTotime.substring(17, 19));
                int end_mintue = Integer.parseInt(timeTotime.substring(20, 22));
                int end_scend = Integer.parseInt(timeTotime.substring(23, 25));
                int end_milli = Integer.parseInt(timeTotime.substring(26, 29));
                int endTime = (end_hour * 3600 + end_mintue * 60 + end_scend)
                        * 1000 + end_milli;

                System.out.println("开始:" + begin_hour + ":" + begin_mintue +
                        ":"
                        + begin_scend + ":" + begin_milli + "=" + beginTime
                        + "ms");
                System.out.println("结束:" + end_hour + ":" + end_mintue + ":"
                        + end_scend + ":" + end_milli + "=" + endTime + "ms");
//     解析字幕文字
                String srtBody = "";
                // 可能1句字幕，也可能2句及以上。
                for (int i = 2; i < parseStrs.length; i++) {
                    srtBody += parseStrs[i]+ "\n";
                }
                // 删除最后一个"\n"
                srtBody = srtBody.substring(0, srtBody.length() - 1);
                // 设置SRT
                srt.setBeginTime(beginTime);
                srt.setEndTime(endTime);
                srt.setSrtBody(new String(srtBody.getBytes(), "UTF-8"));
                // 插入队列
//    srt_map.put(key, srt);
//    key++;
                srtList.add(srt);
                sb.delete(0, sb.length());// 清空，否则影响下一个字幕元素的解析
            }
            lastEndTime=srtList.get(srtList.size()-1).getEndTime();
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //每隔500ms执行一次()取

    }

    public static void showSRT(VideoView videoView,TextView tvSrt) {

        int currentPosition = videoView.getCurrentPosition();//vv是VideoView播放器

        if(currentPosition>lastEndTime){
            tvSrt.setVisibility(View.GONE);
            return;
        }
        for(int i=0;i<srtList.size();i++){
            SRT srtbean =srtList.get(i);
            if (currentPosition > srtbean.getBeginTime()
                    && currentPosition < srtbean.getEndTime()) {

                tvSrt.setText(srtbean.getSrtBody());
                //显示过的就删掉，提高查询效率
                srtList.remove(i);
                break;//找到后就没必要继续遍历下去，节约资源
            }
        }
    }
}
