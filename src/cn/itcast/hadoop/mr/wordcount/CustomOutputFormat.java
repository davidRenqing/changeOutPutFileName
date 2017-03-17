package cn.itcast.hadoop.mr.wordcount;
import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CustomOutputFormat extends FileOutputFormat<Text,LongWritable> {

 private String prefix = "custom_";

//每次在文件输出之前都会调用这个getRecordWriter方法，将job的Reduce阶段要输出的内容制定成 <key,value>的格式
//之后调用 继承了RecordWriter的write方法

 @Override
 public RecordWriter<Text,LongWritable> getRecordWriter(TaskAttemptContext job)
   throws IOException, InterruptedException {
  // 新建一个可写入的文件
  Path outputDir = FileOutputFormat.getOutputPath(job); //得到工作job的输出路径 hdfs://hadoop01:9000/output
//  System.out.println("outputDir.getName():"+outputDir.getName()+",otuputDir.toString():"+outputDir.toString());
  String subfix = job.getTaskAttemptID().getTaskID().toString(); //得到Task的attempt的id号 task_local482061002_0001_r_000000
  Path path = new Path(outputDir.toString()+"/"+prefix+subfix.substring(subfix.length()-5, subfix.length())); //根据job的id和路径，创建一个path.hdfs://hadoop01:9000/output/custom_00000
  FSDataOutputStream fileOut = path.getFileSystem(job.getConfiguration()).create(path);//你把这个程序放到你的环境中，然后将各个类与各自的源文件连接到一块，一看，或者查帮助文档就知道了。先使用path.getFileSystem(job.getConfiguration())这个方法返回使用的文件系统，之后再create这个path路径。将返回的路径当作

  return new CustomRecordWriter(fileOut);
 }

}