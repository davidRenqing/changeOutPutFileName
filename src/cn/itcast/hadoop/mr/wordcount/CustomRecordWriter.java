package cn.itcast.hadoop.mr.wordcount;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class CustomRecordWriter extends RecordWriter<Text,LongWritable> {

        private PrintWriter out;
        private String separator =",";

        //这是构造函数
        public CustomRecordWriter(FSDataOutputStream fileOut) {
                out = new PrintWriter(fileOut); //根据文件的输出系统，创建一个文件输出格式
        }

        @Override
        public void write(Text key, LongWritable value) throws IOException,
                        InterruptedException {
                out.println(key.toString()+separator+value.get());//设定输出的格式
        }

        @Override public void close(TaskAttemptContext context) throws IOException, InterruptedException
     { out.close(); } }